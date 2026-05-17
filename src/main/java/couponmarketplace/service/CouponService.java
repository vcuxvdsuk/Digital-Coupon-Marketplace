package couponmarketplace.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import couponmarketplace.dto.CreateCouponRequest;
import couponmarketplace.dto.ProductDto;
import couponmarketplace.dto.PurchaseRequest;
import couponmarketplace.dto.PurchaseResponse;
import couponmarketplace.entity.Coupon;
import couponmarketplace.exception.ProductAlreadySoldException;
import couponmarketplace.exception.ProductNotFoundException;
import couponmarketplace.exception.ResellerPriceTooLowException;
import couponmarketplace.mapper.ProductMapper;
import couponmarketplace.repository.CouponRepository;


@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final PricingService pricingService;

    public CouponService(PricingService pricingService, 
                        CouponRepository couponRepository) {
        this.pricingService = pricingService;
        this.couponRepository = couponRepository;
    }

    public List<ProductDto> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(coupon -> ProductMapper.toDto((Coupon) coupon))
                .collect(Collectors.toList());
    }
    
    public List<ProductDto> getAvailableCoupons() {
        return couponRepository.findBySoldFalse().stream()
                .map(coupon -> ProductMapper.toDto((Coupon) coupon))
                .collect(Collectors.toList());
    }

    public ProductDto getCouponById(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductMapper.toDto(coupon);
    }

    @Transactional
    public Coupon createCoupon(CreateCouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setName(request.getName());
        coupon.setDescription(request.getDescription());
        coupon.setImageUrl(request.getImageUrl());
        coupon.setCostPrice(request.getCostPrice());
        coupon.setMarginPercentage(request.getMarginPercentage());
        coupon.setValue(request.getValue());
        return couponRepository.save(coupon);
    }

    @Transactional
    public PurchaseResponse purchaseCoupon(UUID id, PurchaseRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (coupon.isSold()) {
            throw new ProductAlreadySoldException("Product already sold");
        }
        if (request.getResellerPrice().compareTo(pricingService.getMinimumSellPrice(coupon)) < 0) {
            throw new ResellerPriceTooLowException("Reseller price is below minimum sell price");
        }
        coupon.setSold(true);
        couponRepository.save(coupon);
        PurchaseResponse response = new PurchaseResponse();
        response.setProductId(coupon.getId());
        response.setFinalPrice(request.getResellerPrice());
        response.setValue(coupon.getValue());
        return response;
    }

    @Transactional
    public PurchaseResponse purchaseCouponDirect(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (coupon.isSold()) {
            throw new ProductAlreadySoldException("Product already sold");
        }
        coupon.setSold(true);
        couponRepository.save(coupon);
        PurchaseResponse response = new PurchaseResponse();
        response.setProductId(coupon.getId());
        response.setFinalPrice(pricingService.getMinimumSellPrice(coupon));
        response.setValue(coupon.getValue());
        return response;
    }

}