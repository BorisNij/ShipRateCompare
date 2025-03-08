package net.bnijik.backend.controller.rest;

import lombok.RequiredArgsConstructor;
import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/shipments")
public class ShipmentController {
    @Autowired
    private final ShipmentService rateService;

    @PostMapping
    public ResponseEntity<ShipResponse> createShipments(@RequestBody ShipRequest rateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rateService.createShipments(rateRequest));
    }
}
