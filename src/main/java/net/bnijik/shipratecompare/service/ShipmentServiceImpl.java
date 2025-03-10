package net.bnijik.shipratecompare.service;

import net.bnijik.shipratecompare.client.ShipTimeClient;
import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;
import net.bnijik.shipratecompare.payload.converter.ShipResponseConverter;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;
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
        ShipTimeShipRequest shipTimeShipRequest = shipResponseConverter.shipRequestToShipTimeShipRequest(
                shipRequest);
        ShipTimeShipResponse shipTimeShipments = shipTimeClient.createShipments(shipTimeShipRequest);
        return shipResponseConverter.shipTimeShipResponseToShipResponse(shipTimeShipments);
    }
}
