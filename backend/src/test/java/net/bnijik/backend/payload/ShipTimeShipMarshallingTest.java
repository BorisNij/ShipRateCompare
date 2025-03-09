package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalapi.ShipTimeShipResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipTimeShipMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeShipRequest request = Fixtures.createShipTimeShipRequest();

        String actualJson = OBJECT_MAPPER.writeValueAsString(request);

        JsonNode actualNode = OBJECT_MAPPER.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIPTTIME_SHIP_REQUEST_JSON);

        String actualKeys = OBJECT_MAPPER.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipRequest() throws Exception {
        ShipTimeShipRequest originalRequest = Fixtures.createShipTimeShipRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        ShipTimeShipRequest unmarshalledRequest = OBJECT_MAPPER.readValue(json, ShipTimeShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipResponse() throws Exception {
        ShipTimeShipResponse originalResponse = Fixtures.createShipTimeShipResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        ShipTimeShipResponse unmarshalledResponse = OBJECT_MAPPER.readValue(json, ShipTimeShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
