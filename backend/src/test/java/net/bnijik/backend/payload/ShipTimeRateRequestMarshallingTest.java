package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipTimeRateRequestMarshallingTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeRateRequest request = Fixtures.createShipTimeRateRequest();

        String actualJson = objectMapper.writeValueAsString(request);

        JsonNode actualNode = objectMapper.readTree(actualJson);
        JsonNode expectedNode = objectMapper.readTree(Fixtures.SHIPTTIME_RATE_REQUEST_JSON);

        String actualKeys = objectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = objectMapper.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateRequest() throws Exception {
        ShipTimeRateRequest originalRequest = Fixtures.createShipTimeRateRequest();

        String json = objectMapper.writeValueAsString(originalRequest);
        ShipTimeRateRequest unmarshalledRequest = objectMapper.readValue(json, ShipTimeRateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateResponse() throws Exception {
        ShipTimeRateResponse originalResponse = Fixtures.createShipTimeRateResponse();

        String json = objectMapper.writeValueAsString(originalResponse);
        ShipTimeRateResponse unmarshalledResponse = objectMapper.readValue(json, ShipTimeRateResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
