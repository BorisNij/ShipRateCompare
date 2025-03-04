package net.bnijik.backend.payload.externalApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.bnijik.backend.model.QuoteModel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeRateResponse(List<QuoteModel> availableRates) {
}
