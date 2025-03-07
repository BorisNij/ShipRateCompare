package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RateMarshallingTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        RateRequest request = Fixtures.createRateRequest();

        String actualJson = objectMapper.writeValueAsString(request);

        JsonNode actualNode = objectMapper.readTree(actualJson);
        JsonNode expectedNode = objectMapper.readTree(Fixtures.RATE_REQUEST_JSON);

        String actualKeys = objectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = objectMapper.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallRateRequest() throws Exception {
        RateRequest originalRequest = Fixtures.createRateRequest();

        String json = objectMapper.writeValueAsString(originalRequest);
        RateRequest unmarshalledRequest = objectMapper.readValue(json, RateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallRateResponse() throws Exception {
        RateResponse originalResponse = Fixtures.createRateResponse();

        String json = objectMapper.writeValueAsString(originalResponse);
        RateResponse unmarshalledResponse = objectMapper.readValue(json, RateResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}