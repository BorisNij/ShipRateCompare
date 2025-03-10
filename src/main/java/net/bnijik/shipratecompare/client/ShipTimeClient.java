package net.bnijik.shipratecompare.client;

import net.bnijik.shipratecompare.config.ShipTimeClientConfig;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = ShipTimeClientConfig.API_PATH, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface ShipTimeClient {
    @PostExchange(ShipTimeClientConfig.RATE_RESOURCE)
    ShipTimeRateResponse getRates(@RequestBody ShipTimeRateRequest shipTimeRateRequest);

    @PostExchange(ShipTimeClientConfig.SHIPMENT_RESOURCE)
    ShipTimeShipResponse createShipments(@RequestBody ShipTimeShipRequest shipTimeShipRequest);
}