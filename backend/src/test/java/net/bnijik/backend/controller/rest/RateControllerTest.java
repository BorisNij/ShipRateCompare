package net.bnijik.backend.controller.rest;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.service.RateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateControllerTest {

    @Mock
    private RateService rateService;

    @InjectMocks
    private RateController rateController;

    @Test
    void getRates_ShouldReturnSuccessfulResponse() {
        RateRequest rateRequest = Fixtures.createRateRequest();
        RateResponse expectedResponse = Fixtures.createRateResponse();
        when(rateService.getRates(rateRequest)).thenReturn(expectedResponse);

        ResponseEntity<RateResponse> responseEntity = rateController.getRates(rateRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(rateService, times(1)).getRates(rateRequest);
    }

}