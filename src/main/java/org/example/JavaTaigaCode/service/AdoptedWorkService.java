package org.example.JavaTaigaCode.service;

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
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.util.GlobalData;
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

    public List<UserStoryDTO> getUSAddedAfterSprintPlanning(Integer milestoneID) {
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
            List<UserStoryDTO> userStoryList = new ArrayList<>();
            if (responseEntity != null) {
                String response = EntityUtils.toString(responseEntity);
                JsonNode milestoneJSON = objectMapper.readTree(response);
                JsonNode userStories = milestoneJSON.get("user_stories");
                LocalDate sprintEstimatedStart = LocalDate.parse(milestoneJSON.get("estimated_start").asText(),
                        dateFormatter);

                if (userStories.isArray()) {
                    for (JsonNode userStoryNode : userStories) {
                        LocalDate finishDate;
                        if (userStoryNode.get("finish_date").asText().equals("null")) {
                            finishDate = null;
                        } else {
                            finishDate = LocalDate.parse(userStoryNode.get("finish_date").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                        }

                        LocalDate createdDate = LocalDate.parse(userStoryNode.get("created_date").asText(),
                                DateTimeFormatter.ISO_DATE_TIME);

                        if (createdDate.isAfter(sprintEstimatedStart)) {
                            UserStoryDTO userStory = new UserStoryDTO(
                                    userStoryNode.get("id").asInt(),
                                    null,
                                    userStoryNode.get("is_closed").asBoolean(),
                                    finishDate,
                                    createdDate);
                            userStoryList.add(userStory);
                        }
                    }
                }
            }
            return userStoryList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
