package couponmarketplace.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import couponmarketplace.dto.PurchaseRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.entity.Coupon;
import couponmarketplace.exception.ProductAlreadySoldException;
import couponmarketplace.exception.ProductNotFoundException;
import couponmarketplace.exception.ResellerPriceTooLowException;
import couponmarketplace.repository.CouponRepository;

@Service
public class PurchaseService {

    private final CouponRepository couponRepository;
    private final PricingService pricingService;

    public PurchaseService(CouponRepository couponRepository, PricingService pricingService) {
        this.couponRepository = couponRepository;
        this.pricingService = pricingService;
    }

    @Transactional
    public PurchaseResponse purchaseCoupon(UUID id, PurchaseRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (coupon.isSold()) {
            throw new ProductAlreadySoldException("Product already sold");
        }

        if (request == null || request.getResellerPrice() == null) {
            throw new IllegalArgumentException("reseller_price is required");
        }

        BigDecimal minimumPrice = pricingService.getMinimumSellPrice(coupon);
        if (request.getResellerPrice().compareTo(minimumPrice) < 0) {
            throw new ResellerPriceTooLowException("Reseller price is below minimum sell price");
        }
        coupon.setSold(true);
        couponRepository.save(coupon);

        return buildPurchaseResponse(coupon, request.getResellerPrice());
    }

    @Transactional
    public PurchaseResponse purchaseCouponDirect(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (coupon.isSold()) {
            throw new ProductAlreadySoldException("Product already sold");
        }

        BigDecimal finalPrice = pricingService.getMinimumSellPrice(coupon);
        coupon.setSold(true);
        couponRepository.save(coupon);

        return buildPurchaseResponse(coupon, finalPrice);
    }

    private PurchaseResponse buildPurchaseResponse(Coupon coupon, BigDecimal finalPrice) {
        PurchaseResponse response = new PurchaseResponse();
        response.setProductId(coupon.getId());
        response.setFinalPrice(finalPrice);
        response.setValueType(coupon.getValueType());
        response.setValue(coupon.getValue());
        return response;
    }
}
