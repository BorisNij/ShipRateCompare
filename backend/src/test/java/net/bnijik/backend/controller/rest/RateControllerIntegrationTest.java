package net.bnijik.backend.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.backend.Fixtures;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(RateController.class)

//@SpringJUnitConfig(classes = {ShipTimeClientConfig.class, ShipTimeClientLogger.class, RateServiceImpl.class, RateController.class, RateResponseConverterImpl.class}, initializers = ConfigDataApplicationContextInitializer.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;


//    @Autowired
//    private RateController rateController;

    @Test
    void getRates_ShouldReturnSuccessfulResponse() throws Exception {
        RateRequest rateRequest = Fixtures.createRateRequest();

        String responseJson = mockMvc.perform(post("/api/rates")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(OBJECT_MAPPER.writeValueAsString(rateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RateResponse actualResponse = OBJECT_MAPPER.readValue(responseJson, RateResponse.class);
        assertThat(actualResponse.availableRates()).isNotEmpty();
    }
}
