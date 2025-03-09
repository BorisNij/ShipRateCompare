package net.bnijik.backend.service;

import net.bnijik.backend.client.ShipTimeClient;
import net.bnijik.backend.exception.ConfigurationException;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.payload.converter.RateResponseConverter;
import net.bnijik.backend.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalapi.ShipTimeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class RateServiceImpl implements RateService {
    private final ShipTimeClient shipTimeClient;
    private final RateResponseConverter rateResponseConverter;
    private final Environment environment;

    @Autowired
    public RateServiceImpl(ShipTimeClient shipTimeClient,
                           RateResponseConverter rateResponseConverter,
                           Environment environment) {
        this.shipTimeClient = shipTimeClient;
        this.rateResponseConverter = rateResponseConverter;
        this.environment = environment;
    }

    @Override
    public RateResponse getRates(RateRequest rateRequest) {
        final ShipTimeRateRequest shipTimeRateRequest = rateResponseConverter.RateRequestToShipTimeRateRequest(
                rateRequest);
        final ShipTimeRateResponse shipTimeRates;
        try {
            shipTimeRates = shipTimeClient.getRates(shipTimeRateRequest);
        } catch (HttpClientErrorException.Unauthorized e) {
            String applicationName = environment.getProperty("spring.application.name");
            throw new ConfigurationException(String.format("Bad user name or password configured for %s",
                                                           applicationName));
        }
        return rateResponseConverter.ShipTimeRateResponseToRateResponse(shipTimeRates);
    }
}
