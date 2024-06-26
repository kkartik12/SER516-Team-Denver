package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.LocalDate;

@Service
public class TaskService {
    WebClient webClient;

    String authUrl = "http://leadtime:8080/api";

    public TaskService() {
        this.webClient = WebClient.create();
    }

    public String calculateLeadTimeUS(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/leadTime/US/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String calculateLeadTimeTask(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/leadTime/Task/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String calculateLeadTimeUSbyTime(Integer projectId,
                                            LocalDate startDate,
                                            LocalDate endDate,
                                            String token) {
        URI uri = URI.create(authUrl + "/customleadTime/US/"
                + "?projectId=" + projectId
                + "&startDate=" + startDate
                + "&endDate=" + endDate);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String calculateLeadTimeTaskbyTime(Integer projectId,
                                            LocalDate startDate,
                                            LocalDate endDate,
                                            String token) {
        URI uri = URI.create(authUrl + "/customleadTime/Task/"
                + "?projectId=" + projectId
                + "&startDate=" + startDate
                + "&endDate=" + endDate);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
