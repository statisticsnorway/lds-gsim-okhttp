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
public class StatisticalProgram extends IdentifiableArtefact {

    public static final String STATISTICAL_PROGRAM_NAME = "StatisticalProgram";

    @JsonProperty
    private String statisticalProgramStatus;

    @JsonProperty
    private List<String> statisticalProgramCycles;

    @JsonProperty
    private List<Map<String, Object>> subjectMatterDomains;

    public void setStatisticalProgramStatus(String statisticalProgramStatus) {
        this.statisticalProgramStatus = statisticalProgramStatus;
    }

    public void setStatisticalProgramCycles(List<String> statisticalProgramCycles) {
        this.statisticalProgramCycles = statisticalProgramCycles;
    }

    public void setSubjectMatterDomains(List<Map<String,Object>> subjectMatterDomains) {
        this.subjectMatterDomains = subjectMatterDomains;
    }

    public static class Fetcher extends AbstractFetcher<StatisticalProgram> {

        @Override
        public StatisticalProgram deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, StatisticalProgram.class);
        }

        @Override
        protected String getDomainName() {
            return STATISTICAL_PROGRAM_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, StatisticalProgram object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
