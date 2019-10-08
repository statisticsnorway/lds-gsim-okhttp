package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnumeratedValueDomain extends ValueDomain {

    static final String ENUMERATED_VALUE_NAME = "EnumeratedValueDomain";

    @JsonProperty
    private String klassUrl;

    public String getKlassUrl() {
        return klassUrl;
    }

    public void setKlassUrl(String klassUrl) {
        this.klassUrl = klassUrl;
    }

    public static class Fetcher extends AbstractFetcher<EnumeratedValueDomain> {

        @Override
        public EnumeratedValueDomain deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, EnumeratedValueDomain.class);
        }

        @Override
        protected String getDomainName() {
            return ENUMERATED_VALUE_NAME;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, EnumeratedValueDomain object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
