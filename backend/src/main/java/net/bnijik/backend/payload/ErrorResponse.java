package net.bnijik.backend.payload;

import java.util.List;

public record ErrorResponse(List<String> errors) {
}
