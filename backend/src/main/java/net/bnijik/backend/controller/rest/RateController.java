package net.bnijik.backend.controller.rest;

import lombok.RequiredArgsConstructor;
import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.RateResponse;
import net.bnijik.backend.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/rates")
public class RateController {
    @Autowired
    private final RateService rateService;

    @PostMapping
    @Cacheable(value = "rates", key = "#rateRequest")
    public ResponseEntity<RateResponse> getRates(@RequestBody RateRequest rateRequest) {
        return ResponseEntity.ok(rateService.getRates(rateRequest));
    }
}
