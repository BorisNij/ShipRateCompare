package net.bnijik.backend.client;

import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/rest")
public interface ShipTimeRateClient {
    @PostExchange("/rates")
    ShipTimeRateResponse getRates(@RequestBody ShipTimeRateRequest shipTimeRateRequest);
}
