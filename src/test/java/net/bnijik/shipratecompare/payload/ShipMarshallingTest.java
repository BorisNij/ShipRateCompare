package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Autowired
    private ObjectMapper actualObjectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipRequest request = Fixtures.createShipRequest();

        String actualJson = actualObjectMapper.writeValueAsString(request);

        JsonNode actualNode = actualObjectMapper.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIP_REQUEST_JSON);

        String actualKeys = actualObjectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }


    @Test
    void shouldMarshallAndUnmarshallShipRequest() throws Exception {
        ShipRequest originalRequest = Fixtures.createShipRequest();

        String json = OBJECT_MAPPER.writeValueAsString(originalRequest);
        ShipRequest unmarshalledRequest = actualObjectMapper.readValue(json, ShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(originalRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipResponse() throws Exception {
        ShipResponse originalResponse = Fixtures.createShipResponse();

        String json = OBJECT_MAPPER.writeValueAsString(originalResponse);
        ShipResponse unmarshalledResponse = actualObjectMapper.readValue(json, ShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(originalResponse);
    }
}
