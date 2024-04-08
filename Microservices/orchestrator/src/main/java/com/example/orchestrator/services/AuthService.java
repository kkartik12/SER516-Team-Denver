package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class AuthService {
    WebClient authClient;

    String authUrl = "http://auth:8080/api/login";

    public AuthService() {
        this.authClient = WebClient.create();
    }


    public AuthModel authenticate(String username, String password) {
        URI authUri = URI.create(authUrl + "?username=" + username +"&password=" + password);
        return authClient.post()
                .uri(authUri) // Using the complete URI here
                .retrieve()
                .bodyToMono(AuthModel.class).block();
    }
}
