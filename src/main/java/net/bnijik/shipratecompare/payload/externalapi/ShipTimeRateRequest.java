package net.bnijik.shipratecompare.payload.externalapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.bnijik.shipratecompare.model.AddressModel;
import net.bnijik.shipratecompare.model.LineItemModel;
import net.bnijik.shipratecompare.payload.RateRequest;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Accessors(fluent = true)
public record ShipTimeRateRequest(
        @JsonProperty("from") AddressModel fromAddress,
        @JsonProperty("to") AddressModel toAddress,
        RateRequest.PackageType packageType,
        List<LineItemModel> lineItems,
        RateRequest.UnitOfMeasurement unitOfMeasurement,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        ZonedDateTime shipDate) {

}

