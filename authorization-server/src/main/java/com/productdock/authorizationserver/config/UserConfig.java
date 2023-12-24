package com.productdock.authorizationserver.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class UserConfig {

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    ApplicationRunner usersRunner(UserDetailsManager userDetailsManager) {
        return args -> {
            var users = Map.of(
                    "user", User.builder().username("user").password("{bcrypt}$2a$10$K4nPRbjfGTPLmrRu07PMT.h6TWPJvSGuZRUzATYyDHJ1JZBsRrNoi").roles("USER").build(),
                    "admin", User.builder().username("admin").password("{bcrypt}$2a$10$tiJjsXtwenC/VzR/artQOeHwpTuicnAWYi.3M9g7ME6cH8MxtUovK").roles("ADMIN").build());

            users.forEach((username, user) -> {
                if (!userDetailsManager.userExists(username)) {
                    userDetailsManager.createUser(user);
                }
            });
        };

    }
}