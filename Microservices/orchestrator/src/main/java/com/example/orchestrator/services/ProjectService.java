package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class ProjectService {
    WebClient webClient;

    String authUrl = "http://project:8080/api";

    public ProjectService() {
        this.webClient = WebClient.create();
    }

    public String getProjectList(Integer memberID, String token) {
        URI uri = URI.create(authUrl + "/projectList/" + memberID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getPojectDetails(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "/projects/" + projectID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getProjectDetailsSlug(String slug, String token) {
        URI uri = URI.create(authUrl + "/projects/by-slug/" + slug);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getClosedMilestonesbyID(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "/DoT/" + projectID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getClosedMilestonesbySlug(String slug, String token) {
        URI uri = URI.create(authUrl + "/DoT/by-slug/" + slug);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
