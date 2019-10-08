package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceVariable extends IdentifiableArtefact {

    public static final String INSTANCE_VARIABLE_NAME = "InstanceVariable";

    @JsonProperty
    private String shortName;
    @JsonProperty
    private String dataStructureComponentRole;
    @JsonProperty
    private String dataStructureComponentType;
    @JsonProperty
    private boolean identifierComponentIsComposite;
    @JsonProperty
    private boolean identifierComponentIsUnique;
    @JsonProperty
    private String representedVariable;
    @JsonProperty
    private String sentinelValueDomain;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDataStructureComponentRole() {
        return dataStructureComponentRole;
    }

    public void setDataStructureComponentRole(String dataStructureComponentRole) {
        this.dataStructureComponentRole = dataStructureComponentRole;
    }

    public String getDataStructureComponentType() {
        return dataStructureComponentType;
    }

    public void setDataStructureComponentType(String dataStructureComponentType) {
        this.dataStructureComponentType = dataStructureComponentType;
    }

    public boolean getIdentifierComponentIsComposite() {
        return identifierComponentIsComposite;
    }

    public void setIdentifierComponentIsComposite(boolean identifierComponentIsComposite) {
        this.identifierComponentIsComposite = identifierComponentIsComposite;
    }

    public boolean getIdentifierComponentIsUnique() {
        return identifierComponentIsUnique;
    }

    public void setIdentifierComponentIsUnique(boolean identifierComponentIsUnique) {
        this.identifierComponentIsUnique = identifierComponentIsUnique;
    }

    public String getRepresentedVariable() {
        return representedVariable;
    }

    public void setRepresentedVariable(String representedVariable) {
        this.representedVariable = representedVariable;
    }

    public String getSentinelValueDomain() {
        return sentinelValueDomain;
    }

    public void setSentinelValueDomain(String sentinelValueDomain) {
        this.sentinelValueDomain = sentinelValueDomain;
    }

    public CompletableFuture<RepresentedVariable> fetchRepresentedVariable() {
        RepresentedVariable.Fetcher fetcher = new RepresentedVariable.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.fetchAsync(getRepresentedVariable())
                .thenApply(result -> (RepresentedVariable) result.withParametersFrom(this));
    }


    public static class Fetcher extends AbstractFetcher<InstanceVariable> {

        @Override
        public InstanceVariable deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, InstanceVariable.class);
        }

        @Override
        protected String getDomainName() {
            return INSTANCE_VARIABLE_NAME;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, InstanceVariable object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
