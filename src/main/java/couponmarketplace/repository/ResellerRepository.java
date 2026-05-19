package couponmarketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import couponmarketplace.entity.Reseller;

public interface ResellerRepository extends JpaRepository<Reseller, Long> {

    boolean existsByApiToken(String apiToken);

    Optional<Reseller> findByEmail(String email);

    Optional<Reseller> findByEmailAndPassword(String email, String password);

    Optional<Reseller> findByApiToken(String apiToken);
}
