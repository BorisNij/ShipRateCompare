package net.bnijik.shipratecompare.service;

import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.client.ShipTimeClient;
import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;
import net.bnijik.shipratecompare.payload.converter.ShipResponseConverter;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {
    @Mock
    private ShipTimeClient shipTimeClient;

    @Mock
    private ShipResponseConverter shipResponseConverter;

    @InjectMocks
    private ShipmentServiceImpl shipService;

    @Test
    void getShips_ShouldReturnShipResponse() throws MalformedURLException, URISyntaxException {
        ShipRequest shipRequest = Fixtures.createShipRequest();
        ShipTimeShipRequest shipTimeShipRequest = Fixtures.createShipTimeShipRequest();
        ShipTimeShipResponse shipTimeShipResponse = Fixtures.createShipTimeShipResponse();
        ShipResponse expectedResponse = Fixtures.createShipResponse();

        when(shipResponseConverter.shipRequestToShipTimeShipRequest(shipRequest)).thenReturn(shipTimeShipRequest);
        when(shipTimeClient.createShipments(shipTimeShipRequest)).thenReturn(shipTimeShipResponse);
        when(shipResponseConverter.shipTimeShipResponseToShipResponse(shipTimeShipResponse)).thenReturn(expectedResponse);

        ShipResponse actualResponse = shipService.createShipments(shipRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(shipResponseConverter, times(1)).shipRequestToShipTimeShipRequest(shipRequest);
        verify(shipTimeClient, times(1)).createShipments(shipTimeShipRequest);
        verify(shipResponseConverter, times(1)).shipTimeShipResponseToShipResponse(shipTimeShipResponse);
    }

}