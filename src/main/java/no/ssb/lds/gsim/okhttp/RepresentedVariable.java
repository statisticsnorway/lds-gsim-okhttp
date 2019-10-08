package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
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
        String substantiveValueDomainId = getSubstantiveValueDomain();
        if (substantiveValueDomainId.contains(EnumeratedValueDomain.ENUMERATED_VALUE_NAME)) {
            EnumeratedValueDomain.Fetcher fetcher = new EnumeratedValueDomain.Fetcher();
            fetcher.withParametersFrom(this);
            return fetcher.fetchAsync(substantiveValueDomainId)
                    .thenApply(result -> (ValueDomain) result.withParametersFrom(this));
        } else if (substantiveValueDomainId.contains(DescribedValueDomain.DESCRIBED_VALUE_DOMAIN_NAME)) {
            DescribedValueDomain.Fetcher fetcher = new DescribedValueDomain.Fetcher();
            fetcher.withParametersFrom(this);
            return fetcher.fetchAsync(substantiveValueDomainId)
                    .thenApply(result -> (ValueDomain) result.withParametersFrom(this));
        } else {
            throw new IllegalArgumentException("unexpected type " + substantiveValueDomainId);
        }
    }

    public static class Fetcher extends AbstractFetcher<RepresentedVariable> {

        @Override
        public RepresentedVariable deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, RepresentedVariable.class);
        }

        @Override
        protected String getDomainName() {
            return REPRESENTED_VARIABLE_NAME;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, RepresentedVariable object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
