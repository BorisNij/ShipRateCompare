package net.bnijik.backend.service;

import net.bnijik.backend.client.ShipTimeClient;
import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.payload.converter.ShipResponseConverter;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipTimeClient shipTimeClient;
    private final ShipResponseConverter shipResponseConverter;

    @Autowired
    public ShipmentServiceImpl(ShipTimeClient shipTimeClient, ShipResponseConverter shipResponseConverter) {
        this.shipTimeClient = shipTimeClient;
        this.shipResponseConverter = shipResponseConverter;
    }

    @Override
    public ShipResponse createShipments(ShipRequest shipRequest) {
        final ShipTimeShipRequest shipTimeShipRequest = shipResponseConverter.ShipRequestToShipTimeShipRequest(
                shipRequest);
        final ShipTimeShipResponse shipTimeShipments = shipTimeClient.createShipments(shipTimeShipRequest);
        return shipResponseConverter.ShipTimeShipResponseToShipResponse(shipTimeShipments);
    }
}
