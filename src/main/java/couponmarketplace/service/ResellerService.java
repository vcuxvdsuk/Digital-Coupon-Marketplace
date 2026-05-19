package couponmarketplace.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import couponmarketplace.dto.CreateResellerRequest;
import couponmarketplace.dto.CreateResellerResponse;
import couponmarketplace.dto.ResellerLoginRequest;
import couponmarketplace.entity.Reseller;
import couponmarketplace.repository.ResellerRepository;

@Service
public class ResellerService {

    private final ResellerRepository resellerRepository;

    public ResellerService(ResellerRepository resellerRepository) {
        this.resellerRepository = resellerRepository;
    }

    public CreateResellerResponse createReseller(CreateResellerRequest request) {
        Optional<Reseller> existing = resellerRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A reseller with that email already exists");
        }

        Reseller reseller = new Reseller();
        reseller.setName(request.getName());
        reseller.setEmail(request.getEmail());
        reseller.setPassword(request.getPassword());
        reseller.setApiToken(UUID.randomUUID().toString());

        Reseller saved = resellerRepository.save(reseller);
        return new CreateResellerResponse(saved.getApiToken());
    }

    public CreateResellerResponse loginReseller(ResellerLoginRequest request) {
        Reseller reseller = resellerRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!reseller.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid email or password");
        }

        return new CreateResellerResponse(reseller.getApiToken());
    }

    public boolean isValidToken(String token) {
        return token != null && resellerRepository.existsByApiToken(token);
    }

    public Optional<Reseller> findByApiKey(String apiKey) {
        return resellerRepository.findByApiToken(apiKey);
    }
}
