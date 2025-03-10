package net.bnijik.shipratecompare.service;

import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;

public interface RateService {

    RateResponse getRates(RateRequest rateRequest);
}
