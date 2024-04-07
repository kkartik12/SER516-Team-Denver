package com.example.orchestrator.services;

import com.example.orchestrator.models.AuthModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class BurndownChart {
    WebClient webClient;

    String authUrl = "http://burndown:8080/api";

    public BurndownChart() {
        this.webClient = WebClient.create();
    }

    public Object getBurndownValues(Integer milestoneID,
                                    Boolean totalSum,
                                    Boolean partialSum,
                                    Boolean BVSum,
                                    String token) {
        URI uri = URI.create(authUrl + "/burndownchart/"
                + milestoneID
                + "?totalSum=" + totalSum
                + "&partialSum=" + partialSum
                + "&BVSum=" + BVSum);
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(Object.class).block();
    }

}
