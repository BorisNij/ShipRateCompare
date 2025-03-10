package net.bnijik.shipratecompare.payload;

import java.net.URL;
import java.util.List;

public record ShipResponse(int shipId, List<String> trackingNumbers, URL labelUrl) {
}
