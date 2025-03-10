package net.bnijik.shipratecompare.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.config.CacheConfig;
import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import net.bnijik.shipratecompare.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateController.class)
@Import(CacheConfig.class)
public class RateControllerCacheTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RateService rateService;
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(cacheManager.getCache("rates")).clear();
    }


    @Test
    void getRates_ShouldCacheResponse() throws Exception {
        RateResponse rateResponse = Fixtures.createRateResponse();
        when(rateService.getRates(any(RateRequest.class))).thenReturn(rateResponse);

        mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON).content(Fixtures.RATE_REQUEST_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON).content(Fixtures.RATE_REQUEST_JSON))
                .andExpect(status().isOk());

        verify(rateService, times(1)).getRates(any(RateRequest.class));
    }

    @Test
    void getRates_DifferentRequests_ShouldNotUseCache(@Autowired ObjectMapper objectMapper) throws Exception {
        RateResponse rateResponse = Fixtures.createRateResponse();
        when(rateService.getRates(any(RateRequest.class))).thenReturn(rateResponse);

        RateRequest originalRequest = Fixtures.createRateRequest();
        RateRequest modifiedRequest = RateRequest.builder()
                .fromAddress(originalRequest.fromAddress())
                .toAddress(originalRequest.toAddress())
                .packageType(originalRequest.packageType())
                .lineItems(List.of(originalRequest.lineItems().get(0), originalRequest.lineItems().get(0)))
                .unitOfMeasurement(originalRequest.unitOfMeasurement())
                .shipDate(originalRequest.shipDate())
                .build();
        String modifiedRequestJson = objectMapper.writeValueAsString(modifiedRequest);

        mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON).content(Fixtures.RATE_REQUEST_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON).content(modifiedRequestJson))
                .andExpect(status().isOk());

        verify(rateService, times(2)).getRates(any(RateRequest.class));
    }
}
