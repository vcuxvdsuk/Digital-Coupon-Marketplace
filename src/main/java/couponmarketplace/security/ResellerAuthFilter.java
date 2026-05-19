package couponmarketplace.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import couponmarketplace.entity.Reseller;
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
            String token = request.getHeader("X-API-KEY");

            if (token == null || token.isBlank()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing API key");
                return;
            }

            Optional<Reseller> reseller
                    = resellerService.findByApiKey(token);

            if (reseller.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid API key");
                return;
            }

            if (!resellerService.isValidToken(token)) {
                throw new UnauthorizedException("Missing or invalid API token");
            }

        }
        filterChain.doFilter(request, response);
    }
}
