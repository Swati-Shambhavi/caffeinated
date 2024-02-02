package com.caffeinated.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator caffeinatedRouteConfiguration(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/caffeinated/categories/**")
						.filters(f -> f.rewritePath("/caffeinated/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("productServiceCircuitBreaker").setFallbackUri("forward:/contactSupportTeam"))
						)
						.uri("lb://PRODUCT-CRAFTSMAN-SERVICE"))
				.route(p -> p
						.path("/caffeinated/products/**")
						.filters(f -> f.rewritePath("/caffeinated/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("productServiceCircuitBreaker").setFallbackUri("forward:/contactSupportTeam")))
						.uri("lb://PRODUCT-CRAFTSMAN-SERVICE"))
				.route(p -> p
						.path("/caffeinated/carts/**")
						.filters(f -> f.rewritePath("/caffeinated/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("cartServiceCircuitBreaker").setFallbackUri("forward:/contactSupportTeam"))
						)
						.uri("lb://CART-EXPRESSO-SERVICE"))
				.route(p -> p
						.path("/caffeinated/users/**")
						.filters(f -> f.rewritePath("/caffeinated/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("userServiceCircuitBreaker").setFallbackUri("forward:/contactSupportTeam"))
						)
						.uri("lb://CAFFEINATED-PERSONA-SERVICE"))
				.route(p -> p
						.path("/caffeinated/admin/**")
						.filters(f -> f.rewritePath("/caffeinated/(?<segment>.*)", "/${segment}")
								.circuitBreaker(config -> config.setName("userServiceCircuitBreaker").setFallbackUri("forward:/contactSupportTeam"))
						)
						.uri("lb://CAFFEINATED-PERSONA-SERVICE"))
				.build();

	}

}
