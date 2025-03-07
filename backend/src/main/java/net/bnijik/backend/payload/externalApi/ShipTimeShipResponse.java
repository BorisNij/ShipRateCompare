package net.bnijik.backend.payload.externalApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeShipResponse(int ShipId, List<String> trackingNumbers, URL labelUrl) {
}
