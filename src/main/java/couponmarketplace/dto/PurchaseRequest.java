package couponmarketplace.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class PurchaseRequest {
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal resellerPrice;

    // getter setter
    public BigDecimal getResellerPrice() { return resellerPrice; }
    public void setResellerPrice(BigDecimal resellerPrice) { this.resellerPrice = resellerPrice; }
}