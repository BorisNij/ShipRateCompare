package net.bnijik.backend.payload.converter;

import net.bnijik.backend.payload.ShipRequest;
import net.bnijik.backend.payload.ShipResponse;
import net.bnijik.backend.payload.externalApi.ShipTimeShipRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeShipResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShipResponseConverter {
    ShipResponse ShipTimeShipResponseToShipResponse(ShipTimeShipResponse shipTimeShipResponse);

    ShipTimeShipResponse ShipResponseToShipTimeShipResponse(ShipResponse shipResponse);

    ShipRequest ShipTimeShipRequestToShipRequest(ShipTimeShipRequest shipTimeShipRequest);

    ShipTimeShipRequest ShipRequestToShipTimeShipRequest(ShipRequest shipRequest);
}