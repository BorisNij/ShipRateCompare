package net.bnijik.backend.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.bnijik.backend.model.AddressModel;
import net.bnijik.backend.model.LineItemModel;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Accessors(fluent = true)
public record RateRequest(AddressModel fromAddress,
                          AddressModel toAddress,
                          PackageType packageType,
                          List<LineItemModel> lineItems,
                          UnitOfMeasurement unitOfMeasurement,
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
                          ZonedDateTime shipDate) {
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
