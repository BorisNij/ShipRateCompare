package net.bnijik.shipratecompare.model;

import com.fasterxml.jackson.annotation.JsonValue;

public record MoneyAmountModel(Currency currency, int amount) {
    public enum Currency {
        CAD("CAD"), USD("USD");

        private final String value;

        Currency(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
