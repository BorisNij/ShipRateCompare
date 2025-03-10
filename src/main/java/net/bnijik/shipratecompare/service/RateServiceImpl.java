package net.bnijik.shipratecompare.service;

import net.bnijik.shipratecompare.client.ShipTimeClient;
import net.bnijik.shipratecompare.exception.ConfigurationException;
import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import net.bnijik.shipratecompare.payload.converter.RateResponseConverter;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
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
        ShipTimeRateRequest shipTimeRateRequest = rateResponseConverter.rateRequestToShipTimeRateRequest(
                rateRequest);
        ShipTimeRateResponse shipTimeRates;
        try {
            shipTimeRates = shipTimeClient.getRates(shipTimeRateRequest);
        } catch (HttpClientErrorException.Unauthorized e) {
            String applicationName = environment.getProperty("spring.application.name");
            throw new ConfigurationException(String.format("Bad user name or password configured for %s",
                                                           applicationName));
        }
        return rateResponseConverter.shipTimeRateResponseToRateResponse(shipTimeRates);
    }
}
