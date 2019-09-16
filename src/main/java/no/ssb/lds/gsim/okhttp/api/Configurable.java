package no.ssb.lds.gsim.okhttp.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


import java.time.Instant;

public interface Configurable {

    @JsonIgnore
    Configurable withMapper(ObjectMapper mapper);

    @JsonIgnore
    Configurable withClient(OkHttpClient client);

    @JsonIgnore
    Configurable withPrefix(HttpUrl prefix);

    @JsonIgnore
    Configurable withTimestamp(Long timestamp);

    @JsonIgnore
    default Configurable withTimestamp(Instant timestamp) {
        return withTimestamp(timestamp.toEpochMilli());
    }

    @JsonIgnore
    default Configurable withParametersFrom(Configured configured) {
        return this.withClient(configured.getClient())
                .withMapper(configured.getMapper())
                .withPrefix(configured.getPrefix())
                .withTimestamp(configured.getTimestamp());
    }
}
