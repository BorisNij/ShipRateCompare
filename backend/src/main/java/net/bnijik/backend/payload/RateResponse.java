package net.bnijik.backend.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.bnijik.backend.model.QuoteModel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RateResponse(List<QuoteModel> availableRates) {
}
