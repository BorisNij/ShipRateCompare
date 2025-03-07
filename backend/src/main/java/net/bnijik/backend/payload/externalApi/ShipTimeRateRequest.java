package net.bnijik.backend.payload.externalApi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;
import net.bnijik.backend.payload.RateRequest;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeRateRequest(
        @JsonProperty("from") AddressModel fromAddress,
        @JsonProperty("to") AddressModel toAddress,
        RateRequest.PackageType packageType,
        List<LineItemModel> lineItems,
        RateRequest.UnitOfMeasurement unitOfMeasurement,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        ZonedDateTime shipDate) {

}

