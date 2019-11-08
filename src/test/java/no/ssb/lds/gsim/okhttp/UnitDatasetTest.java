package no.ssb.lds.gsim.okhttp;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class UnitDatasetTest {

    private MockWebServer server;

    @Before
    public void setUp() {
        server = new MockWebServer();
    }

    @Test
    public void testDeserialize() throws IOException {
        InputStream in = this.getClass().getResourceAsStream(
                "/data/UnitDataSet_Person_1.json");
        server.enqueue(new MockResponse().setBody(new Buffer().readFrom(in)));
        server.start();
        HttpUrl baseUrl = server.url("/test/");
        UnitDataset.Fetcher fetcher = new UnitDataset.Fetcher();
        fetcher.withPrefix(baseUrl);
        fetcher.withClient(new OkHttpClient());
        fetcher.withMapper(new ObjectMapper());

        UnitDataset unitDataset = fetcher.fetch("unitDatasetId");
        assertThat(unitDataset).isNotNull();
        assertThat(unitDataset.getDataSourcePath()).isEqualTo("%DATA_PATH%");
        assertThat(unitDataset.getId()).isEqualTo("b9c10b86-5867-4270-b56e-ee7439fe381e");

        unitDataset.setId("blabla");
        String s = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(unitDataset);
        System.out.println(s);
    }

}