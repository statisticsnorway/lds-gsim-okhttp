package no.ssb.lds.gsim.okhttp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractFetcher<T extends Configured> extends Configured implements Fetchable<T>, Updatable<T>, Deserializable<T>,
        Serializable<T>, Configurable {

    private static final String APPLICATION_JSON_STRING = "application/json; charset=utf-8";
    private static final MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_STRING);
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFetcher.class);

    public Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) throws IOException {
        String normalizedId = id.replace(getDomainName() + "/", "");
        if (normalizedId.startsWith("/")) {
            normalizedId = normalizedId.substring(1);
        }
        HttpUrl url = prefix.resolve("./" + getDomainName() + "/" + normalizedId);
        if (url == null) {
            throw new MalformedURLException();
        }
        Request.Builder builder = new Request.Builder();
        return builder.url(url);
    }

    public Request.Builder getUpdateRequest(HttpUrl prefix, String id) throws IOException {
        Request.Builder builder = new Request.Builder();
        HttpUrl url = prefix.resolve("./" + getDomainName() + "/" + id);
        if (url == null) {
            throw new MalformedURLException();
        }
        return builder.url(url);
    }


    @Override
    public T fetch(String id) {
        return fetch(id, getTimestamp());
    }

    @Override
    public CompletableFuture<T> fetchAsync(String id) {
        return fetchAsync(id, getTimestamp());
    }

    @Override
    public CompletableFuture<T> fetchAsync(String id, Long timestamp) {
        try {
            Request.Builder request = getFetchRequest(getPrefix(), id, getTimestamp());
            request.header("Accept", APPLICATION_JSON_STRING);
            Call call = getClient().newCall(request.build());
            FetcherCallback callback = new FetcherCallback();
            call.enqueue(callback);
            return callback.thenApplyAsync(t -> (T) t.withParametersFrom(this));
        } catch (IOException e) {
            // Need to use java < 9 API here.
            CompletableFuture<T> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }
    }

    @Override
    public CompletableFuture<Void> updateAsync(String id, T object) throws IOException {
        ByteString content = ByteString.of(serialize(getMapper(), object));
        Request.Builder updateRequest = getUpdateRequest(getPrefix(), id);


        updateRequest.put(RequestBody.create(content, APPLICATION_JSON));
        Call call = getClient().newCall(updateRequest.build());
        CompletableFuture<Void> future = new CompletableFuture<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    future.complete(null);
                }
                ResponseBody body = response.body();
                String string = body != null ? body.string() : "body missing";
                future.completeExceptionally(new IOException("http error: " + string));
            }
        });
        return future;
    }

    @Override
    public abstract T deserialize(ObjectMapper mapper, InputStream bytes) throws IOException;

    protected abstract String getDomainName();

    private final class FetcherCallback extends CompletableFuture<T> implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            this.completeExceptionally(e);
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) {
            try {
                if (!response.isSuccessful()) {
                    throw new IOException("http error: " + response.message());
                }
                ResponseBody body = response.body();
                if (body == null) {
                    throw new IOException("empty body");
                }
                this.complete(AbstractFetcher.this.deserialize(getMapper(), body.byteStream()));
            } catch (Exception e) {
                LOG.warn("failed to deserialize body of {}", call.request());
                this.completeExceptionally(e);
            }
        }
    }
}
