package no.ssb.lds.gsim.okhttp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractFetcher<T extends Configured> extends Configured implements Fetchable<T>, Updatable<T>, Deserializable<T>,
        Serializable<T>, Configurable {

    public static final String APPLICATION_JSON_STRING = "application/json; charset=utf-8";
    public static final MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_STRING);
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFetcher.class);

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
        Request.Builder request = getFetchRequest(getPrefix(), id, getTimestamp());
        request.header("Accept", APPLICATION_JSON_STRING);
        Call call = getClient().newCall(request.build());
        FetcherCallback callback = new FetcherCallback();
        call.enqueue(callback);
        return callback.thenApplyAsync(t -> (T) t.withParametersFrom(this));
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

    public abstract Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) throws IOException;

    public abstract Request.Builder getUpdateRequest(HttpUrl prefix, String id) throws IOException;


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
