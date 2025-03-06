package net.bnijik.backend.service;

import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;

public interface RateService {

    RateResponse getRates(RateRequest rateRequest);
}
