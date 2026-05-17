package couponmarketplace.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseResponse {
    private UUID productId;
    private BigDecimal finalPrice;
    private String valueType;
    private String value;

    // getters setters
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public BigDecimal getFinalPrice() { return finalPrice; }
    public void setFinalPrice(BigDecimal finalPrice) { this.finalPrice = finalPrice; }
    public String getValueType() { return valueType; }
    public void setValueType(String valueType) { this.valueType = valueType; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}