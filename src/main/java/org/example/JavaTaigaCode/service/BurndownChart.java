package org.example.JavaTaigaCode.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.JavaTaigaCode.models.BurndownChartDTO;
import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
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
            } else {
                throw new RuntimeException("Response body is NULL");
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
            totalRunningSum.add(new BurndownChartDTO(
                    LocalDate.parse(milestoneJSON.get("estimated_start").asText(), dateFormatter), totalSum));
            Map<LocalDate, Double> pointsMap = new TreeMap<>();
            if (userStories.isArray()) {
                for (JsonNode us : userStories) {
                    if (us.get("is_closed").asBoolean()) {
                        LocalDateTime dateTime = LocalDateTime.parse(us.get("finish_date").asText(),
                                DateTimeFormatter.ISO_DATE_TIME);
                        if(!pointsMap.containsKey(dateTime.toLocalDate())) {
                            pointsMap.put(dateTime.toLocalDate(),
                                    us.get("total_points").asDouble());
                        } else {
                            pointsMap.put(dateTime.toLocalDate(),
                                    pointsMap.get(dateTime.toLocalDate()) + us.get("total_points").asDouble());
                        }
                    }
                }

                for (LocalDate key : pointsMap.keySet()) {
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

    public MilestoneDTO calculatePartialRunningSum(Integer milestoneID) {
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
            List<BurndownChartDTO> partialRunningSum = new ArrayList<>();
            partialRunningSum.add(new BurndownChartDTO(
                    LocalDate.parse(milestoneJSON.get("estimated_start").asText(), dateFormatter), totalSum));
            //Map<LocalDate, Double> pointsMap = new TreeMap<>();
            if (userStories.isArray()) {
                for (JsonNode us : userStories) {
                    //story points of that user story
                    double storyPoints = us.get("total_points").asDouble();
                    List<TaskDTO> tasks = getUserStoryTasks(us.get("id").asInt());
                    //len of tasks = total tasks in that story
                    double taskStoryPoint = storyPoints / tasks.size();
                    // if the task is closed only then store the value
                    tasks = tasks.stream().filter(task -> task.getClosedDate() != null).collect(Collectors.toList());
                    Collections.sort(tasks, Comparator.comparing(TaskDTO::getClosedDate));
                    for(TaskDTO task:tasks) {
                        if(task.getClosed()==true){
                            partialRunningSum.add(new BurndownChartDTO(task.getClosedDate(), taskStoryPoint));
                        }
                    }
                    
                }

                Collections.sort(partialRunningSum, Comparator.comparing(BurndownChartDTO::getDate));
                for(int i=1; i<partialRunningSum.size();i++) {
                    totalSum = totalSum - partialRunningSum.get(i).getValue();
                    partialRunningSum.get(i).setValue(totalSum);
                }
            }
            milestone.setPartialSumValue(partialRunningSum);
            return milestone;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<TaskDTO> getUserStoryTasks(Integer userStoryID) {
        try{
            String endpoint = TAIGA_API_ENDPOINT + "/tasks?user_story=" + userStoryID;
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
            List<TaskDTO> tasks = new ArrayList<>();

            if (responseEntity != null) {
                String response = EntityUtils.toString(responseEntity);
                JsonNode tasksJSON = objectMapper.readTree(response);
                if(tasksJSON.isArray()) {
                    for(JsonNode taskJSON: tasksJSON) {
                        TaskDTO task = new TaskDTO();
                        task.setTaskID(taskJSON.get("id").asInt());
                        task.setTaskName(taskJSON.get("subject").asText());
                        task.setClosed(taskJSON.get("is_closed").asBoolean());
                        if(task.getClosed()) {
                            LocalDateTime dateTime = LocalDateTime.parse(taskJSON.get("finished_date").asText(),
                                    DateTimeFormatter.ISO_DATE_TIME);
                            task.setClosedDate(dateTime.toLocalDate());
                        }
                        tasks.add(task);
                    }
                }
                return tasks;
            }else {
                throw new RuntimeException("Response body is NULL");
            }
        } catch (Exception e){
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
        return 0;
    }

    public MilestoneDTO getTotalBusinessValue(Integer milestoneID) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/milestones/" + milestoneID;
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
                JsonNode node = objectMapper.readTree(response);
                JsonNode userStoriesNode = node.get("user_stories");
                LocalDate estimatedStart = LocalDate.parse(node.get("estimated_start").asText(), dateFormatter);
                LocalDate estimatedFinish = LocalDate.parse(node.get("estimated_finish").asText(), dateFormatter);
                
                List<UserStoryDTO> userStories = new ArrayList<>();
                for (JsonNode userStory : userStoriesNode) {
                    Integer userStoryId = userStory.get("id").asInt();
                    String dateText = userStory.get("finish_date").asText();
                    Boolean isClosed = userStory.get("is_closed").asBoolean();

                    LocalDate finishDate = null;
                    LocalDate createdAt = null;
                    if (isClosed) {
                        finishDate = LocalDateTime.parse(dateText, DateTimeFormatter.ISO_DATE_TIME).toLocalDate();
                        userStories.add(new UserStoryDTO(userStoryId, getBusinessValueForUserStory(userStoryId),
                                isClosed, finishDate, createdAt));
                    }
                }
                Collections.sort(userStories, Comparator.comparing(UserStoryDTO::getFinishDate));
                List<BurndownChartDTO> totalSumValue = new ArrayList<>();
                Integer runningSum = 0;
                for (UserStoryDTO userStory : userStories) {
                    runningSum += userStory.getBusinessValue();
                    totalSumValue.add(new BurndownChartDTO(userStory.getFinishDate(), runningSum.doubleValue()));
                }

                MilestoneDTO milestone = new MilestoneDTO(milestoneID, node.get("name").asText(),
                        estimatedStart,
                        estimatedFinish,
                        totalSumValue, null, totalSumValue.get(totalSumValue.size() - 1).getValue());
                return milestone;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
