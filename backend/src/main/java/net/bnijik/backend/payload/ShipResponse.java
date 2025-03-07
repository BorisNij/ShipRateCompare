package net.bnijik.backend.payload;

import java.net.URL;
import java.util.List;

public record ShipResponse(int shipId, List<String> trackingNumbers, URL labelUrl) {
}
