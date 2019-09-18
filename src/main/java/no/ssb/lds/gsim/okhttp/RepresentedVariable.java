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
import java.util.concurrent.CompletableFuture;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepresentedVariable extends IdentifiableArtefact {

    private static final String REPRESENTED_VARIABLE_NAME = "RepresentedVariable";

    @JsonProperty
    private String substantiveValueDomain;

    public String getSubstantiveValueDomain() {
        return substantiveValueDomain;
    }

    public void setSubstantiveValueDomain(String substantiveValueDomain) {
        this.substantiveValueDomain = substantiveValueDomain;
    }

    public CompletableFuture<ValueDomain> fetchInstanceVariables() {
        String substantiveValueDomain = getSubstantiveValueDomain();
        if (substantiveValueDomain.contains(EnumeratedValueDomain.ENUMERATED_VALUE_NAME)) {
            EnumeratedValueDomain.Fetcher fetcher = new EnumeratedValueDomain.Fetcher();
            fetcher.withParametersFrom(this);
            return fetcher.fetchAsync(substantiveValueDomain)
                    .thenApply(result -> (ValueDomain) result.withParametersFrom(this));
        } else if (substantiveValueDomain.contains(DescribedValueDomain.DESCRIBED_VALUE_DOMAIN_NAME)) {
            DescribedValueDomain.Fetcher fetcher = new DescribedValueDomain.Fetcher();
            fetcher.withParametersFrom(this);
            return fetcher.fetchAsync(substantiveValueDomain)
                    .thenApply(result -> (ValueDomain) result.withParametersFrom(this));
        } else {
            throw new IllegalArgumentException("unexpected type " + substantiveValueDomain);
        }
    }

    static class Fetcher extends AbstractFetcher<RepresentedVariable> {

        @Override
        public RepresentedVariable deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, RepresentedVariable.class);
        }

        @Override
        public Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) {
            String normalizedId = id.replaceAll(REPRESENTED_VARIABLE_NAME + "/", "");
            if (normalizedId.startsWith("/")) {
                normalizedId = normalizedId.substring(1);
            }
            HttpUrl url = prefix.resolve("./" + REPRESENTED_VARIABLE_NAME + "/" + normalizedId);
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
        public byte[] serialize(ObjectMapper mapper, RepresentedVariable object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
