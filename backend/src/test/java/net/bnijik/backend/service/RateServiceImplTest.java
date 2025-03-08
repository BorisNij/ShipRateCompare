package net.bnijik.backend.service;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.client.ShipTimeClient;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.converter.RateResponseConverter;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @Mock
    private ShipTimeClient shipTimeClient;

    @Mock
    private RateResponseConverter rateResponseConverter;

    @InjectMocks
    private RateServiceImpl rateService;

    @Test
    void getRates_ShouldReturnRateResponse() {
        RateRequest rateRequest = Fixtures.createRateRequest();
        ShipTimeRateRequest shipTimeRateRequest = Fixtures.createShipTimeRateRequest();
        ShipTimeRateResponse shipTimeRateResponse = Fixtures.createShipTimeRateResponse();
        RateResponse expectedResponse = Fixtures.createRateResponse();

        when(rateResponseConverter.RateRequestToShipTimeRateRequest(rateRequest)).thenReturn(shipTimeRateRequest);
        when(shipTimeClient.getRates(shipTimeRateRequest)).thenReturn(shipTimeRateResponse);
        when(rateResponseConverter.ShipTimeRateResponseToRateResponse(shipTimeRateResponse)).thenReturn(expectedResponse);

        RateResponse actualResponse = rateService.getRates(rateRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(rateResponseConverter, times(1)).RateRequestToShipTimeRateRequest(rateRequest);
        verify(shipTimeClient, times(1)).getRates(shipTimeRateRequest);
        verify(rateResponseConverter, times(1)).ShipTimeRateResponseToRateResponse(shipTimeRateResponse);
    }

}