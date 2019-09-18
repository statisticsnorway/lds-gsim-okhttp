package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogicalRecord extends IdentifiableArtefact {

    public static final String LOGICAL_RECORD_NAME = "LogicalRecord";

    @JsonProperty
    private List<String> instanceVariables;

    @JsonProperty
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getInstanceVariables() {
        return instanceVariables;
    }

    public void setInstanceVariables(List<String> instanceVariables) {
        this.instanceVariables = instanceVariables;
    }

    public CompletableFuture<List<InstanceVariable>> fetchInstanceVariables() {
        if (getInstanceVariables().isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        InstanceVariable.Fetcher fetcher = new InstanceVariable.Fetcher();
        fetcher.withParametersFrom(this);

        List<CompletableFuture<InstanceVariable>> fetches = getInstanceVariables().stream()
                .map(fetcher::fetchAsync).collect(Collectors.toList());

        return CompletableFuture.allOf(fetches.toArray(new CompletableFuture[0]))
                .thenApply(aVoid -> fetches.stream()
                        .map(CompletableFuture::join)
                        .map(instanceVariable -> (InstanceVariable) instanceVariable.withParametersFrom(this))
                        .collect(Collectors.toList())
                );
    }

    static class Fetcher extends AbstractFetcher<LogicalRecord> {

        @Override
        public LogicalRecord deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, LogicalRecord.class);
        }

        @Override
        public Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) {
            String normalizedId = id.replaceAll(LOGICAL_RECORD_NAME + "/", "");
            if (normalizedId.startsWith("/")) {
                normalizedId = normalizedId.substring(1);
            }
            HttpUrl url = prefix.resolve("./" + LOGICAL_RECORD_NAME + "/" + normalizedId);
            if (url == null) {
                throw new RuntimeException(new MalformedURLException());
            }
            Request.Builder builder = new Request.Builder();
            return builder.url(url);
        }

        @Override
        public Request.Builder getUpdateRequest(HttpUrl prefix, String id) {
            return null;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, LogicalRecord object) throws IOException {
            return new byte[0];
        }
    }
}
