package net.bnijik.shipratecompare.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import net.bnijik.shipratecompare.service.RateService;
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

@WebMvcTest(RateController.class)
class RateControllerTest {

    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;

    @Autowired
    private ObjectMapper actualObjectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private RateService rateService;

    @Test
    void getRates_ShouldReturnSuccessfulResponse() throws Exception {
        RateRequest rateRequest = Fixtures.createRateRequest();
        RateResponse expectedResponse = Fixtures.createRateResponse();
        when(rateService.getRates(any(RateRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(rateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(actualObjectMapper.writeValueAsString(expectedResponse)));
    }


}
