package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
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

    public static class Fetcher extends AbstractFetcher<LogicalRecord> {

        @Override
        public LogicalRecord deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, LogicalRecord.class);
        }

        @Override
        protected String getDomainName() {
            return LOGICAL_RECORD_NAME;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, LogicalRecord object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
