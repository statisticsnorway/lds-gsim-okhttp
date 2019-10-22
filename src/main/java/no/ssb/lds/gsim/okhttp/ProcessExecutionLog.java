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
public class ProcessExecutionLog extends IdentifiableArtefact {

    public static final String PROCESS_EXECUTION_LOG_NAME = "ProcessExecutionLog";

    @JsonProperty
    private List<Map> codeBlocks;

    public static class Fetcher extends AbstractFetcher<ProcessExecutionLog> {

        @Override
        public ProcessExecutionLog deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, ProcessExecutionLog.class);
        }

        @Override
        protected String getDomainName() {
            return PROCESS_EXECUTION_LOG_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, ProcessExecutionLog object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
