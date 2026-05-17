package couponmarketplace.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponmarketplace.dto.ProductDto;
import couponmarketplace.dto.PurchaseRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.service.CouponService;

@RestController
@RequestMapping("/api/v1/products")
public class ResellerProductController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    public List<ProductDto> getAvailableProducts() {
        return couponService.getAvailableCoupons();
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        return couponService.getCouponById(productId);
    }

    @PostMapping("/{productId}/purchase")
    public PurchaseResponse purchaseProduct(@PathVariable UUID productId, @RequestBody PurchaseRequest request) {
        return couponService.purchaseCoupon(productId, request);
    }
}