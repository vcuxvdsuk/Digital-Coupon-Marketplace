package couponmarketplace.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.dto.ProductDto;
import couponmarketplace.dto.PurchaseRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.service.CouponService;
import couponmarketplace.service.ProductService;
import couponmarketplace.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin API", description = "Administrative endpoints for managing coupons and purchases.")
public class AdminAPIController {

    private final ProductService productService;
    private final CouponService couponService;
    private final PurchaseService purchaseService;

    public AdminAPIController(ProductService productService, CouponService couponService, PurchaseService purchaseService) {
        this.productService = productService;
        this.couponService = couponService;
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "List all products",
            description = "Returns all products for the admin catalog.")
    @GetMapping("/products/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Create a new product",
            description = "Creates a new coupon-backed product from the provided payload.")
    @PostMapping("/products/create")
    public ProductDto createProduct(@Valid @RequestBody CreateCouponRequest request) {
        return productService.getProductById(couponService.createCoupon(request).getId());
    }

    @Operation(summary = "Get product by ID",
            description = "Returns product details for the specified product ID.")
    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Purchase a product",
            description = "Executes a purchase request for the specified product ID.")
    @PostMapping("/products/{id}/purchase")
    public PurchaseResponse purchaseProduct(@PathVariable UUID id, @Valid @RequestBody PurchaseRequest request) {
        return purchaseService.purchaseCoupon(id, request);
    }

    @Operation(summary = "Delete a product",
            description = "Deletes the specified product by its ID.")
    @DeleteMapping("/products/{id}/delete")
    public void deleteProduct(@PathVariable UUID id) {
        couponService.deleteCoupon(id);
    }

    @Operation(summary = "Update a product",
            description = "Updates product data for the specified product ID.")
    @PutMapping("/products/{id}/update")
    public ProductDto updateProduct(@PathVariable UUID id, @Valid @RequestBody CreateCouponRequest request) {
        return productService.getProductById(couponService.updateCoupon(id, request).getId());
    }

}
