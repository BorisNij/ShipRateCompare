package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipRequest request = Fixtures.createShipRequest();

        String actualJson = OBJECT_MAPPER.writeValueAsString(request);

        JsonNode actualNode = OBJECT_MAPPER.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIP_REQUEST_JSON);

        String actualKeys = OBJECT_MAPPER.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallShipRequest() throws Exception {
        ShipRequest originalRequest = Fixtures.createShipRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        ShipRequest unmarshalledRequest = OBJECT_MAPPER.readValue(json, ShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipResponse() throws Exception {
        ShipResponse originalResponse = Fixtures.createShipResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        ShipResponse unmarshalledResponse = OBJECT_MAPPER.readValue(json, ShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
