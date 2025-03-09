package net.bnijik.backend.model;

import java.util.List;

public record ErrorModel(boolean success, List<String> messages) {
}
