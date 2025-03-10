package net.bnijik.shipratecompare.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class ShipTimeClientLogger implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ShipTimeClientLogger.class);
    private static final int MAX_CONTENT_LENGTH = 1000;

    @Value("${client.logging.include-headers:false}")
    private boolean includeHeaders;

    @Value("${client.logging.enabled:true}")
    private boolean loggingEnabled;

    @Value("${client.logging.truncate-logs:true}")
    private boolean truncateLogs;

    @Value("${client.logging.single-line:true}")
    private boolean singleLine;

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        if (loggingEnabled) {
            logRequest(request, body);
        }
        ClientHttpResponse response = execution.execute(request, body);
        if (loggingEnabled) {
            return logResponse(request, response);
        }
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Request: {} {}", request.getMethod(), request.getURI());
        if (includeHeaders) {
            logHeaders(request.getHeaders());
        }
        if (body.length > 0) {
            String content = new String(body, StandardCharsets.UTF_8);
            log.info("Request body: {}", formatContent(content));
        }
    }

    private ClientHttpResponse logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        log.info("Response status: {}", response.getStatusCode());
        if (includeHeaders) {
            logHeaders(response.getHeaders());
        }

        byte[] responseBody = response.getBody().readAllBytes();
        if (responseBody.length > 0) {
            String content = new String(responseBody, StandardCharsets.UTF_8);
            log.info("Response body: {}", formatContent(content));
        }

        return new BufferingClientHttpResponseWrapper(response, responseBody);
    }

    private void logHeaders(HttpHeaders headers) {
        headers.forEach((name, values) -> values.forEach(value -> log.info("{}={}",
                                                                           name,
                                                                           truncateIfNeeded(value,
                                                                                            MAX_CONTENT_LENGTH))));
    }

    private String formatContent(String content) {
        String result = content;
        if (singleLine) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Parse to minify the JSON content
                JsonNode jsonNode = mapper.readTree(content);
                result = mapper.writeValueAsString(jsonNode);
            } catch (JsonProcessingException e) {
                // Not a JSON content, apply simple whitespace removal
                result = result.replaceAll("\\s+", " ").trim();
            }
        }
        return truncateIfNeeded(result, MAX_CONTENT_LENGTH);
    }


    private String truncateIfNeeded(String content, int maxLength) {
        if (!truncateLogs || content.length() <= maxLength) {
            return content;
        }
        return content.substring(0, maxLength) + "... (truncated)";
    }

    private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {
        private final ClientHttpResponse response;
        private final byte[] body;

        public BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
            this.response = response;
            this.body = body;
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(body);
        }

        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }
    }
}