package net.bnijik.backend.client;

import net.bnijik.backend.Fixtures;
import net.bnijik.backend.config.ShipTimeClientConfig;
import net.bnijik.backend.config.ShipTimeClientLogger;
import net.bnijik.backend.model.QuoteModel;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {ShipTimeClientConfig.class, ShipTimeClientLogger.class}, initializers = ConfigDataApplicationContextInitializer.class)
class ShipTimeRateClientTest {

    @Autowired
    private ShipTimeRateClient shipTimeRateClient;

    @Test
    void getRates_ShouldGetRatesSuccessfully() {
        ShipTimeRateRequest shipTimeRateRequest = Fixtures.createShipTimeRateRequest();

        var shipTimeRates = shipTimeRateClient.getRates(shipTimeRateRequest);

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

    }
}