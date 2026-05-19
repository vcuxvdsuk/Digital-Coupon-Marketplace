package couponmarketplace.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import couponmarketplace.dto.ProductDto;
import couponmarketplace.entity.Coupon;
import couponmarketplace.entity.Product;
import couponmarketplace.exception.ProductNotFoundException;
import couponmarketplace.mapper.ProductMapper;
import couponmarketplace.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllAvailableProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product instanceof Coupon coupon && coupon.isSold() == false)
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductMapper.toDto(product);
    }
}
