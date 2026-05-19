package couponmarketplace.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import couponmarketplace.dto.CreateResellerRequest;
import couponmarketplace.dto.CreateResellerResponse;
import couponmarketplace.dto.ResellerLoginRequest;
import couponmarketplace.service.ResellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reseller")
@Tag(name = "Reseller Auth", description = "Endpoints for reseller registration and authentication.")
public class ResellerController {

    private final ResellerService resellerService;

    public ResellerController(ResellerService resellerService) {
        this.resellerService = resellerService;
    }

    @Operation(summary = "Register reseller", description = "Create a new reseller account and return an API token.")
    @PostMapping("/register")
    public CreateResellerResponse register(@Valid @RequestBody CreateResellerRequest request) {
        return resellerService.createReseller(request);
    }

    @Operation(summary = "Login reseller", description = "Authenticate a reseller and return the existing API token.")
    @PostMapping("/login")
    public CreateResellerResponse login(@Valid @RequestBody ResellerLoginRequest request) {
        return resellerService.loginReseller(request);
    }
}
