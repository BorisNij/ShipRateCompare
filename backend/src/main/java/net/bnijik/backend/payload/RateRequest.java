package net.bnijik.backend.payload;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Accessors(fluent = true)
public record RateRequest(AddressModel fromAddress,
                          AddressModel toAddress,
                          PackageType packageType,
                          List<LineItemModel> lineItems,
                          UnitOfMeasurement unitOfMeasurement,
                          OffsetDateTime shipDate) {
    public enum UnitOfMeasurement {
        IMPERIAL("IMPERIAL"), METRIC("METRIC");

        private String value;

        UnitOfMeasurement(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    public enum PackageType {
        PACKAGE("PACKAGE");

        private String value;

        PackageType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
