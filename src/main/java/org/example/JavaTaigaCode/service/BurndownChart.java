package org.example.JavaTaigaCode.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.util.GlobalData;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class BurndownChart {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();

    public List<MilestoneDTO> calculateTotalRunningSum(Integer projectID) {
        // Implement logic to calculate the total running sum
        String response = "";
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones?project=" + projectID;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            // String responseJson = HTTPRequest.sendHttpRequest(request);
            HttpResponse httpResponse = httpClient.execute(request);
            List<MilestoneDTO> milestones = new ArrayList<>();
            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
            }
            System.out.println(response);
            return milestones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MilestoneDTO> calculatePartialRunningSum(Integer projectID) {
        String response = "";
        try{
            String endpoint = TAIGA_API_ENDPOINT + "/milestones?project="+ projectID;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpResponse httpResponse = httpClient.execute(request);
            List<MilestoneDTO> milestones = new ArrayList<>();
            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
            }
            JsonNode rootNode = objectMapper.readTree(response);
            if(rootNode.isArray()) {
                for(JsonNode milestoneJSON: rootNode) {
                    MilestoneDTO milestone = new MilestoneDTO();
                    milestone.setMilestoneID(milestoneJSON.get("id").asInt());
                    milestone.setMilestoneName(milestoneJSON.get("name").asText());
                    JsonNode userStories = milestoneJSON.get("user_stories");
                    double totalSum = 0;
                    if(userStories.isArray()) {
                        for(JsonNode us: userStories) {
                            //write login for partial sum
                            // if(us.get("status_extra_info").get("name").asText().equals("Done")) {
                            //     totalSum = totalSum + us.get("total_points").asDouble();
                            // }
                        }
                    }
                    milestone.setPartialSumValue(totalSum);
                    milestones.add(milestone);
                }
            } else {
                throw new RuntimeException(response);
            }
            return milestones;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Integer getBusinessValueForUserStory(Integer userStoryID) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/userstories/custom-attributes-values/" + userStoryID;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            HttpResponse httpResponse = httpClient.execute(request);

            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }

            HttpEntity responseEntity = httpResponse.getEntity();
            
            if (responseEntity != null) {
                String response = EntityUtils.toString(responseEntity);
                // TODO - Fix. Currently assumes the only custom attribute is the business value
                JsonNode node = objectMapper.readTree(response).path("attributes_values");
                String bvKey = node.fieldNames().next();
                return node.get(bvKey).asInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
