package org.example.adoptedWork;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class AdoptedWorkService {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private final String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();

    public AdoptedWorkDTO getUSAddedAfterSprintPlanning(Integer milestoneID) {
        // percentage of adopted work
        // sum of original estimate of stories added after sprint planning /
        // sum of original forecast for the sprint
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones/" + milestoneID;
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setHeader("x-disable-pagination", "True");

            HttpResponse httpResponse = httpClient.execute(request);

            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                String response = EntityUtils.toString(responseEntity);
                JsonNode milestoneJSON = objectMapper.readTree(response);
                JsonNode userStories = milestoneJSON.get("user_stories");
                LocalDate sprintEstimatedStart = LocalDate.parse(milestoneJSON.get("estimated_start").asText(),
                        dateFormatter);
                Integer sprintTotalPoints = milestoneJSON.get("total_points").asInt();
                Integer adoptedWork = 0;

                if (userStories.isArray()) {
                    for (JsonNode userStoryNode : userStories) {
                        LocalDate createdDate = LocalDate.parse(userStoryNode.get("created_date").asText(),
                                DateTimeFormatter.ISO_DATE_TIME);

                        if (createdDate.isAfter(sprintEstimatedStart)) {
                            adoptedWork += userStoryNode.get("total_points").asInt();
                        }
                    }
                }
                return new AdoptedWorkDTO(adoptedWork, sprintTotalPoints, milestoneID);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AdoptedWorkDTO> getAdoptedWorkForAllSprints(Integer projectId) {
        try {
            ProjectService projectService = new ProjectService();
            List<MilestoneDTO> milestones = projectService.getPojectDetails(projectId).getMilestoneDetails();
            List<AdoptedWorkDTO> adoptedWorkList = new ArrayList<>();

            for (MilestoneDTO milestone : milestones) {
                AdoptedWorkDTO adoptedWork = getUSAddedAfterSprintPlanning(milestone.getMilestoneID());
                adoptedWork.setMilestoneName(milestone.getMilestoneName());
                if (adoptedWork != null) {
                    adoptedWorkList.add(adoptedWork);
                }
            }
            return adoptedWorkList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
