package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class ProjectService {
    WebClient webClient;

    String authUrl = "http://auth:8080/api";

    public ProjectService() {
        this.webClient = WebClient.create();
    }

    public Object getProjectList(Integer memberID, String token) {
        URI uri = URI.create(authUrl + "/projectList/" + memberID);
        return webClient.post()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object getPojectDetails(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "/projects/" + projectID);
        return webClient.post()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

    public Object getProjectDetailsSlug(String slug, String token) {
        URI uri = URI.create(authUrl + "/projects/by-slug/" + slug);
        return webClient.post()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }
}
