package couponmarketplace.mapper;

import couponmarketplace.dto.ProductDto;
import couponmarketplace.entity.Product;


public class ProductMapper {

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}