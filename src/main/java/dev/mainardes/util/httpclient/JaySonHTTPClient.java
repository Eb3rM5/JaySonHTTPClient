package dev.mainardes.util.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;

import java.io.IOException;
import java.io.InputStream;

import static dev.mainardes.util.httpclient.util.ResponseUtils.isExpectedResponse;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

public final class JaySonHTTPClient {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public JaySonHTTPClient(HttpClient client, ObjectMapper mapper){
        this.client = client;
        this.mapper = mapper;
    }

    public <T> T doRequest(BasicClassicHttpRequest request, Class<T> responseType, int expectedStatus, String body, Header... headers) throws IOException {
        if (body != null){
            request.setEntity(HttpEntities.create(body, APPLICATION_JSON));
        }
        if (headers != null && headers.length > 0){
            request.setHeaders(headers);
        }
        return doRequest(request, responseType, expectedStatus);
    }

    public <T> T doRequest(BasicClassicHttpRequest request, Class<T> responseType, int expectedStatus) throws IOException {
        final var response = (BasicClassicHttpResponse)client.execute(request);
        if (isExpectedResponse(expectedStatus, APPLICATION_JSON, response)){
            try (final InputStream input = response.getEntity().getContent()){
                return mapper.readValue(input, responseType);
            }
        }
        return null;
    }

}
