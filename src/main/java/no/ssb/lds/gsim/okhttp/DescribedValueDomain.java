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
public class DescribedValueDomain extends ValueDomain {

    static final String DESCRIBED_VALUE_DOMAIN_NAME = "DescribedValueDomain";

    @JsonProperty
    private Number minValue;

    @JsonProperty
    private Number maxValue;

    @JsonProperty
    private Number minLength;

    @JsonProperty
    private Number maxLength;

    @JsonProperty
    private Number minDecimals;

    @JsonProperty
    private Number maxDecimals;

    public Number getMinValue() {
        return minValue;
    }

    public void setMinValue(Number minValue) {
        this.minValue = minValue;
    }

    public Number getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Number maxValue) {
        this.maxValue = maxValue;
    }

    public Number getMinLength() {
        return minLength;
    }

    public void setMinLength(Number minLength) {
        this.minLength = minLength;
    }

    public Number getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Number maxLength) {
        this.maxLength = maxLength;
    }

    public Number getMinDecimals() {
        return minDecimals;
    }

    public void setMinDecimals(Number minDecimals) {
        this.minDecimals = minDecimals;
    }

    public Number getMaxDecimals() {
        return maxDecimals;
    }

    public void setMaxDecimals(Number maxDecimals) {
        this.maxDecimals = maxDecimals;
    }

    public static class Fetcher extends AbstractFetcher<DescribedValueDomain> {

        @Override
        public DescribedValueDomain deserialize(ObjectMapper mapper, InputStream bytes) throws IOException {
            return mapper.readValue(bytes, DescribedValueDomain.class);
        }

        @Override
        public Request.Builder getFetchRequest(HttpUrl prefix, String id, Long timestamp) throws IOException {
            String normalizedId = id.replace(DESCRIBED_VALUE_DOMAIN_NAME + "/", "");
            if (normalizedId.startsWith("/")) {
                normalizedId = normalizedId.substring(1);
            }
            HttpUrl url = prefix.resolve("./" + DESCRIBED_VALUE_DOMAIN_NAME + "/" + normalizedId);
            if (url == null) {
                throw new MalformedURLException();
            }
            Request.Builder builder = new Request.Builder();
            return builder.url(url);
        }

        @Override
        public Request.Builder getUpdateRequest(HttpUrl prefix, String id) throws IOException {
            Request.Builder builder = new Request.Builder();
            HttpUrl url = prefix.resolve("./" + DESCRIBED_VALUE_DOMAIN_NAME + "/" + id);
            if (url == null) {
                throw new MalformedURLException();
            }
            return builder.url(url);
        }

        @Override
        public byte[] serialize(ObjectMapper mapper, DescribedValueDomain object) throws IOException {
            return mapper.writeValueAsBytes(object);
        }
    }
}
