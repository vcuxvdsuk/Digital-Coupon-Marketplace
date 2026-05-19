package couponmarketplace.mapper;

import java.math.BigDecimal;

import couponmarketplace.dto.ProductDto;
import couponmarketplace.entity.Coupon;
import couponmarketplace.entity.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        if (product instanceof Coupon coupon) {
            dto.setPrice(coupon.getMinimumSellPrice());
        } else {
            dto.setPrice(BigDecimal.ZERO);
        }
        return dto;
    }
}
