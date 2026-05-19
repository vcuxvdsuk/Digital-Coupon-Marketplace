package couponmarketplace.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class PurchaseRequest {

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 12, fraction = 2, message = "Price must have at most 12 integer digits and 2 decimals")
    @JsonProperty("reseller_price")
    private BigDecimal resellerPrice;

    // getter setter
    public BigDecimal getResellerPrice() {
        return resellerPrice;
    }

    public void setResellerPrice(BigDecimal resellerPrice) {
        this.resellerPrice = resellerPrice;
    }
}
