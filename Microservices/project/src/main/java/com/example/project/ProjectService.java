package com.example.project;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Value("${taiga_api_endpoint}")
    private String TAIGA_API_ENDPOINT;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String datePattern = "yyyy-MM-dd";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    HttpClient httpClient = HttpClients.createDefault();

    public ProjectDTO getProjectDetailsSlug(String Slug, String token) {
        String endpoint = TAIGA_API_ENDPOINT + "/projects/by_slug?slug=" + Slug;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        try {
            String responseJson = HTTPRequest.sendHttpRequest(request);
            ProjectDTO project = new ProjectDTO();
            if (responseJson != null) {

                JsonNode projectJSON = objectMapper.readTree(responseJson);
                project.setProjectID(projectJSON.get("id").asInt());
                project.setProjectName(projectJSON.get("name").asText());
                project.setSlug(projectJSON.get("slug").asText());
                project.setDescription(projectJSON.get("description").asText());
                project.setCreatedDate(
                        LocalDate.parse(projectJSON.get("created_date").asText(), DateTimeFormatter.ISO_DATE_TIME));
                JsonNode owner = projectJSON.get("owner");
                project.setOwner(owner.get("full_name_display").asText());
                JsonNode membersJSON = projectJSON.get("members");
                if (membersJSON.isArray()) {
                    List<String> members = new ArrayList<>();
                    for (JsonNode member : membersJSON) {
                        members.add(member.get("full_name").asText());
                    }
                    project.setMembers(members);
                }

                JsonNode milestonesJSON = projectJSON.get("milestones");
                if (milestonesJSON.isArray()) {
                    List<String> milestones = new ArrayList<>();
//                    List<String> milestoneIds = new ArrayList<>();
//                    List<Boolean> isClosed = new ArrayList<>();
                    List<MilestoneDTO> milestoneList = new ArrayList<>();
                    for (JsonNode milestoneJSON : milestonesJSON) {
                        milestones.add(milestoneJSON.get("name").asText());
                        MilestoneDTO milestone = getMilestoneDetails(milestoneJSON.get("id").asInt(), token);
                        milestoneList.add(milestone);
                    }
                    project.setMilestones(milestones);
                    project.setMilestoneDetails(milestoneList);
//                    project.setMilestoneIds(milestoneIds);
//                    project.setIsClosed(isClosed);
                }

                JsonNode customAttr = projectJSON.get("userstory_custom_attributes");
                if (customAttr.isArray()) {
                    for (JsonNode attr : customAttr) {
                        if (attr.get("name").asText().equals("BV")) {
                            project.setBvCustomAttributeID(attr.get("id").asInt());
                            break;
                        }
                    }
                }
            }
            return project;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // This method returns list of Projects related to member who has logged in.
    public List<ProjectDTO> getPojectList(Integer memberID, String token) {
        String endpoint = TAIGA_API_ENDPOINT + "/projects?member=" + memberID;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        String responseJson = HTTPRequest.sendHttpRequest(request);

        if (responseJson != null) {
            try {
                JsonNode projects = objectMapper.readTree(responseJson);
                if (projects.isArray()) {
                    List<ProjectDTO> projectList = new ArrayList<>();
                    for (JsonNode project : projects) {
                        ProjectDTO p = new ProjectDTO(
                                project.get("id").asInt(),
                                project.get("name").asText(),
                                project.get("slug").asText(),
                                project.get("description").asText());
                        projectList.add(p);
                    }
                    return projectList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ProjectDTO getPojectDetails(int projectID, String token) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/projects/" + projectID;
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            String responseJson = HTTPRequest.sendHttpRequest(request);
            ProjectDTO project = new ProjectDTO();
            if (responseJson != null) {

                JsonNode projectJSON = objectMapper.readTree(responseJson);
                project.setProjectID(projectJSON.get("id").asInt());
                project.setProjectName(projectJSON.get("name").asText());
                project.setSlug(projectJSON.get("slug").asText());
                project.setDescription(projectJSON.get("description").asText());
                project.setCreatedDate(
                        LocalDate.parse(projectJSON.get("created_date").asText(), DateTimeFormatter.ISO_DATE_TIME));
                project.setUpdatedDate(
                        LocalDate.parse(projectJSON.get("totals_updated_datetime").asText(), DateTimeFormatter.ISO_DATE_TIME));
                JsonNode owner = projectJSON.get("owner");
                project.setOwner(owner.get("full_name_display").asText());
                JsonNode membersJSON = projectJSON.get("members");
                if (membersJSON.isArray()) {
                    List<String> members = new ArrayList<>();
                    for (JsonNode member : membersJSON) {
                        members.add(member.get("full_name").asText());
                    }
                    project.setMembers(members);
                }

                JsonNode milestonesJSON = projectJSON.get("milestones");
                if (milestonesJSON.isArray()) {
                    List<String> milestones = new ArrayList<>();
//                    List<String> milestoneIds = new ArrayList<>();
//                    List<Boolean> isClosed = new ArrayList<>();
                    List<MilestoneDTO> milestoneList = new ArrayList<>();
                    for (JsonNode milestoneJSON : milestonesJSON) {
                        milestones.add(milestoneJSON.get("name").asText());
                        MilestoneDTO milestone = getMilestoneDetails(milestoneJSON.get("id").asInt(), token);
                        milestoneList.add(milestone);
                    }
                    project.setMilestones(milestones);
                    project.setMilestoneDetails(milestoneList);
//                    project.setMilestoneIds(milestoneIds);
//                    project.setIsClosed(isClosed);
                }

                JsonNode customAttr = projectJSON.get("userstory_custom_attributes");
                if (customAttr.isArray()) {
                    for (JsonNode attr : customAttr) {
                        if (attr.get("name").asText().equals("BV")) {
                            project.setBvCustomAttributeID(attr.get("id").asInt());
                            break;
                        }
                    }
                }
            }
            return project;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MilestoneDTO getMilestoneDetails(Integer milestoneID, String token) {
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
            }
            JsonNode milestoneJSON = objectMapper.readTree(response);
            MilestoneDTO milestone = new MilestoneDTO();
            milestone.setMilestoneID(milestoneJSON.get("id").asInt());
            milestone.setMilestoneName(milestoneJSON.get("name").asText());
            milestone.setTotalPoints(milestoneJSON.get("total_points").asDouble());
            milestone.setSpCompleted(milestoneJSON.get("closed_points").asDouble());
            milestone.setClosed(milestoneJSON.get("closed").asBoolean());
            //adding the estimated start and finish dates
            String start = milestoneJSON.get("estimated_start").asText(); 
            String end = milestoneJSON.get("estimated_finish").asText();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate startDate = LocalDate.parse(start, formatter);
            LocalDate endDate = LocalDate.parse(end, formatter);
            milestone.setStart_date(startDate);
            milestone.setEnd_date(endDate);
            
            return milestone;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MilestoneDTO> getClosedMilestonesbyID(Integer projectID) {
        List<MilestoneDTO> closedMilestones = new ArrayList<>();
        return closedMilestones;
    }

    public List<MilestoneDTO> getClosedMilestonesbySlug(String Slug) {
        List<MilestoneDTO> closedMilestones = new ArrayList<>();
        return closedMilestones;
    }
}