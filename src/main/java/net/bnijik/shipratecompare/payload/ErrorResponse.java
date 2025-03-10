package net.bnijik.shipratecompare.payload;

import java.util.List;

public record ErrorResponse(List<String> errors) {
}
