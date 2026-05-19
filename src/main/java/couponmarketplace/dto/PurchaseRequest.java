package couponmarketplace.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class PurchaseRequest {

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal BuyOffer;

    // getter setter
    public BigDecimal getOffer() {
        return BuyOffer;
    }

    public void setOffer(BigDecimal BuyOffer) {
        this.BuyOffer = BuyOffer;
    }
}
