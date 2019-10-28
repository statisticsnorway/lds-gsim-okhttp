package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessProcess extends IdentifiableArtefact {

    public static final String BUSINESS_PROCESS_NAME = "BusinessProcess";

    @JsonProperty
    private List<String> processSteps;

    @JsonProperty
    private boolean isPlaceholderProcess;

    public boolean hasPlaceholderProcess() {
        return this.isPlaceholderProcess;
    }

    public void setPlaceholderProcess(boolean placeholderProcess) {
        this.isPlaceholderProcess = placeholderProcess;
    }

    public List<String> getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(List<String> processSteps) {
        this.processSteps = processSteps;
    }

    public static class Fetcher extends AbstractFetcher<BusinessProcess> {

        @Override
        public BusinessProcess deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, BusinessProcess.class);
        }

        @Override
        protected String getDomainName() {
            return BUSINESS_PROCESS_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, BusinessProcess object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
