package net.bnijik.backend.payload.externalApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;
import net.bnijik.backend.payload.RateRequest;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeRateRequest(AddressModel fromAddress,
                                  AddressModel toAddress,
                                  RateRequest.PackageType packageType,
                                  List<LineItemModel> lineItems,
                                  RateRequest.UnitOfMeasurement unitOfMeasurement,
                                  OffsetDateTime shipDate) {

}

