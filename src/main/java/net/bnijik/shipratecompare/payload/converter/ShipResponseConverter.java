package net.bnijik.shipratecompare.payload.converter;

import net.bnijik.shipratecompare.payload.ShipRequest;
import net.bnijik.shipratecompare.payload.ShipResponse;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeShipResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShipResponseConverter {
    ShipResponse shipTimeShipResponseToShipResponse(ShipTimeShipResponse shipTimeShipResponse);

    ShipTimeShipRequest shipRequestToShipTimeShipRequest(ShipRequest shipRequest);
}