package com.example.cycletime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class Tasks {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Value("${taiga_api_endpoint}")
    private String TAIGA_API_ENDPOINT;

    public List<UserStoryDTO> getClosedStories(Integer milestoneId, String token){
        String endpoint = TAIGA_API_ENDPOINT + "/userstories?milestone=" + milestoneId;
        HttpGet request = new HttpGet(endpoint);
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


    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"); 
        return LocalDate.parse(dateString, formatter);
    }

/*     private static int[] calculateCycleTime(JsonNode historyData, LocalDateTime finishedDate) {
        int cycleTime = 0;
        int closedTasks = 0;

        for (JsonNode event : historyData) {
            JsonNode valuesDiff = event.get("values_diff");
            if (valuesDiff != null && valuesDiff.has("status")) {
                JsonNode statusDiff = valuesDiff.get("status");
                if (statusDiff.isArray() && statusDiff.size() == 2
                        && "New".equals(statusDiff.get(0).asText()) && "In progress".equals(statusDiff.get(1).asText())) {
                    LocalDate createdAt =parseDate(event.get("created_at").asText());
                    cycleTime += Duration.between(createdAt.toLocalDate().atStartOfDay(), finishedDate.toLocalDate().atStartOfDay()).toDays();
                    closedTasks++;
                }
            }
        }

        return new int[]{cycleTime, closedTasks};
    } */

//    public static List<Integer> getTaskHistory(List<JsonNode> tasks, String authToken, String TAIGA_API_ENDPOINT) {
//        List<Integer> result = new ArrayList<>(List.of(0, 0));
//
//        for (JsonNode task : tasks) {
//            int taskId = task.get("id").asInt();
//
//            // API to get history of task
//            String taskHistoryUrl = TAIGA_API_ENDPOINT + "/history/task/" + taskId;
//
//            try {
//                HttpGet request = new HttpGet(taskHistoryUrl);
//                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
//                request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
//
//                String responseJson = HTTPRequest.sendHttpRequest(request);
//
//                JsonNode historyData = objectMapper.readTree(responseJson);
//                LocalDateTime finishedDate = parseDateTime(task.get("finished_date").asText());
//
//                int[] cycleTimeAndClosedTasks = calculateCycleTime(historyData, finishedDate);
//                result.set(0, result.get(0) + cycleTimeAndClosedTasks[0]);
//                result.set(1, result.get(1) + cycleTimeAndClosedTasks[1]);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
}
