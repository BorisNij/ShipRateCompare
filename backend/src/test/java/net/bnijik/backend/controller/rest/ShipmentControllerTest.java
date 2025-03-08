package net.bnijik.backend.controller.rest;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentControllerTest {
    @Mock
    private ShipmentService shipService;

    @InjectMocks
    private ShipmentController shipController;

    @Test
    void getShips_ShouldReturnSuccessfulResponse() throws MalformedURLException, URISyntaxException {
        ShipRequest shipRequest = Fixtures.createShipRequest();
        ShipResponse expectedResponse = Fixtures.createShipResponse();
        when(shipService.createShipments(shipRequest)).thenReturn(expectedResponse);

        ResponseEntity<ShipResponse> responseEntity = shipController.createShipments(shipRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(shipService, times(1)).createShipments(shipRequest);
    }
}