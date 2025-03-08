package net.bnijik.backend.service;

import net.bnijik.backend.client.ShipTimeClient;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.converter.RateResponseConverter;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    private final ShipTimeClient shipTimeClient;
    private final RateResponseConverter rateResponseConverter;

    @Autowired
    public RateServiceImpl(ShipTimeClient shipTimeClient, RateResponseConverter rateResponseConverter) {
        this.shipTimeClient = shipTimeClient;
        this.rateResponseConverter = rateResponseConverter;
    }

    @Override
    public RateResponse getRates(RateRequest rateRequest) {
        final ShipTimeRateRequest shipTimeRateRequest = rateResponseConverter.RateRequestToShipTimeRateRequest(
                rateRequest);
        final ShipTimeRateResponse shipTimeRates = shipTimeClient.getRates(shipTimeRateRequest);
        return rateResponseConverter.ShipTimeRateResponseToRateResponse(shipTimeRates);
    }
}
