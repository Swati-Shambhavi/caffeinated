package com.caffeinated.keycloakuserservice;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.springframework.web.reactive.function.client.WebClient;

public class UserRegistrationEventListenerProviderFactory implements EventListenerProviderFactory {
    private final WebClient.Builder webClientBuilder;

    public UserRegistrationEventListenerProviderFactory(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new UserRegistrationEventListenerProvider(keycloakSession, webClientBuilder);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "user-registration-event-listener";
    }
}
