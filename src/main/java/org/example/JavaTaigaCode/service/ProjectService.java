package org.example.JavaTaigaCode.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.util.GlobalData;
import org.example.JavaTaigaCode.util.HTTPRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class ProjectService {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final String TAIGA_API_ENDPOINT = GlobalData.getTaigaURL();

    // private static String promptUserPassword(String prompt) {
    // if (System.console() != null) {
    // char[] passwordChars = System.console().readPassword(prompt);
    // return new String(passwordChars);
    // } else {
    // System.out.print(prompt);
    // return scanner.nextLine();
    // }
    // }

    // public static int getProjectId(String authToken,String TAIGA_API_ENDPOINT) {
    //
    // // Prompting user to enter project slug name. A slug name is nothing but an
    // identifier for a project.
    // // Open any Taiga project and check the url of your browser. Slug name is the
    // value after " /project/SLUG_NAME "
    // // Example https://tree.taiga.io/project/SLUG_NAME/us/1?no-milestone=1
    //
    // String projectSlug = promptUser("Enter the Taiga project slug: ");
    // String endpoint = TAIGA_API_ENDPOINT + "/projects/by_slug?slug=" +
    // projectSlug;
    //
    // HttpGet request = new HttpGet(endpoint);
    // request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
    // request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    //
    // String responseJson = HTTPRequest.sendHttpRequest(request);
    //
    // if (responseJson != null) {
    // try {
    // JsonNode projectInfo = objectMapper.readTree(responseJson);
    // int projectId = projectInfo.has("id") ? projectInfo.get("id").asInt() : -1;
    //
    // if (projectId != -1) {
    // System.out.println("Service.Project details retrieved successfully.");
    // return projectId;
    // } else {
    // System.out.println("Invalid project slug. Please try again.");
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // return -1;
    // }

    // This method returns list of Projects related to member who has logged in.
    public List<ProjectDTO> getPojectList(Integer memberID) {
        String endpoint = TAIGA_API_ENDPOINT + "/projects?member=" + memberID;
        HttpGet request = new HttpGet(endpoint);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
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

    public ProjectDTO getPojectDetails(int projectID) {
        try {
            String endpoint = TAIGA_API_ENDPOINT + "/projects/" + projectID;
            HttpGet request = new HttpGet(endpoint);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + Authentication.authToken);
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
                    List<String> milestoneIds = new ArrayList<>();
                    for (JsonNode milestone : milestonesJSON) {
                        milestones.add(milestone.get("name").asText());
                        milestoneIds.add(milestone.get("id").asText());
                    }
                    project.setMilestones(milestones);
                    project.setMilestoneIds(milestoneIds);
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

    public double calculateBusinessValue(Integer projectID) {
        // Implement the logic to calculate the business value metric parameter
        return 0.0; // Placeholder, replace with actual calculation
    }

    public double calculatePartialRunningSum(Integer projectID) {
        // Implement logic to calculate the partial running sum
        return 0.0; // Placeholder, replace with actual calculation
    }

    public double calculateTotalRunningSum(Integer projectID) {
        // Implement logic to calculate the total running sum
        return 0.0; // Placeholder, replace with actual calculation
    }
}