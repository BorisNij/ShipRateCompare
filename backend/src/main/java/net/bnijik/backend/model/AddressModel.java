package net.bnijik.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressModel(String companyName,
                           String streetAddress,
                           String city,
                           String countryCode,
                           String state,
                           String postalCode,
                           String attention,
                           String phone) {
}
