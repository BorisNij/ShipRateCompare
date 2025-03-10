package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipTimeRateMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeRateRequest request = Fixtures.createShipTimeRateRequest();

        String actualJson = OBJECT_MAPPER.writeValueAsString(request);

        JsonNode actualNode = OBJECT_MAPPER.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIPTTIME_RATE_REQUEST_JSON);

        String actualKeys = OBJECT_MAPPER.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateRequest() throws Exception {
        ShipTimeRateRequest originalRequest = Fixtures.createShipTimeRateRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        ShipTimeRateRequest unmarshalledRequest = OBJECT_MAPPER.readValue(json, ShipTimeRateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateResponse() throws Exception {
        ShipTimeRateResponse originalResponse = Fixtures.createShipTimeRateResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        ShipTimeRateResponse unmarshalledResponse = OBJECT_MAPPER.readValue(json, ShipTimeRateResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
