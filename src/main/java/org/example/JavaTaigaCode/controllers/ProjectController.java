package org.example.JavaTaigaCode.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.example.JavaTaigaCode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    BurndownChart burndownChart;
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

    @GetMapping("/projects/{projectID}/businessValue")
    @ResponseBody
    public Map<String, Double> getBusinessValue(@PathVariable("projectID") Integer projectID) {
        Map<String, Double> bv = new HashMap<>();
        double businessValue = projectService.calculateBusinessValue(projectID);
        bv.put("businessValue", businessValue);
        return bv;
    }
    
    // @GetMapping("/projects/{projectID}/partialRunningSum")
    // @ResponseBody
    // public Map<String, Double> getPartialRunningSum(@PathVariable("projectID") Integer projectID) {
    //     Map<String, Double> prs = new HashMap<>();
    //     double partialRunningSum = projectService.calculatePartialRunningSum(projectID);
    //     prs.put("partialRunningSum", partialRunningSum);
    //     return prs;
    // }

    @GetMapping("/projects/{projectID}/patialRunningSum")
    @ResponseBody
    public List<MilestoneDTO> getPartialRunningSum(@PathVariable("projectID") Integer projectID) {
        return burndownChart.calculatePartialRunningSum(projectID);
    }
    
    @GetMapping("/projects/{projectID}/totalRunningSum")
    @ResponseBody
    public List<MilestoneDTO> getTotalRunningSum(@PathVariable("projectID") Integer projectID) {
        return burndownChart.calculateTotalRunningSum(projectID);
    }

}
