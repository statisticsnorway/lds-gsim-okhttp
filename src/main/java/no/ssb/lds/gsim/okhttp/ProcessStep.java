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
public class ProcessStep extends IdentifiableArtefact {

    public static final String PROCESS_STEP_NAME = "ProcessStep";

    @JsonProperty
    private List<Map> codeBlocks;

    public List<Map> getCodeBlocks() {
        return codeBlocks;
    }

    public void setCodeBlocks(List<Map> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }

    public static class Fetcher extends AbstractFetcher<ProcessStep> {

        @Override
        public ProcessStep deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, ProcessStep.class);
        }

        @Override
        protected String getDomainName() {
            return PROCESS_STEP_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, ProcessStep object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
