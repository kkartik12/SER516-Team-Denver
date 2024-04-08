package com.example.orchestrator.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class AdoptedWorkService {
    WebClient webClient;

    String authUrl = "http://adoptedwork:8080/api";

    public AdoptedWorkService() {
        this.webClient = WebClient.create();
    }

    public String getUSAddedAfterSprintPlanning(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/adoptedWork/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getAdoptedWorkForAllSprints(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "/adoptedWork/project/" + projectID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
