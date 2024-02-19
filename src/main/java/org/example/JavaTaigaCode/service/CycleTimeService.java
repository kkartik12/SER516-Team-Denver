package org.example.JavaTaigaCode.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.util.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CycleTimeService {

    @Autowired
    Tasks taskService;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();
    HttpClient httpClient = HttpClients.createDefault();

    public List<UserStoryDTO> getUSCycleTime(Integer milestoneID) {
        String response = "";
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones/" + milestoneID;
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setHeader("x-disable-pagination","True");
            HttpResponse httpResponse = httpClient.execute(request);
            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            List<UserStoryDTO> userStoryList = new ArrayList<>();
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
                JsonNode milestoneJSON = objectMapper.readTree(response);
                JsonNode userStories = milestoneJSON.get("user_stories");
                if (userStories.isArray()) {
                    for (JsonNode us : userStories) {
                        if (us.get("is_closed").asBoolean()) {
                            LocalDate inprogressDate = getInProgressDateUS(us.get("id").asInt());
                            UserStoryDTO userStoryDTO = new UserStoryDTO();
                            userStoryDTO.setUserStoryID(us.get("id").asInt());
                            userStoryDTO.setClosed(us.get("is_closed").asBoolean());
                            LocalDateTime dateTime = LocalDateTime.parse(us.get("finish_date").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                            userStoryDTO.setFinishDate(dateTime.toLocalDate());
                            userStoryDTO.setCreatedDate(inprogressDate);
                            if (userStoryDTO.getCreatedDate() == null) {
                                userStoryDTO.setCreatedDate(userStoryDTO.getFinishDate());
                            }
                            if (userStoryDTO.getCreatedDate() != null && userStoryDTO.getFinishDate() != null) {
                                long cycleTimeInDays = ChronoUnit.DAYS.between(userStoryDTO.getCreatedDate(),
                                        userStoryDTO.getFinishDate());
                                if (cycleTimeInDays == 0) {
                                    cycleTimeInDays = 1;
                                }
                                userStoryDTO.setCycleTime(cycleTimeInDays);
                            }
                            userStoryList.add(userStoryDTO);
                        }
                    }
                }
                return userStoryList;
            } else {
                throw new RuntimeException("Response body is NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LocalDate getInProgressDateUS(Integer userStoryID) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/history/userstory/" + userStoryID;
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
                JsonNode historyListJSON = objectMapper.readTree(response);
                if (historyListJSON.isArray()) {
                    for (JsonNode history : historyListJSON) {
                        JsonNode statusList = history.get("values_diff").get("status");
                        if (statusList != null && statusList.get(1).asText().equals("In progress")) {
                            LocalDateTime dateTime = LocalDateTime.parse(history.get("created_at").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                            return dateTime.toLocalDate();
                        }
                    }
                }
            } else {
                throw new RuntimeException("Response body is NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<TaskDTO> getTaskCycleTime(Integer milestoneID) {
        String response = "";
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/tasks?milestone=" + milestoneID;
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setHeader("x-disable-pagination","True");
            HttpResponse httpResponse = httpClient.execute(request);
            int httpStatus = httpResponse.getStatusLine().getStatusCode();
            if (httpStatus < 200 || httpStatus >= 300) {
                throw new RuntimeException(httpResponse.getStatusLine().toString());
            }
            HttpEntity responseEntity = httpResponse.getEntity();
            List<TaskDTO> tasks = new ArrayList<>();
            if (responseEntity != null) {
                response = EntityUtils.toString(responseEntity);
                JsonNode taskListJSON = objectMapper.readTree(response);
                if (taskListJSON.isArray()) {
                    for (JsonNode taskJSON : taskListJSON) {
                        if (taskJSON.get("is_closed").asBoolean()) {
                            TaskDTO task = new TaskDTO();
                            task.setTaskID(taskJSON.get("id").asInt());
                            task.setClosed(taskJSON.get("is_closed").asBoolean());
                            LocalDateTime dateTime = LocalDateTime.parse(taskJSON.get("finished_date").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                            task.setClosedDate(dateTime.toLocalDate());
                            task.setCreatedDate(getInProgressDateTask(task.getTaskID()));
                            if (task.getCreatedDate() == null) {
                                task.setCreatedDate(task.getClosedDate());
                            }
                            if (task.getCreatedDate() != null && task.getClosedDate() != null) {
                                long cycleTimeInDays = ChronoUnit.DAYS.between(task.getCreatedDate(),
                                        task.getClosedDate());
                                if (cycleTimeInDays == 0) {
                                    cycleTimeInDays = 1;
                                }
                                task.setCycleTime(cycleTimeInDays);
                            }
                            tasks.add(task);
                        }
                    }
                }
                return tasks;
            } else {
                throw new RuntimeException("Response body is NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LocalDate getInProgressDateTask(Integer taskID) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/history/task/" + taskID;
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
                JsonNode historyListJSON = objectMapper.readTree(response);
                if (historyListJSON.isArray()) {
                    for (JsonNode history : historyListJSON) {
                        JsonNode statusList = history.get("values_diff").get("status");
                        if (statusList != null && statusList.get(1).asText().equals("In progress")) {
                            LocalDateTime dateTime = LocalDateTime.parse(history.get("created_at").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                            return dateTime.toLocalDate();
                        }
                    }
                }
            } else {
                throw new RuntimeException("Response body is NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
