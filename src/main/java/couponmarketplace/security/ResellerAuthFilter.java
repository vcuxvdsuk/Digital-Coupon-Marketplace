package couponmarketplace.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import couponmarketplace.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class ResellerAuthFilter extends OncePerRequestFilter {

    @Value("${reseller.api.token}")
    private String expectedToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/api/v1/")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7);
            if (!expectedToken.equals(token)) {
                throw new UnauthorizedException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}