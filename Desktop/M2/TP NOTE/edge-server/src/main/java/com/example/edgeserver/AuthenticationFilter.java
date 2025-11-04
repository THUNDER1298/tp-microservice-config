package com.example.edgeserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Si ce n'est pas la route composite, on laisse passer
        if (!exchange.getRequest().getURI().getPath().startsWith("/api/product-composite")) {
            // (Votre test POST va passer par ici)
            return chain.filter(exchange);
        }

        // Extraire les en-têtes (pour le test GET)
        String username = exchange.getRequest().getHeaders().getFirst("username");
        String password = exchange.getRequest().getHeaders().getFirst("password");
        String role = exchange.getRequest().getHeaders().getFirst("role");

        if (username == null || password == null || role == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Appeler le service d'autorisation (en minuscules)
        return webClientBuilder.build().post()
            .uri("lb://AUTHORIZATION-SERVICE/validate")
            .header("username", username)
            .header("password", password)
            .header("role", role)
            .retrieve()
            .toBodilessEntity()
            .flatMap(response -> {
                // OK, continuer
                return chain.filter(exchange);
            })
            .onErrorResume(e -> {
                // Erreur (service 401 ou 503)
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            });
    }

    @Override
    public int getOrder() {
        return -1; // Exécuter ce filtre en priorité
    }
}