package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipTimeShipMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Autowired
    private ObjectMapper actualObjectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeShipRequest request = Fixtures.createShipTimeShipRequest();

        String actualJson = actualObjectMapper.writeValueAsString(request);

        JsonNode actualNode = actualObjectMapper.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIPTTIME_SHIP_REQUEST_JSON);

        String actualKeys = actualObjectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipRequest() throws Exception {
        ShipTimeShipRequest expectedRequest = Fixtures.createShipTimeShipRequest();

        String json = OBJECT_MAPPER.writeValueAsString(expectedRequest);
        ShipTimeShipRequest unmarshalledRequest = actualObjectMapper.readValue(json, ShipTimeShipRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(expectedRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeShipResponse() throws Exception {
        ShipTimeShipResponse expectedResponse = Fixtures.createShipTimeShipResponse();

        String json = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        ShipTimeShipResponse unmarshalledResponse = actualObjectMapper.readValue(json, ShipTimeShipResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
