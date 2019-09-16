package no.ssb.lds.gsim.okhttp.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public interface Deserializable<T> {
    T deserialize(ObjectMapper mapper, InputStream bytes) throws IOException;
}
