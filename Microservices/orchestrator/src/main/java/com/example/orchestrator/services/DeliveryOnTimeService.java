package com.example.orchestrator.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
public class DeliveryOnTimeService {
    WebClient webClient;

    String authUrl = "http://deliveryontime:8080/api";

    public DeliveryOnTimeService() {
        this.webClient = WebClient.create();
    }

    public String getClosedMilestonesbyIDForBV(Integer projectID, String token) {
        URI uri = URI.create(authUrl + "DoT/"+projectID+"/BV");
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getClosedMilestonesbySlugForBV(String slug, String token) {
        URI uri = URI.create(authUrl + "DoT/by-slug/"+slug+"/BV");
        return webClient.get()
                .uri(uri)
                .header("token", token)
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
