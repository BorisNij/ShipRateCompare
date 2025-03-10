package net.bnijik.shipratecompare.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bnijik.shipratecompare.Fixtures;
import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIf(expression = "#{environment['spring.profiles.active'].matches('.*devlocal.*|.*sandbox.*')}", loadContext = true)
public class RateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${shiptime.base-url}")
    private String baseUrl;

    private final ObjectMapper OBJECT_MAPPER = Fixtures.OBJECT_MAPPER;
    @Autowired
    private ObjectMapper actualObjectMapper;

    private boolean isServerAccessible() {
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            return connection.getResponseCode() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void getRates_ShouldReturnSuccessfulResponse() throws Exception {
        Assumptions.assumeTrue(isServerAccessible(), "'" + baseUrl + "' is not accessible, skipping test");

        RateRequest rateRequest = Fixtures.createRateRequest();

        String responseJson = mockMvc.perform(post("/api/rates").contentType(MediaType.APPLICATION_JSON)
                                                      .content(OBJECT_MAPPER.writeValueAsString(rateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RateResponse actualResponse = actualObjectMapper.readValue(responseJson, RateResponse.class);
        assertThat(actualResponse.availableRates()).isNotEmpty();
    }
}