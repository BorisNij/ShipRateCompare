package net.bnijik.backend.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipMarshallingTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipRequest request = Fixtures.createShipRequest();

        String actualJson = objectMapper.writeValueAsString(request);

        JsonNode actualNode = objectMapper.readTree(actualJson);
        JsonNode expectedNode = objectMapper.readTree(Fixtures.SHIP_REQUEST_JSON);

        String actualKeys = objectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = objectMapper.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallShipRequest() throws Exception {
        ShipRequest originalRequest = Fixtures.createShipRequest();

        String json = objectMapper.writeValueAsString(originalRequest);
        ShipRequest unmarshalledRequest = objectMapper.readValue(json, ShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipResponse() throws Exception {
        ShipResponse originalResponse = Fixtures.createShipResponse();

        String json = objectMapper.writeValueAsString(originalResponse);
        ShipResponse unmarshalledResponse = objectMapper.readValue(json, ShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
