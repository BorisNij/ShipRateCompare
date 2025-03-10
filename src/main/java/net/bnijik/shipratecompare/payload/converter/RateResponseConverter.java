package net.bnijik.shipratecompare.payload.converter;

import net.bnijik.shipratecompare.payload.RateRequest;
import net.bnijik.shipratecompare.payload.RateResponse;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateRequest;
import net.bnijik.shipratecompare.payload.externalapi.ShipTimeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RateResponseConverter {
    RateResponse shipTimeRateResponseToRateResponse(ShipTimeRateResponse shipTimeRateResponse);

    ShipTimeRateRequest rateRequestToShipTimeRateRequest(RateRequest rateRequest);
}
