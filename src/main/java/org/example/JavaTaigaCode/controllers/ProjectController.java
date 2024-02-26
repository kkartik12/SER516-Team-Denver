package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.example.JavaTaigaCode.service.ProjectService;
import org.example.JavaTaigaCode.service.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/burndownchart/{milestoneID}/businessValue")
    @ResponseBody
    public MilestoneDTO getTotalBusinessValue(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.getTotalBusinessValue(milestoneID);
    }
    
    @GetMapping("/burndownchart/{milestoneID}/partialRunningSum")
    @ResponseBody
    public MilestoneDTO getPartialRunningSum(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.calculatePartialRunningSum(milestoneID);
    }
    
    @GetMapping("/burndownchart/{milestoneID}/totalRunningSum")
    @ResponseBody
    public MilestoneDTO getTotalRunningSum(@PathVariable("milestoneID") Integer milestoneID) {
        return burndownChart.calculateTotalRunningSum(milestoneID);
    }

    @GetMapping("/leadTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getLeadTimeUS(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeUS(milestoneID);
    }

    @GetMapping("/leadTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTask(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeTask(milestoneID);
    }
}
