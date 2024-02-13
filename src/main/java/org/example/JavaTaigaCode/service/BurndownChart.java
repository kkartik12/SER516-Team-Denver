package org.example.JavaTaigaCode.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.node.DoubleNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.JavaTaigaCode.models.BurndownChartDTO;
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

    String datePattern = "yyyy-MM-dd";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    HttpClient httpClient = HttpClients.createDefault();

    public MilestoneDTO calculateTotalRunningSum(Integer milestoneID) {
        // Implement logic to calculate the total running sum
        String response = "";
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones/" + milestoneID;
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
                response = EntityUtils.toString(responseEntity);
            }
            JsonNode milestoneJSON = objectMapper.readTree(response);
            MilestoneDTO milestone = new MilestoneDTO();
            milestone.setMilestoneID(milestoneJSON.get("id").asInt());
            milestone.setMilestoneName(milestoneJSON.get("name").asText());
            milestone.setTotalPoints(milestoneJSON.get("total_points").asDouble());
            milestone.setStart_date(LocalDate.parse(milestoneJSON.get("estimated_start").asText(), dateFormatter));
            milestone.setEnd_date(LocalDate.parse(milestoneJSON.get("estimated_finish").asText(), dateFormatter));
            JsonNode userStories = milestoneJSON.get("user_stories");
            double totalSum = milestone.getTotalPoints();
            List<BurndownChartDTO> totalRunningSum = new ArrayList<>();
            totalRunningSum.add(new BurndownChartDTO(LocalDate.parse(milestoneJSON.get("estimated_start").asText(), dateFormatter)
                    , totalSum));
            Map<LocalDate, Double> pointsMap = new TreeMap<>();
            if(userStories.isArray()) {
                for(JsonNode us: userStories) {
                    if(us.get("status_extra_info").get("name").asText().equals("Done")) {
                        LocalDateTime dateTime = LocalDateTime.parse(us.get("finish_date").asText(), DateTimeFormatter.ISO_DATE_TIME);
                        pointsMap.put(dateTime.toLocalDate(),
                                us.get("total_points").asDouble());
                    }
                }

                for(LocalDate key: pointsMap.keySet()) {
                    totalSum = totalSum - pointsMap.get(key);
                    totalRunningSum.add(new BurndownChartDTO(key, totalSum));
                }
            }
            milestone.setTotalSumValue(totalRunningSum);

            return milestone;
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
