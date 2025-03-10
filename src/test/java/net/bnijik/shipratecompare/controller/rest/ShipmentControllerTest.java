package net.bnijik.shipratecompare.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;
import net.bnijik.shipratecompare.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {
    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ShipmentService shipService;

    @Test
    void getShips_ShouldReturnSuccessfulResponse() throws Exception {
        ShipRequest shipRequest = Fixtures.createShipRequest();
        ShipResponse expectedResponse = Fixtures.createShipResponse();
        when(shipService.createShipments(any(ShipRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/shipments").contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(shipRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expectedResponse)));
    }
}
