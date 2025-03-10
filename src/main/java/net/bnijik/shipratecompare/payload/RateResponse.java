package net.bnijik.shipratecompare.payload;

import net.bnijik.shipratecompare.model.QuoteModel;

import java.util.List;

public record RateResponse(List<QuoteModel> availableRates) {
}
