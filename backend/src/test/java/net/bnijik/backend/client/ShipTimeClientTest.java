package net.bnijik.backend.client;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.config.ShipTimeClientConfig;
import net.bnijik.backend.config.ShipTimeClientLogger;
import net.bnijik.backend.model.QuoteModel;
import net.bnijik.backend.payload.externalapi.ShipTimeShipRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringJUnitConfig(classes = {ShipTimeClientConfig.class, ShipTimeClientLogger.class}, initializers = ConfigDataApplicationContextInitializer.class)
class ShipTimeClientTest {

    @Autowired
    RestClient.Builder restClientBuilder;

    private MockRestServiceServer shipTimeMockServer;

    private ShipTimeClient shipTimeClient;

    @BeforeEach
    void setUp() {
        shipTimeMockServer = MockRestServiceServer.bindTo(restClientBuilder).build();

        RestClient restClient = restClientBuilder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        shipTimeClient = factory.createClient(ShipTimeClient.class);
    }


    @Test
    void getRates_ShouldGetRatesSuccessfully() {
        shipTimeMockServer.expect(requestTo(endsWith(ShipTimeClientConfig.API_PATH + ShipTimeClientConfig.RATE_RESOURCE)))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(Fixtures.SHIPTTIME_RATE_REQUEST_JSON))
                .andRespond(withSuccess(Fixtures.SHIPTTIME_RATE_RESPONSE_JSON, MediaType.APPLICATION_JSON));

        final var shipTimeRates = shipTimeClient.getRates(Fixtures.createShipTimeRateRequest());

        assertThat(shipTimeRates).isNotNull();
        assertThat(shipTimeRates).isNotNull();
        assertThat(shipTimeRates.availableRates()).isNotEmpty();

        QuoteModel firstQuote = shipTimeRates.availableRates().get(0);
        assertThat(firstQuote.carrierId()).isNotEmpty();
        assertThat(firstQuote.carrierName()).isNotEmpty();
        assertThat(firstQuote.serviceId()).isNotEmpty();
        assertThat(firstQuote.serviceName()).isNotEmpty();
        assertThat(firstQuote.totalCharge()).isNotNull();
        assertThat(firstQuote.quoteId()).isNotEmpty();
        shipTimeMockServer.verify();
    }

    @Test
    void getShips_ShouldCreateShipmentsSuccessfully() {
        shipTimeMockServer.expect(requestTo(endsWith(ShipTimeClientConfig.API_PATH + ShipTimeClientConfig.SHIPMENT_RESOURCE)))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(Fixtures.SHIPTTIME_SHIP_REQUEST_JSON))
                .andRespond(withSuccess(Fixtures.SHIPTTIME_SHIP_RESPONSE_JSON, MediaType.APPLICATION_JSON));

        final ShipTimeShipRequest shipTimeShipRequest = Fixtures.createShipTimeShipRequest();
        final var response = shipTimeClient.createShipments(shipTimeShipRequest);

        assertThat(response).isNotNull();
        assertThat(response.shipId()).isPositive();
        assertThat(response.trackingNumbers()).isNotEmpty();
        assertThat(response.labelUrl()).isNotNull();
        shipTimeMockServer.verify();
    }
}