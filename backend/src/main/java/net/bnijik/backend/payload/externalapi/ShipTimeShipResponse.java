package net.bnijik.backend.payload.externalapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URL;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipTimeShipResponse(int shipId, List<String> trackingNumbers, URL labelUrl) {
}
