package couponmarketplace.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import couponmarketplace.security.ResellerAuthFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<ResellerAuthFilter> resellerAuthFilterRegistration(
            ResellerAuthFilter filter) {

        FilterRegistrationBean<ResellerAuthFilter> registration
                = new FilterRegistrationBean<>();

        registration.setFilter(filter);
        registration.addUrlPatterns("/api/v1/*");

        return registration;
    }
}
