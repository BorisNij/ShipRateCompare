package net.bnijik.backend.service;

import net.bnijik.backend.client.ShipTimeRateClient;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.converter.RateResponseConverter;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {
    private final ShipTimeRateClient shipTimeRateClient;
    private final RateResponseConverter rateResponseConverter;

    @Autowired
    public RateServiceImpl(ShipTimeRateClient shipTimeRateClient, RateResponseConverter rateResponseConverter) {
        this.shipTimeRateClient = shipTimeRateClient;
        this.rateResponseConverter = rateResponseConverter;
    }

    @Override
    public RateResponse getRates(RateRequest rateRequest) {
        final ShipTimeRateRequest shipTimeRateRequest = rateResponseConverter.RateRequestToShipTimeRateRequest(
                rateRequest);
        final ShipTimeRateResponse shipTimeRates = shipTimeRateClient.getRates(shipTimeRateRequest);
        return rateResponseConverter.ShipTimeRateResponseToRateResponse(shipTimeRates);
    }
}
