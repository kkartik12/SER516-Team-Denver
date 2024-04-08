package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.LocalDate;

@Service
public class CycleTimeService {
    WebClient webClient;

    String authUrl = "http://cycletime:8080/api";

    public CycleTimeService() {
        this.webClient = WebClient.create();
    }

    public String getUSCycleTime(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/cycleTime/US/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getTaskCycleTime(Integer milestoneID, String token) {
        URI uri = URI.create(authUrl + "/cycleTime/Task/" + milestoneID);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String calculateUSCycleTimebyDates(Integer projectId,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        String token) {
        URI uri = URI.create(authUrl + "/cycleTime/US/byTime/"
                + projectId
                + "?startDate=" + startDate
                + "&endDate=" + endDate);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String calculateTaskCycleTimebyDates(Integer projectId,
                                              LocalDate startDate,
                                              LocalDate endDate,
                                              String token) {
        URI uri = URI.create(authUrl + "/cycleTime/Task/byTime/"
                + projectId
                + "?startDate=" + startDate
                + "&endDate=" + endDate);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

}
