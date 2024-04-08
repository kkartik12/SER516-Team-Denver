package com.example.dot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BurndownChart {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Value("${taiga_api_endpoint}") private String TAIGA_API_ENDPOINT;

    public Integer getBusinessValueForUserStory(Integer userStoryID, String token) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/userstories/custom-attributes-values/" + userStoryID;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            HttpResponse httpResponse = httpClient.execute(request);

            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }

            HttpEntity responseEntity = httpResponse.getEntity();

            if (responseEntity != null) {
                String response = EntityUtils.toString(responseEntity);
                JsonNode node = objectMapper.readTree(response).get("attributes_values");
                String bvKey = node.fieldNames().next();
                return node.get(bvKey).asInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}