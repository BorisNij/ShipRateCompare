package net.bnijik.backend.client;

import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/rest", contentType = MediaType.APPLICATION_JSON_VALUE)
public interface ShipTimeRateClient {
    @PostExchange("/rates")
    ShipTimeRateResponse getRates(@RequestBody ShipTimeRateRequest shipTimeRateRequest);
}
