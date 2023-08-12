package com.mobiliz.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9091"))
                .route("company-service", r -> r.path("/api/companies/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9092"))
                .route("fleet-service", r -> r.path("/api/fleets/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9093"))
                .route("company-districtgroup-service", r -> r.path("/api/districtgroups/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9094"))
                .route("company-group-service", r -> r.path("/api/companygroups/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9095"))
                .route("vehicle-service", r -> r.path("/api/vehicles/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:9096"))
                .build();
    }
}
