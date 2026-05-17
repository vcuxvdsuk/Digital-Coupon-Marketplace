package couponmarketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.dto.ProductDto;
import couponmarketplace.entity.Coupon;
import couponmarketplace.mapper.ProductMapper;
import couponmarketplace.service.CouponService;
import org.springframework.ui.Model;


@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/products/all")
    public List<ProductDto> getAllProducts() {
        return couponService.getAvailableCoupons();
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody CreateCouponRequest request) {
        Coupon coupon = couponService.createCoupon(request);
        return ProductMapper.toDto(coupon);
    }

    @GetMapping("/create")
    public String adminPage(Model model) {
        model.addAttribute("createCouponRequest", new CreateCouponRequest());
        model.addAttribute("products", couponService.getAvailableCoupons());
        return "admin";
    }
}