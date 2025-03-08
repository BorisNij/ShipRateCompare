package net.bnijik.backend.client;

import net.bnijik.backend.config.ShipTimeClientConfig;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;
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