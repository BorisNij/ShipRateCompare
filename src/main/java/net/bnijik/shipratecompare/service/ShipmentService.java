package net.bnijik.shipratecompare.service;

import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;

public interface ShipmentService {
    ShipResponse createShipments(ShipRequest shipRequest);
}
