package net.bnijik.backend.client;

import net.bnijik.backend.config.ShipTimeClientConfig;
import net.bnijik.backend.config.ShipTimeClientLogger;
import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;
import net.bnijik.backend.model.MoneyAmountModel;
import net.bnijik.backend.model.QuoteModel;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {ShipTimeClientConfig.class, ShipTimeClientLogger.class}, initializers = ConfigDataApplicationContextInitializer.class)
class ShipTimeRateClientTest {

    @Autowired
    private ShipTimeRateClient shipTimeRateClient;

    @Test
    void shouldGetRatesSuccessfully() {
        // Given
        AddressModel fromAddress = AddressModel.builder()
                .companyName("Test Company")
                .streetAddress("123 Test St")
                .city("Toronto")
                .countryCode("CA")
                .state("ON")
                .postalCode("M5S2C6")
                .attention("John Doe")
                .phone("14165868000")
                .build();

        AddressModel toAddress = AddressModel.builder()
                .companyName("Receiver Company")
                .streetAddress("456 Recv St")
                .city("Calgary")
                .countryCode("CA")
                .state("AB")
                .postalCode("T2P1K1")
                .attention("Jane Smith")
                .phone("14032667171")
                .build();

        MoneyAmountModel declaredValue = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 10000 // $100.00
        );

        LineItemModel lineItem = LineItemModel.builder()
                .length(10)
                .width(10)
                .height(10)
                .weight(2)
                .declaredValue(declaredValue)
                .build();

        ShipTimeRateRequest shipTimeRateRequest = ShipTimeRateRequest.builder()
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .packageType(RateRequest.PackageType.PACKAGE)
                .lineItems(List.of(lineItem))
                .unitOfMeasurement(RateRequest.UnitOfMeasurement.METRIC)
                .shipDate(OffsetDateTime.now().plusDays(1))
                .build();


        // When
        var shipTimeRates = shipTimeRateClient.getRates(shipTimeRateRequest);

        // Then
        assertThat(shipTimeRates).isNotNull();
        assertThat(shipTimeRates).isNotNull();
        assertThat(shipTimeRates.availableRates()).isNotEmpty();

        // Verify quote details
        QuoteModel firstQuote = shipTimeRates.availableRates().get(0);
        assertThat(firstQuote.carrierId()).isNotEmpty();
        assertThat(firstQuote.carrierName()).isNotEmpty();
        assertThat(firstQuote.serviceId()).isNotEmpty();
        assertThat(firstQuote.serviceName()).isNotEmpty();
        assertThat(firstQuote.totalCharge()).isNotNull();
        assertThat(firstQuote.quoteId()).isNotEmpty();

    }
}