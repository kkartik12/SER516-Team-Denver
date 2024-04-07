package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class AdoptedWorkService {
    WebClient webClient;

    String authUrl = "http://adopted_work:8080/api";

    public AdoptedWorkService() {
        this.webClient = WebClient.create();
    }

    public Object getUSAddedAfterSprintPlanning(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/adoptedWork/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object getAdoptedWorkForAllSprints(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "/adoptedWork/project/" + projectID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
}
