package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransformedOutput extends IdentifiableArtefact {

    public static final String TRANSFORMABLE_OUTPUT_NAME = "TransformedOutput";

    @JsonProperty
    private String outputId;

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }

    public static class Fetcher extends AbstractFetcher<TransformedOutput> {

        @Override
        public TransformedOutput deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, TransformedOutput.class);
        }

        @Override
        protected String getDomainName() {
            return TRANSFORMABLE_OUTPUT_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, TransformedOutput object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
