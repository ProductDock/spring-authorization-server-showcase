package com.productdock.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder().username("user").password("{bcrypt}$2a$10$K4nPRbjfGTPLmrRu07PMT.h6TWPJvSGuZRUzATYyDHJ1JZBsRrNoi").build()
        );
    }
}
