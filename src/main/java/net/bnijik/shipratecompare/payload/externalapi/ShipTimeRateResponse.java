package net.bnijik.shipratecompare.payload.externalapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.bnijik.shipratecompare.model.QuoteModel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeRateResponse(List<QuoteModel> availableRates) {
}
