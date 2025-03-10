package net.bnijik.shipratecompare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bnijik.shipratecompare.model.AddressModel;
import net.bnijik.shipratecompare.model.LineItemModel;
import net.bnijik.shipratecompare.model.MoneyAmountModel;
import net.bnijik.shipratecompare.model.QuoteModel;
import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Fixtures {


    private static final RateRequest.UnitOfMeasurement UNIT_OF_MEASUREMENT = RateRequest.UnitOfMeasurement.METRIC;
    private static final RateRequest.PackageType PACKAGE_TYPE = RateRequest.PackageType.PACKAGE;
    private static final MoneyAmountModel DECLARED_VALUE = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 10000);
    private static final MoneyAmountModel TOTAL_CHARGE = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 5000);
    private static final String QUOTE_ID = "QUOTE789";
    private static final ZonedDateTime SHIP_DATE = ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS);

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final String SHIP_REQUEST_JSON = getShipRequestJson();
    public static final String SHIPTTIME_RATE_RESPONSE_JSON = getShipTimeRateResponseJson();
    public static final String SHIPTTIME_SHIP_REQUEST_JSON = getShipTimeShipRequestJson();
    public static final String SHIPTTIME_SHIP_RESPONSE_JSON = getShipTimeShipResponseJson();
    public static final String RATE_REQUEST_JSON = getRateRequestJson();
    public static final String SHIPTTIME_RATE_REQUEST_JSON = getShipTimeRateRequestJson();

    public static String getRateRequestJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createRateRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRateResponseJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createRateResponse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipRequestJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipResponseJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipResponse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipTimeRateRequestJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipTimeRateRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipTimeRateResponseJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipTimeRateResponse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipTimeShipRequestJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipTimeShipRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getShipTimeShipResponseJson() {
        try {
            return OBJECT_MAPPER.writeValueAsString(createShipTimeShipResponse());
        } catch (Exception e) {
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