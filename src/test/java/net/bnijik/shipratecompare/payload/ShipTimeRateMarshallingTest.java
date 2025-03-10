package net.bnijik.shipratecompare.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ShipTimeRateMarshallingTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Autowired
    private ObjectMapper actualObjectMapper;

    @Test
    void shouldContainCorrectJsonStructure() throws Exception {
        ShipTimeRateRequest request = Fixtures.createShipTimeRateRequest();

        String actualJson = actualObjectMapper.writeValueAsString(request);

        JsonNode actualNode = actualObjectMapper.readTree(actualJson);
        JsonNode expectedNode = OBJECT_MAPPER.readTree(Fixtures.SHIPTTIME_RATE_REQUEST_JSON);

        String actualKeys = actualObjectMapper.writeValueAsString(actualNode.fieldNames());
        String expectedKeys = OBJECT_MAPPER.writeValueAsString(expectedNode.fieldNames());

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateRequest() throws Exception {
        ShipTimeRateRequest expectedRequest = Fixtures.createShipTimeRateRequest();

        String json = OBJECT_MAPPER.writeValueAsString(expectedRequest);
        ShipTimeRateRequest unmarshalledRequest = actualObjectMapper.readValue(json, ShipTimeRateRequest.class);

        assertThat(unmarshalledRequest).usingRecursiveComparison().isEqualTo(expectedRequest);
    }

    @Test
    void shouldMarshallAndUnmarshallShipTimeRateResponse() throws Exception {
        ShipTimeRateResponse expectedResponse = Fixtures.createShipTimeRateResponse();

        String json = OBJECT_MAPPER.writeValueAsString(expectedResponse);
        ShipTimeRateResponse unmarshalledResponse = actualObjectMapper.readValue(json, ShipTimeRateResponse.class);

        assertThat(unmarshalledResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
