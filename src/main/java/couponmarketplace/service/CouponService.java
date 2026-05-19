package couponmarketplace.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.entity.Coupon;
import couponmarketplace.exception.ProductNotFoundException;
import couponmarketplace.repository.CouponRepository;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final PricingService pricingService;

    public CouponService(CouponRepository couponRepository, PricingService pricingService) {
        this.couponRepository = couponRepository;
        this.pricingService = pricingService;
    }

    @Transactional
    public Coupon createCoupon(CreateCouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setName(request.getName());
        coupon.setDescription(request.getDescription());
        coupon.setImageUrl(request.getImageUrl());
        coupon.setCostPrice(request.getCostPrice());
        coupon.setMarginPercentage(request.getMarginPercentage());
        coupon.setValueType(request.getValueType().name());
        coupon.setValue(request.getValue());
        coupon.setMinimumSellPrice(pricingService.getMinimumSellPrice(coupon));
        return couponRepository.save(coupon);
    }

    @Transactional
    public void deleteCoupon(UUID id) {
        if (!couponRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        couponRepository.deleteById(id);
    }

    @Transactional
    public Coupon updateCoupon(UUID id, CreateCouponRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        coupon.setName(request.getName());
        coupon.setDescription(request.getDescription());
        coupon.setImageUrl(request.getImageUrl());
        coupon.setCostPrice(request.getCostPrice());
        coupon.setMarginPercentage(request.getMarginPercentage());
        coupon.setValueType(request.getValueType().name());
        coupon.setValue(request.getValue());
        coupon.setMinimumSellPrice(pricingService.getMinimumSellPrice(coupon));
        return couponRepository.save(coupon);
    }
}
