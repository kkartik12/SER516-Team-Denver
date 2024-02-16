package org.example.JavaTaigaCode.controllers;

import java.util.List;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.models.ProjectDTO;
import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.example.JavaTaigaCode.service.ProjectService;
import org.example.JavaTaigaCode.service.Tasks;
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
    
    // @GetMapping("/projects/{projectID}/partialRunningSum")
    // @ResponseBody
    // public Map<String, Double> getPartialRunningSum(@PathVariable("projectID") Integer projectID) {
    //     Map<String, Double> prs = new HashMap<>();
    //     double partialRunningSum = projectService.calculatePartialRunningSum(projectID);
    //     prs.put("partialRunningSum", partialRunningSum);
    //     return prs;
    // }

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
        return Tasks.calculateLeadTimeUS(milestoneID);
    }

    @GetMapping("/leadTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTask(@PathVariable("milestoneID") Integer  milestoneID) {
        return Tasks.calculateLeadTimeTask(milestoneID);
    }
}
