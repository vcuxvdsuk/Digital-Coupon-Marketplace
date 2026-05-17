package couponmarketplace.dto;

import java.math.BigDecimal;

import couponmarketplace.entity.ValueType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCouponRequest {
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String imageUrl;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal costPrice;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal marginPercentage;
    @NotNull
    private ValueType valueType;
    @NotBlank
    private String value;

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }
    public BigDecimal getMarginPercentage() { return marginPercentage; }
    public void setMarginPercentage(BigDecimal marginPercentage) { this.marginPercentage = marginPercentage; }
    public ValueType getValueType() { return valueType; }
    public void setValueType(ValueType valueType) { this.valueType = valueType; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}