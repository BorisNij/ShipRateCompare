package net.bnijik.backend.payload;

import net.bnijik.backend.model.QuoteModel;

import java.util.List;

public record RateResponse(List<QuoteModel> availableRates) {
}
