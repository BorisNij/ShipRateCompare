package net.bnijik.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record LineItemModel(int length, int width, int height, int weight, MoneyAmountModel declaredValue) {
}
