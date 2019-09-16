package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.*;
import no.ssb.lds.gsim.okhttp.api.Configured;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class IdentifiableArtefact extends Configured {

    @JsonProperty
    private String id;
    @JsonIgnore
    private Map<String, Object> unknownProperties = new LinkedHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getUnknownProperties() {
        return unknownProperties;
    }

    @JsonAnySetter
    public void setUnknowProperty(String key, Object value) {
        unknownProperties.put(key, value);
    }
}
