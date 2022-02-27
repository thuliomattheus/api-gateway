package com.in28minutes.microservices.apigateway.config;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

  @Bean
  public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(
            // acessa o naming-server e recupera a uri do "currency-exchange"
            // o prefixo "lb" é relacionado ao load-balancer
            routeTo("/currency-exchange/**", "lb://currency-exchange")
        )
        .route(
            routeTo("/currency-conversion/**", "lb://currency-conversion")
        )
        .route(
            routeTo("/currency-conversion-feign/**", "lb://currency-conversion")
        )
        .build();
  }

  private Function<PredicateSpec, Buildable<Route>> routeTo(String path, String uri) {
    return p -> p.path(path).uri(uri);
  }
}
