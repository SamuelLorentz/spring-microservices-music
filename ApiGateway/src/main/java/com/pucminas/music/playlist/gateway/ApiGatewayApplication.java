package com.pucminas.music.playlist.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
	    return builder.routes()
	        
	        .route(p -> p
		            .path("/v1/playlists")
		            .uri("http://localhost:8070/v1/playlists"))
	        .route(t -> t.path("/v1/playlists/**")
	                .filters(rw -> rw.rewritePath("/v1/playlists/(?<segment>.*)", "/v1/playlists/${segment}"))
	                .uri("http://localhost:8070/v1/playlists/"))
	        
	        .route(p -> p
		            .path("/v1/tracks")
		            .uri("http://localhost:8060/v1/tracks"))
	        .route(t -> t.path("/v1/tracks/**")
	                .filters(rw -> rw.rewritePath("/v1/tracks/(?<segment>.*)", "/v1/tracks/${segment}"))
	                .uri("http://localhost:8060/v1/tracks/"))
	        
	        .build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
