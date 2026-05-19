package couponmarketplace.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import couponmarketplace.exception.UnauthorizedException;
import couponmarketplace.service.ResellerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ResellerAuthFilter extends OncePerRequestFilter {

    private final ResellerService resellerService;

    public ResellerAuthFilter(ResellerService resellerService) {
        this.resellerService = resellerService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/v1/")
                && !path.equals("/api/v1/reseller/register")
                && !path.equals("/api/v1/reseller/login")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7);

            if (!resellerService.isValidToken(token)) {
                throw new UnauthorizedException("Missing or invalid API token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
