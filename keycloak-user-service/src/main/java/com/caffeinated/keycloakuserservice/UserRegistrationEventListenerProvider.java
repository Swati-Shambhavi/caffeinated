package com.caffeinated.keycloakuserservice;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class UserRegistrationEventListenerProvider implements EventListenerProvider {
    private final KeycloakSession session;
    private final RealmProvider model;
    private final WebClient webClient;

    private static final Logger log = Logger.getLogger(UserRegistrationEventListenerProviderFactory.class);

    public UserRegistrationEventListenerProvider(KeycloakSession session, WebClient.Builder webClientBuilder) {
        this.session = session;
        this.model = session.realms();
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/users/api").build();
    }

    @Override
    public void onEvent(Event event) {
        if(EventType.REGISTER.equals(event.getType())){
            log.infof("## NEW %s EVENT", event.getType());
            log.info("-----------------------------------------------------------");

            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(realm,event.getUserId());
            sendUserDataToBackend(newRegisteredUser);
        }
    }

    private void sendUserDataToBackend(UserModel user) {
        // Prepare the data to send to your backend
        CaffeinatdNewUserRequest userRegistrationData = new CaffeinatdNewUserRequest(user.getFirstName(), user.getLastName(), user.getEmail());

        // Use WebClient to send a POST request to your backend
        webClient.post()
                .uri("/register")
                .body(BodyInserters.fromValue(userRegistrationData))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("User data sent to backend successfully"))
                .doOnError(error -> log.error("Error sending user data to backend", error))
                .subscribe();
    }


    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
