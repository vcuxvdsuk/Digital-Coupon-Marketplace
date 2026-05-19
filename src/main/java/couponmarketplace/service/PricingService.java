package couponmarketplace.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import couponmarketplace.entity.Coupon;

@Service
public class PricingService {

    public BigDecimal getMinimumSellPrice(Coupon coupon) {
        if (coupon == null || coupon.getCostPrice() == null || coupon.getMarginPercentage() == null) {
            throw new IllegalArgumentException("Coupon pricing data is incomplete");
        }

        return coupon.getCostPrice().multiply(
                BigDecimal.ONE.add(
                        coupon.getMarginPercentage().divide(
                                BigDecimal.valueOf(100),
                                4,
                                RoundingMode.HALF_UP
                        )
                )
        ).setScale(2, RoundingMode.HALF_UP);
    }
}
