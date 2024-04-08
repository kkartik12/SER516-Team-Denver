// package org.example.JavaTaigaCode.service;
package com.example.leadtime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
// import org.example.JavaTaigaCode.models.TaskDTO;
// import org.example.JavaTaigaCode.models.UserStoryDTO;
// import org.example.JavaTaigaCode.util.GlobalData;
// import org.example.JavaTaigaCode.util.;
// import com.example.leadTime.TaskDTO;
// import com.example.leadTime.UserStoryDTO;
// import com.example.leadTime.HTTPRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class Tasks {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    // private final static String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();
    @Value("${taiga_api_endpoint}")
    private String TAIGA_API_ENDPOINT;

    public List<UserStoryDTO> getClosedStories(Integer milestoneId, String token){
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?milestone=" + milestoneId;
        HttpGet request = new HttpGet(endpoint);
        // request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("x-disable-pagination","True");
        String responseJson = HTTPRequest.sendHttpRequest(request);
        if(responseJson != null) {
            try {
                JsonNode stories = objectMapper.readTree(responseJson);
                List<UserStoryDTO> closedStories = new ArrayList<>();
                if(stories.isArray()) {
                    for(JsonNode story : stories) {
                        if(story.get("is_closed").booleanValue()){
                            LocalDate createdAt = parseDate(story.get("created_date").asText());
                            LocalDate closedAt = parseDate(story.get("finish_date").asText());
                            UserStoryDTO u = new UserStoryDTO(createdAt, closedAt);
                            closedStories.add(u);
                        }
                    }
                    return closedStories;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<TaskDTO> getClosedTasks(Integer milestoneId, String token){
        String endpoint = TAIGA_API_ENDPOINT + "/tasks?milestone=" + milestoneId;
        HttpGet request = new HttpGet(endpoint);
        // request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("x-disable-pagination","True");
        String responseJson = HTTPRequest.sendHttpRequest(request);
        if(responseJson != null) {
            try {
                JsonNode tasks = objectMapper.readTree(responseJson);
                List<TaskDTO> closedTasks = new ArrayList<>();
                if(tasks.isArray()) {
                    for(JsonNode story : tasks) {
                        if(story.get("is_closed").booleanValue()){
                            LocalDate createdAt = parseDate(story.get("created_date").asText());
                            LocalDate closedAt = parseDate(story.get("finished_date").asText());
                            TaskDTO u = new TaskDTO(createdAt, closedAt);
                            closedTasks.add(u);
                        }
                    }
                    return closedTasks;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<UserStoryDTO> getClosedStoriesByProject(Integer projectId, String token) {
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?project=" + projectId;
        HttpGet request = new HttpGet(endpoint);
        // request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("x-disable-pagination", "True");
        String responseJson = HTTPRequest.sendHttpRequest(request);
        
        if (responseJson != null) {
            try {
                JsonNode stories = objectMapper.readTree(responseJson);
                List<UserStoryDTO> closedStories = new ArrayList<>();
                
                if (stories.isArray()) {
                    for (JsonNode story : stories) {
                        if (story.get("is_closed").booleanValue()) {
                            LocalDate createdAt = parseDate(story.get("created_date").asText());
                            LocalDate closedAt = parseDate(story.get("finish_date").asText());
                            UserStoryDTO u = new UserStoryDTO(createdAt, closedAt);
                            closedStories.add(u);
                        }
                    }
                    return closedStories;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public List<TaskDTO> getClosedTasksByProject(Integer projectId, String token) {
        String endpoint = TAIGA_API_ENDPOINT + "/tasks?project=" + projectId;
        HttpGet request = new HttpGet(endpoint);
        // request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("x-disable-pagination", "True");
        String responseJson = HTTPRequest.sendHttpRequest(request);
        
        if (responseJson != null) {
            try {
                JsonNode tasks = objectMapper.readTree(responseJson);
                List<TaskDTO> closedTasks = new ArrayList<>();       
                if (tasks.isArray()) {
                    for (JsonNode task : tasks) {
                        if (task.get("is_closed").booleanValue()) {
                            LocalDate createdAt = parseDate(task.get("created_date").asText());
                            LocalDate closedAt = parseDate(task.get("finished_date").asText());
                            TaskDTO u = new TaskDTO(createdAt, closedAt);
                            closedTasks.add(u);
                        }
                    }
                    return closedTasks;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
        return null;
    }
    

    public List<UserStoryDTO> calculateLeadTimeUS(Integer milestoneId, String token) {
        List<UserStoryDTO> closedStories = getClosedStories(milestoneId, token);
        for (UserStoryDTO story : closedStories) {
            LocalDate createdAt = story.getCreatedDate();
            LocalDate closedAt = story.getFinishDate();
            if (createdAt != null && closedAt != null) {
                long leadTimeInDays = ChronoUnit.DAYS.between(createdAt, closedAt);
                story.setLeadTime(leadTimeInDays);
            }
        }
        return closedStories;
    }

    public List<TaskDTO> calculateLeadTimeTask(Integer milestoneId, String token) {
        List<TaskDTO> closedTasks = getClosedTasks(milestoneId, token);
        for (TaskDTO task : closedTasks) {
            LocalDate createdAt = task.getCreatedDate();
            LocalDate closedAt = task.getClosedDate();
            if (createdAt != null && closedAt != null) {
                long leadTimeInDays = ChronoUnit.DAYS.between(createdAt, closedAt);
                task.setLeadTime(leadTimeInDays);
            }
        }
        return closedTasks;
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"); 
        return LocalDate.parse(dateString, formatter);
    }

    public List<UserStoryDTO> calculateLeadTimeUSbyTime(Integer projectId, LocalDate startDate, LocalDate endDate, String token) {
        List<UserStoryDTO> closedStoriesWithinDateRange = new ArrayList<>();
        List<UserStoryDTO> closedStories = getClosedStoriesByProject(projectId, token);
        for (UserStoryDTO story : closedStories) {
            LocalDate closedAt = story.getFinishDate();
            if (closedAt != null && closedAt.isAfter(startDate) && closedAt.isBefore(endDate)) {
                closedStoriesWithinDateRange.add(story);
            }
        }
        for (UserStoryDTO story : closedStoriesWithinDateRange) {
            LocalDate createdAt = story.getCreatedDate();
            LocalDate closedAt = story.getFinishDate();
            if (createdAt != null && closedAt != null) {
                long leadTimeInDays = ChronoUnit.DAYS.between(createdAt, closedAt);
                story.setLeadTime(leadTimeInDays);
            }
        }
        return closedStoriesWithinDateRange;
    }

    public List<TaskDTO> calculateLeadTimeTaskbyTime(Integer projectId, LocalDate startDate, LocalDate endDate, String token) {
        List<TaskDTO> closedTasksWithinDateRange = new ArrayList<>();
        List<TaskDTO> closedTasks = getClosedTasksByProject(projectId, token);
        for (TaskDTO task : closedTasks) {
            LocalDate closedAt = task.getClosedDate();
            if (closedAt != null && closedAt.isAfter(startDate) && closedAt.isBefore(endDate)) {
                closedTasksWithinDateRange.add(task);
            }
        }
        for (TaskDTO task : closedTasksWithinDateRange) {
            LocalDate createdAt = task.getCreatedDate();
            LocalDate closedAt = task.getClosedDate();
            if (createdAt != null && closedAt != null) {
                long leadTimeInDays = ChronoUnit.DAYS.between(createdAt, closedAt);
                task.setLeadTime(leadTimeInDays);
            }
        }
        return closedTasksWithinDateRange;
    }
}
