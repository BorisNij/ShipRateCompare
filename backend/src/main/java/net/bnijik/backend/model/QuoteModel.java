package net.bnijik.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record QuoteModel(MoneyAmountModel totalCharge,
                         String carrierId,
                         String carrierName,
                         String serviceId,
                         String serviceName,
                         String quoteId) {
}
