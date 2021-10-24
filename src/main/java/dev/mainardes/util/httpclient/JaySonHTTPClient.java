package dev.mainardes.util.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;

import java.io.IOException;
import java.io.InputStream;

import static dev.mainardes.util.httpclient.util.ResponseUtils.isExpectedResponse;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

public class JaySonHTTPClient {

    private HttpClient client;
    private ObjectMapper mapper;

    public JaySonHTTPClient(HttpClient client){
        this(client, new ObjectMapper());
    }

    public JaySonHTTPClient(HttpClient client, ObjectMapper mapper){
        this.client = client;
        this.mapper = mapper;
    }

    public <T> T get(String url, Class<T> responseType) throws IOException {
        return get(new HttpGet(url), responseType, null);
    }
    
    public <T> T get(String url, Class<T> responseType, String body) throws IOException {
        return get(new HttpGet(url), responseType, body);
    }

    public <T> T get(HttpGet get, Class<T> responseType, String body, Header... headers) throws IOException {
        return get(get, responseType, HttpStatus.SC_OK, body, headers);
    }

    public <T> T get(HttpGet get, Class<T> responseType, int expectedStatus, String body, Header... headers) throws IOException {
        return doRequest(get, responseType, expectedStatus, body, headers);
    }

    public <T> T post(String url, Class<T> responseType) throws IOException {
        return post(new HttpPost(url), responseType, null);
    }

    public <T> T post(String url, Class<T> responseType, String body) throws IOException {
        return post(new HttpPost(url), responseType, body);
    }

    public <T> T post(HttpPost post, Class<T> responseType, String body, Header... headers) throws IOException {
        return post(post, responseType, HttpStatus.SC_OK, body, headers);
    }

    public <T> T post(HttpPost post, Class<T> responseType, int expectedStatus, String body, Header... headers) throws IOException {
        return doRequest(post, responseType, expectedStatus, body, headers);
    }

    public <T> T put(String url, Class<T> responseType) throws IOException {
        return put(new HttpPut(url), responseType, null);
    }

    public <T> T put(String url, Class<T> responseType, String body) throws IOException {
        return put(new HttpPut(url), responseType, body);
    }

    public <T> T put(HttpPut put, Class<T> responseType, String body, Header... headers) throws IOException {
        return put(put, responseType, HttpStatus.SC_OK, body, headers);
    }

    public <T> T put(HttpPut put, Class<T> responseType, int expectedStatus, String body, Header... headers) throws IOException {
        return doRequest(put, responseType, expectedStatus, body, headers);
    }

    public <T> T delete(String url, Class<T> responseType) throws IOException {
        return delete(new HttpDelete(url), responseType, null);
    }

    public <T> T delete(String url, Class<T> responseType, String body) throws IOException {
        return delete(new HttpDelete(url), responseType, body);
    }

    public <T> T delete(HttpDelete delete, Class<T> responseType, String body, Header... headers) throws IOException {
        return delete(delete, responseType, HttpStatus.SC_OK, body, headers);
    }

    public <T> T delete(HttpDelete delete, Class<T> responseType, int expectedStatus, String body, Header... headers) throws IOException {
        return doRequest(delete, responseType, expectedStatus, body, headers);
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
        final var response = (ClassicHttpResponse)client.execute(request);

        if (isExpectedResponse(expectedStatus, APPLICATION_JSON, response)){
            try (final InputStream input = response.getEntity().getContent()){
                return mapper.readValue(input, responseType);
            }
        }
        return null;
    }

    protected void setClient(HttpClient client) {
        this.client = client;
    }

    public HttpClient getClient() {
        return client;
    }

    protected void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
