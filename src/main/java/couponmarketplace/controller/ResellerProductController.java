package couponmarketplace.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponmarketplace.dto.ProductDto;
import couponmarketplace.dto.PurchaseRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.service.ProductService;
import couponmarketplace.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reseller/products")
@Tag(name = "Reseller Products", description = "Endpoints for resellers to browse and purchase products.")
public class ResellerProductController {

    private final ProductService productService;
    private final PurchaseService purchaseService;

    public ResellerProductController(ProductService productService, PurchaseService purchaseService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "List reseller products", description = "Retrieve available products for reseller purchase.")
    @SecurityRequirement(name = "api-key")
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get reseller product", description = "Retrieve details for a single reseller product by ID.")
    @SecurityRequirement(name = "api-key")
    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "Reseller purchase", description = "Purchase a product as a reseller using the provided payload.")
    @SecurityRequirement(name = "api-key")
    @PostMapping("/{productId}/purchase")
    public PurchaseResponse purchaseProduct(@PathVariable UUID productId, @RequestBody PurchaseRequest request) {
        return purchaseService.purchaseCoupon(productId, request);
    }
}
