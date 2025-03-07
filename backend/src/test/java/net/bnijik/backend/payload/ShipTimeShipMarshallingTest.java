package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipTimeShipMarshallingTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeShipRequest request = Fixtures.createShipTimeShipRequest();

        String actualJson = objectMapper.writeValueAsString(request);

        JsonNode actualNode = objectMapper.readTree(actualJson);
        JsonNode expectedNode = objectMapper.readTree(Fixtures.SHIPTTIME_SHIP_REQUEST_JSON);

        String actualKeys = objectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = objectMapper.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipRequest() throws Exception {
        ShipTimeShipRequest originalRequest = Fixtures.createShipTimeShipRequest();

        String json = objectMapper.writeValueAsString(originalRequest);
        ShipTimeShipRequest unmarshalledRequest = objectMapper.readValue(json, ShipTimeShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipResponse() throws Exception {
        ShipTimeShipResponse originalResponse = Fixtures.createShipTimeShipResponse();

        String json = objectMapper.writeValueAsString(originalResponse);
        ShipTimeShipResponse unmarshalledResponse = objectMapper.readValue(json, ShipTimeShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
