package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateMarshallingTest {

    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        RateRequest request = Fixtures.createRateRequest();

        String actualJson = OBJECT_MAPPER.writeValueAsString(request);

        JsonNode actualNode = OBJECT_MAPPER.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.RATE_REQUEST_JSON);

        String actualKeys = OBJECT_MAPPER.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallRateRequest() throws Exception {
        RateRequest originalRequest = Fixtures.createRateRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        RateRequest unmarshalledRequest = OBJECT_MAPPER.readValue(json, RateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallRateResponse() throws Exception {
        RateResponse originalResponse = Fixtures.createRateResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        RateResponse unmarshalledResponse = OBJECT_MAPPER.readValue(json, RateResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}