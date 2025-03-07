package net.bnijik.backend;

import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;
import net.bnijik.backend.model.MoneyAmountModel;
import net.bnijik.backend.model.QuoteModel;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

public class Fixtures {

    public static final String SHIP_REQUEST_JSON;
    public static final String SHIPTTIME_RATE_REQUEST_JSON;
    public static final String SHIPTTIME_RATE_RESPONSE_JSON;
    public static final String RATE_REQUEST_JSON;
    public static final String RATE_RESPONSE_JSON;
    public static final String SHIPTTIME_SHIP_REQUEST_JSON;
    private static final RateRequest.UnitOfMeasurement UNIT_OF_MEASUREMENT = RateRequest.UnitOfMeasurement.METRIC;
    private static final RateRequest.PackageType PACKAGE_TYPE = RateRequest.PackageType.PACKAGE;
    private static final MoneyAmountModel DECLARED_VALUE = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 10000);
    private static final MoneyAmountModel TOTAL_CHARGE = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 5000);
    private static final ZonedDateTime SHIP_DATE = ZonedDateTime.parse("2025-03-08T02:42:15.987Z");
    private static final String QUOTE_ID = "QUOTE789";

    static {
        try {
            SHIPTTIME_RATE_REQUEST_JSON = Files.readString(Path.of(ClassLoader.getSystemResource(
                    "test-data/shiptime_rate_request.json").toURI()));
            SHIPTTIME_RATE_RESPONSE_JSON = Files.readString(Path.of(ClassLoader.getSystemResource(
                    "test-data/shiptime_rate_response.json").toURI()));
            RATE_REQUEST_JSON = Files.readString(Path.of(ClassLoader.getSystemResource("test-data/rate_request.json")
                                                                 .toURI()));
            RATE_RESPONSE_JSON = Files.readString(Path.of(ClassLoader.getSystemResource("test-data/rate_response.json")
                                                                  .toURI()));
            SHIP_REQUEST_JSON = Files.readString(Path.of(ClassLoader.getSystemResource("test-data/ship_request.json")
                                                                 .toURI()));
            SHIPTTIME_SHIP_REQUEST_JSON = Files.readString(Path.of(ClassLoader.getSystemResource(
                    "test-data/shiptime_ship_request.json").toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static AddressModel createFromAddress() {
        return AddressModel.builder()
                .companyName("Test Company")
                .streetAddress("123 Test St")
                .city("Toronto")
                .countryCode("CA")
                .state("ON")
                .postalCode("M5S2C6")
                .attention("John Doe")
                .phone("14165868000")
                .build();
    }

    public static AddressModel createToAddress() {
        return AddressModel.builder()
                .companyName("Receiver Company")
                .streetAddress("456 Recv St")
                .city("Calgary")
                .countryCode("CA")
                .state("AB")
                .postalCode("T2P1K1")
                .attention("Jane Smith")
                .phone("14032667171")
                .build();
    }

    public static LineItemModel createLineItem() {
        return LineItemModel.builder().length(10).width(10).height(10).weight(2).declaredValue(DECLARED_VALUE).build();
    }

    public static RateRequest createRateRequest() {
        return RateRequest.builder()
                .fromAddress(createFromAddress())
                .toAddress(createToAddress())
                .packageType(PACKAGE_TYPE)
                .lineItems(List.of(createLineItem()))
                .unitOfMeasurement(UNIT_OF_MEASUREMENT)
                .shipDate(SHIP_DATE)
                .build();
    }

    public static ShipTimeRateRequest createShipTimeRateRequest() {
        return ShipTimeRateRequest.builder()
                .fromAddress(createFromAddress())
                .toAddress(createToAddress())
                .packageType(PACKAGE_TYPE)
                .lineItems(List.of(createLineItem()))
                .unitOfMeasurement(UNIT_OF_MEASUREMENT)
                .shipDate(SHIP_DATE)
                .build();
    }

    public static ShipTimeRateResponse createShipTimeRateResponse() {
        List<QuoteModel> quotes = List.of(QuoteModel.builder()
                                                  .carrierId("CARRIER123")
                                                  .carrierName("Test Carrier")
                                                  .serviceId("SERVICE456")
                                                  .serviceName("Express Delivery")
                                                  .totalCharge(TOTAL_CHARGE)
                                                  .quoteId(QUOTE_ID)
                                                  .build());

        return new ShipTimeRateResponse(quotes);
    }

    public static RateResponse createRateResponse() {
        List<QuoteModel> quotes = List.of(QuoteModel.builder()
                                                  .carrierId("CARRIER123")
                                                  .carrierName("Test Carrier")
                                                  .serviceId("SERVICE456")
                                                  .serviceName("Express Delivery")
                                                  .totalCharge(TOTAL_CHARGE)
                                                  .quoteId(QUOTE_ID)
                                                  .build());

        return new RateResponse(quotes);
    }

    public static ShipRequest createShipRequest() {
        return new ShipRequest(QUOTE_ID);
    }

    public static ShipResponse createShipResponse() throws URISyntaxException, MalformedURLException {
        return new ShipResponse(4830519,
                                List.of("123456789012"),
                                new URI("http://sandboxrestapi.shiptime.com/rest/shipments/4830519/label").toURL());
    }

    public static ShipTimeShipRequest createShipTimeShipRequest() {
        return new ShipTimeShipRequest("QUOTE789");
    }

    public static ShipTimeShipResponse createShipTimeShipResponse() throws URISyntaxException, MalformedURLException {
        return new ShipTimeShipResponse(4830519,
                                        List.of("123456789012"),
                                        new URI("http://sandboxrestapi.shiptime.com/rest/shipments/4830519/label").toURL());
    }

}