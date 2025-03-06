package net.bnijik.backend.payload.converter;

import net.bnijik.backend.payload.RateRequest;
import net.bnijik.backend.payload.externalApi.ShipTimeRateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RateResponseConverter {
//    RateResponse ShipTimeRateResponseToRateResponse(ShipTimeRateResponse shipTimeRateResponse);

//    ShipTimeRateResponse RateResponseToShipTimeRateResponse(RateResponse rateResponse);

    RateRequest ShipTimeRateRequestToRateRequest(ShipTimeRateRequest shipTimeRateRequest);

    ShipTimeRateRequest RateRequestToShipTimeRateRequest(RateRequest rateRequest);
}
