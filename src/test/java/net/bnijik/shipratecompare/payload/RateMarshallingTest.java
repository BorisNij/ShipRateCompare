package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RateMarshallingTest {

    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Autowired
    private ObjectMapper actualObjectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        RateRequest request = Fixtures.createRateRequest();

        String actualJson = actualObjectMapper.writeValueAsString(request);

        JsonNode actualNode = actualObjectMapper.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.RATE_REQUEST_JSON);

        String actualKeys = actualObjectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallRateRequest() throws Exception {
        RateRequest originalRequest = Fixtures.createRateRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        RateRequest unmarshalledRequest = actualObjectMapper.readValue(json, RateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallRateResponse() throws Exception {
        RateResponse originalResponse = Fixtures.createRateResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        RateResponse expectedUnmarshalledRequest = actualObjectMapper.readValue(json, RateResponse.class);

        assertThat(expectedUnmarshalledRequest).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}