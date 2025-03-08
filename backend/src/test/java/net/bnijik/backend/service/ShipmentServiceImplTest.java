package net.bnijik.backend.service;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.client.ShipTimeClient;
import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.payload.converter.ShipResponseConverter;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;
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

        when(shipResponseConverter.ShipRequestToShipTimeShipRequest(shipRequest)).thenReturn(shipTimeShipRequest);
        when(shipTimeClient.createShipments(shipTimeShipRequest)).thenReturn(shipTimeShipResponse);
        when(shipResponseConverter.ShipTimeShipResponseToShipResponse(shipTimeShipResponse)).thenReturn(expectedResponse);

        ShipResponse actualResponse = shipService.createShipments(shipRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(shipResponseConverter, times(1)).ShipRequestToShipTimeShipRequest(shipRequest);
        verify(shipTimeClient, times(1)).createShipments(shipTimeShipRequest);
        verify(shipResponseConverter, times(1)).ShipTimeShipResponseToShipResponse(shipTimeShipResponse);
    }

}