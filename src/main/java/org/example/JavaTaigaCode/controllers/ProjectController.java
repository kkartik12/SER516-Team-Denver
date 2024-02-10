package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @GetMapping("/projectList/{memberID}")
    @ResponseBody
    public List<ProjectDTO> getProjectList(@PathVariable("memberID") Integer memberID) {
        List<ProjectDTO> projects = projectService.getPojectList(memberID);

            if (projects != null) {
                for (ProjectDTO project : projects) {
                    System.out.println(project.toString());
                }
            } else {
                throw new RuntimeException("Unable to get Project List for user");
            }
            return projects;
    }

    @GetMapping("/projects/{projectID}")
    @ResponseBody
    public ProjectDTO getProjectDetails(@PathVariable("projectID") Integer projectID) {
        ProjectDTO project = projectService.getPojectDetails(projectID);

        if (project == null) {
            throw new RuntimeException("Unable to get Project List for user");
        }
        return project;
    }

    @GetMapping("/projects/{projectID}/metrics")
    @ResponseBody
    public Map<String, Double> getMetrics(@PathVariable("projectID") Integer projectID) {
    Map<String, Double> metrics = new HashMap<>();

    // Calculate business value
    double businessValue = projectService.calculateBusinessValue(projectID);
    metrics.put("businessValue", businessValue);

    // Calculate partial running sum
    double partialRunningSum = projectService.calculatePartialRunningSum(projectID);
    metrics.put("partialRunningSum", partialRunningSum);

    // Calculate total running sum
    double totalRunningSum = projectService.calculateTotalRunningSum(projectID);
    metrics.put("totalRunningSum", totalRunningSum);

    return metrics;
    }
}
