package couponmarketplace.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import couponmarketplace.entity.Coupon;
import jakarta.persistence.LockModeType;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    List<Coupon> findBySoldFalse();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    Optional<Coupon> findById(UUID id);
}