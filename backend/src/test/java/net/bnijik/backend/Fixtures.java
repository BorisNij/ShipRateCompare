package net.bnijik.backend;

import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;
import net.bnijik.backend.model.MoneyAmountModel;
import net.bnijik.backend.model.QuoteModel;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;

import java.time.OffsetDateTime;
import java.util.List;

public class Fixtures {

    private static final RateRequest.UnitOfMeasurement unitOfMeasurement = RateRequest.UnitOfMeasurement.METRIC;
    private static final RateRequest.PackageType packageType = RateRequest.PackageType.PACKAGE;
    private static final OffsetDateTime shipDate = OffsetDateTime.now().plusDays(1);
    private static final MoneyAmountModel declaredValue = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 10000);
    private static final MoneyAmountModel totalCharge = new MoneyAmountModel(MoneyAmountModel.Currency.CAD, 5000);

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
        return LineItemModel.builder().length(10).width(10).height(10).weight(2).declaredValue(declaredValue).build();
    }

    public static RateRequest createRateRequest() {
        return RateRequest.builder()
                .fromAddress(createFromAddress())
                .toAddress(createToAddress())
                .packageType(packageType)
                .lineItems(List.of(createLineItem()))
                .unitOfMeasurement(unitOfMeasurement)
                .shipDate(shipDate)
                .build();
    }

    public static ShipTimeRateRequest createShipTimeRateRequest() {
        return ShipTimeRateRequest.builder()
                .fromAddress(createFromAddress())
                .toAddress(createToAddress())
                .packageType(packageType)
                .lineItems(List.of(createLineItem()))
                .unitOfMeasurement(unitOfMeasurement)
                .shipDate(shipDate)
                .build();
    }

    public static ShipTimeRateResponse createShipTimeRateResponse() {
        List<QuoteModel> quotes = List.of(QuoteModel.builder()
                                                  .carrierId("CARRIER123")
                                                  .carrierName("Test Carrier")
                                                  .serviceId("SERVICE456")
                                                  .serviceName("Express Delivery")
                                                  .totalCharge(totalCharge)
                                                  .quoteId("QUOTE789")
                                                  .build());

        return new ShipTimeRateResponse(quotes);
    }

    public static RateResponse createRateResponse() {
        List<QuoteModel> quotes = List.of(QuoteModel.builder()
                                                  .carrierId("CARRIER123")
                                                  .carrierName("Test Carrier")
                                                  .serviceId("SERVICE456")
                                                  .serviceName("Express Delivery")
                                                  .totalCharge(totalCharge)
                                                  .quoteId("QUOTE789")
                                                  .build());

        return new RateResponse(quotes);
    }
}