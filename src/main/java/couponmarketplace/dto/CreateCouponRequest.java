package couponmarketplace.dto;

import java.math.BigDecimal;

import couponmarketplace.entity.ValueType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCouponRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be shorter than 255 characters")
    private String name;
    private String description;
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    @NotNull(message = "Cost price is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Cost price must be positive")
    @Digits(integer = 12, fraction = 2, message = "Cost price must have at most 12 integer digits and 2 decimals")
    private BigDecimal costPrice;
    @NotNull(message = "Margin percentage is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Margin percentage must be positive")
    @Digits(integer = 3, fraction = 2, message = "Margin percentage must have at most 3 integer digits and 2 decimals")
    private BigDecimal marginPercentage;
    @NotNull(message = "Value type is required")
    private ValueType valueType;
    @NotBlank(message = "Coupon value is required")
    private String value;

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
