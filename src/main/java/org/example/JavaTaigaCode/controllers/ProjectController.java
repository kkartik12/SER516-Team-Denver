package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.example.JavaTaigaCode.service.ProjectService;
import org.example.JavaTaigaCode.service.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    BurndownChart burndownChart;

    @Autowired
    Tasks taskService;

    @Cacheable(value="projectList", key = "#memberID")
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

    @Cacheable(value="projectByID", key = "#projectID")
    @GetMapping("/projects/{projectID}")
    @ResponseBody
    public ProjectDTO getProjectDetails(@PathVariable("projectID") Integer projectID) {
        ProjectDTO project = projectService.getPojectDetails(projectID);

        if (project == null) {
            throw new RuntimeException("Unable to get Project List for user");
        }
        return project;
    }

    @Cacheable(value="projectBySlug", key = "#Slug")
    @GetMapping("/projects/by-slug/{slug}")
    @ResponseBody
    public ProjectDTO getProjectDetailsSlug(@PathVariable("slug") String Slug) {
        ProjectDTO project = projectService.getProjectDetailsSlug(Slug);

        if (project == null) {
            throw new RuntimeException("Unable to get Project List for user");
        }
        return project;
    }

    @GetMapping("/projects/{projectID}/businessValue/{userStoryID}")
    @ResponseBody
    public Integer getBusinessValueForUserStory(@PathVariable("userStoryID") Integer userStoryID) {
        return burndownChart.getBusinessValueForUserStory(userStoryID);
        
    }

    @Cacheable(value="burnDownBV", key = "#milestoneID")
    @GetMapping("/burndownchart/{milestoneID}/businessValue")
    @ResponseBody
    public MilestoneDTO getTotalBusinessValue(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.getTotalBusinessValue(milestoneID);
    }

    @Cacheable(value="burnDownPartialRunningSum", key = "#milestoneID")
    @GetMapping("/burndownchart/{milestoneID}/partialRunningSum")
    @ResponseBody
    public MilestoneDTO getPartialRunningSum(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.calculatePartialRunningSum(milestoneID);
    }

    @Cacheable(value="burnDownTotalRunningSum", key = "#milestoneID")
    @GetMapping("/burndownchart/{milestoneID}/totalRunningSum")
    @ResponseBody
    public MilestoneDTO getTotalRunningSum(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.calculateTotalRunningSum(milestoneID);
    }
}
