package com.productdock.bffgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GatewayConfig {

    @Bean
    public RouteLocator gateway(RouteLocatorBuilder rlb) {
        var apiPrefix = "/api/v1/";
        return rlb
                .routes()
                .route(rs -> rs
                        .path(apiPrefix + "**")
                        .filters(f -> f
                                .tokenRelay()
                                .rewritePath(apiPrefix + "(?<segment>.*)", "/$\\{segment}")
                        )
                        .uri("http://127.0.0.1:8081")
                )
                .route(rs -> rs
                        .path("/**")
                        .uri("http://127.0.0.1:4200")
                )
                .build();
    }

}
