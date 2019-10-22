package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessStepInstance extends IdentifiableArtefact {

    public static final String PROCESS_STEP_INSTANCE_NAME = "ProcessStepInstance";

    @JsonProperty
    private String processExecutionCode;

    @JsonProperty
    private String processExecutionLog;

    @JsonProperty
    private List<String> transformableInputs;

    @JsonProperty
    private List<String> transformedOutputs;

    public String getProcessExecutionCode() {
        return processExecutionCode;
    }

    public void setProcessExecutionCode(String processExecutionCode) {
        this.processExecutionCode = processExecutionCode;
    }

    public String getProcessExecutionLog() {
        return processExecutionLog;
    }

    public void setProcessExecutionLog(String processExecutionLog) {
        this.processExecutionLog = processExecutionLog;
    }

    public List<String> getTransformableInputs() {
        return transformableInputs;
    }

    public void setTransformableInputs(List<String> transformableInputs) {
        this.transformableInputs = transformableInputs;
    }

    public List<String> getTransformedOutputs() {
        return transformedOutputs;
    }

    public void setTransformedOutputs(List<String> transformedOutputs) {
        this.transformedOutputs = transformedOutputs;
    }

    public static class Fetcher extends AbstractFetcher<ProcessStepInstance> {

        @Override
        public ProcessStepInstance deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, ProcessStepInstance.class);
        }

        @Override
        protected String getDomainName() {
            return PROCESS_STEP_INSTANCE_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, ProcessStepInstance object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
