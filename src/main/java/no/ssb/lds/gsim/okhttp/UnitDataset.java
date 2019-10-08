package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitDataset extends Dataset {

    public static final String UNIT_DATA_SET_NAME = "UnitDataSet";
    @JsonProperty
    private String unitDataStructure;

    public String getUnitDataStructure() {
        return unitDataStructure;
    }

    public void setUnitDataStructure(String unitDataStructure) {
        this.unitDataStructure = unitDataStructure;
    }

    public CompletableFuture<UnitDataStructure> fetchUnitDataStructure() {
        UnitDataStructure.Fetcher fetcher = new UnitDataStructure.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.fetchAsync(getUnitDataStructure())
                .thenApply(result -> (UnitDataStructure) result.withParametersFrom(this));
    }

    public static class Fetcher extends AbstractFetcher<UnitDataset> {

        @Override
        public UnitDataset deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, UnitDataset.class);
        }

        @Override
        protected String getDomainName() {
            return UNIT_DATA_SET_NAME;
        }


        @Override
        public byte[] serialize(ObjectMapper mapper, UnitDataset object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
