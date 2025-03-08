package net.bnijik.backend.service;

import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;

public interface ShipmentService {
    ShipResponse createShipments(ShipRequest shipRequest);
}
