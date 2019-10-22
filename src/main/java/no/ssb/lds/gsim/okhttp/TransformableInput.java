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
public class TransformableInput extends IdentifiableArtefact {

    public static final String TRANSFORMABLE_INPUT_NAME = "TransformableInput";

    @JsonProperty
    private String inputId;

    @JsonProperty
    private String inputType;

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public static class Fetcher extends AbstractFetcher<TransformableInput> {

        @Override
        public TransformableInput deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, TransformableInput.class);
        }

        @Override
        protected String getDomainName() {
            return TRANSFORMABLE_INPUT_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, TransformableInput object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
