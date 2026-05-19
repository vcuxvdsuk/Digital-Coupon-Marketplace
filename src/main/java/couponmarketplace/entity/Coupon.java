package couponmarketplace.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@DiscriminatorValue("COUPON")
@Entity
public class Coupon extends Product {

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal costPrice;

    @Column(nullable = false, precision = 5, scale = 2)
    @NotNull
    @DecimalMax("999.99")
    @PositiveOrZero
    private BigDecimal marginPercentage;

    @Column(nullable = false)
    @NotNull
    private boolean sold = false;

    @NotNull
    @PositiveOrZero
    private BigDecimal minimumSellPrice;

    @Column(nullable = false)
    @NotNull
    private String valueType;

    @Column(nullable = false)
    @NotNull
    private String value;

    // getters and setters
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getMarginPercentage() {
        return marginPercentage;
    }

    public void setMarginPercentage(BigDecimal marginPercentage) {
        this.marginPercentage = marginPercentage;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getMinimumSellPrice() {
        return minimumSellPrice;
    }

    public void setMinimumSellPrice(BigDecimal minimumSellPrice) {
        this.minimumSellPrice = minimumSellPrice;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

}
