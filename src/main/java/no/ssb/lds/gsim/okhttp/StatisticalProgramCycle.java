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
public class StatisticalProgramCycle extends IdentifiableArtefact {

    public static final String STATISTICAL_PROGRAM_CYCLE_NAME = "StatisticalProgramCycle";

    @JsonProperty
    private String referencePeriodEnd;

    @JsonProperty
    private String referencePeriodStart;

    public List<String> getBusinessProcesses() {
        return businessProcesses;
    }

    @JsonProperty
    private List<String> businessProcesses;

    public void setReferencePeriodEnd(String referencePeriodEnd) {
        this.referencePeriodEnd = referencePeriodEnd;
    }

    public void setReferencePeriodStart(String referencePeriodStart) {
        this.referencePeriodStart = referencePeriodStart;
    }

    public void setBusinessProcesses(List<String> businessProcesses) {
        this.businessProcesses = businessProcesses;
    }

    public static class Fetcher extends AbstractFetcher<StatisticalProgramCycle> {

        @Override
        public StatisticalProgramCycle deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, StatisticalProgramCycle.class);
        }

        @Override
        protected String getDomainName() {
            return STATISTICAL_PROGRAM_CYCLE_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, StatisticalProgramCycle object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
