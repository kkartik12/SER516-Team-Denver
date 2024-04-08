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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryOnTimeService {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Value("${taiga_api_endpoint}") private String TAIGA_API_ENDPOINT;

    @Autowired
    ProjectService projectService;
    @Autowired
    BurndownChart burndownChart;

    HttpClient httpClient = HttpClients.createDefault();

    public List<MilestoneDTO> getClosedMilestonesbyID(Integer projectID, String token) {
        List<MilestoneDTO> milestones = new ArrayList<>();
        try {
            ProjectDTO project = projectService.getPojectDetails(projectID, token);
            for(MilestoneDTO milestone: project.getMilestoneDetails()) {
                MilestoneDTO milestoneDTO = getMilestondeDetails(milestone.getMilestoneID(), token);
                milestones.add(milestoneDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return milestones;
    }

    public MilestoneDTO getMilestondeDetails(Integer milestoneID, String token) {
        String response = "";
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones/" + milestoneID;
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
                response = EntityUtils.toString(responseEntity);
            } else {
                throw new RuntimeException("Response body is NULL");
            }
            JsonNode milestoneJSON = objectMapper.readTree(response);
            MilestoneDTO milestone = new MilestoneDTO();
            milestone.setMilestoneID(milestoneJSON.get("id").asInt());
            milestone.setMilestoneName(milestoneJSON.get("name").asText());
            milestone.setClosed(milestoneJSON.get("closed").asBoolean());
            JsonNode userStories = milestoneJSON.get("user_stories");
            int completedBV = 0;
            int totalBV = 0;
            for(JsonNode us: userStories) {
                int bv = burndownChart.getBusinessValueForUserStory(us.get("id").asInt(), token);
                totalBV = totalBV + bv;
                if(us.get("is_closed").asBoolean()) {
                    completedBV = completedBV + bv;
                }
            }
            milestone.setBvTotal(totalBV);
            milestone.setBvCompleted(completedBV);
            if(totalBV == 0 && completedBV == 0){
                milestone.setErrorCondition(true);
            } 
            return milestone;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MilestoneDTO> getClosedMilestonesbySlug(String projectSlug, String token){
        List<MilestoneDTO> milestones = new ArrayList<>();
        try {
            ProjectDTO project = projectService.getProjectDetailsSlug(projectSlug, token);
            for(MilestoneDTO milestone: project.getMilestoneDetails()) {
                MilestoneDTO milestoneDTO = getMilestondeDetails(milestone.getMilestoneID(), token);
                milestones.add(milestoneDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return milestones;
    }
}
