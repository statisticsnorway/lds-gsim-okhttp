package no.ssb.lds.gsim.okhttp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.api.AbstractFetcher;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnumeratedValueDomain extends ValueDomain {

    static final String ENUMERATED_VALUE_NAME = "EnumeratedValueDomain";

    @JsonProperty
    private String klassUrl;

    public String getKlassUrl() {
        return klassUrl;
    }

    public void setKlassUrl(String klassUrl) {
        this.klassUrl = klassUrl;
    }

    static class Fetcher extends AbstractFetcher<EnumeratedValueDomain> {

        @Override
        public EnumeratedValueDomain deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, EnumeratedValueDomain.class);
        }

        @Override
        public Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) {
            String normalizedId = id.replaceAll(ENUMERATED_VALUE_NAME + "/", "");
            if (normalizedId.startsWith("/")) {
                normalizedId = normalizedId.substring(1);
            }
            HttpUrl url = prefix.resolve("./" + ENUMERATED_VALUE_NAME + "/" + normalizedId);
            if (url == null) {
                throw new RuntimeException(new MalformedURLException());
            }
            Request.Builder builder = new Request.Builder();
            return builder.url(url);
        }

        @Override
        public Request.Builder getUpdateRequest(HttpUrl prefix, String id) {
            return null;
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, EnumeratedValueDomain object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }

}
