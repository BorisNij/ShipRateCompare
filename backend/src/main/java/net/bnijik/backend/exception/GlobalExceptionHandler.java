package net.bnijik.backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import net.bnijik.backend.model.ErrorModel;
import net.bnijik.backend.payload.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @ExceptionHandler(ConfigurationException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialConfig(ConfigurationException ex) {
        return new ResponseEntity<>(new ErrorResponse(List.of(ex.getMessage())),
                                    new HttpHeaders(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<ErrorResponse> handleBadRequest(HttpClientErrorException.BadRequest ex,
                                                             HttpServletRequest request) {
        ErrorModel errorModel = Optional.ofNullable(ex.getResponseBodyAsString())
                .map(body -> parseErrorBody(body, request))
                .orElseGet(() -> new ErrorModel(false, List.of(ex.getMessage())));

        return new ResponseEntity<>(new ErrorResponse(errorModel.messages()),
                                    new HttpHeaders(),
                                    HttpStatus.BAD_REQUEST);
    }

    private ErrorModel parseErrorBody(String body, HttpServletRequest request) {
        try {
            return objectMapper.readValue(body, ErrorModel.class);
        } catch (JsonProcessingException e) {
            final String baseUrl = request.getRequestURL().toString();
            LOG.error("Failed to parse error response body for request URL {}: {}", baseUrl, body, e);
            return new ErrorModel(false, List.of("Error parsing external service response"));
        }
    }
}
