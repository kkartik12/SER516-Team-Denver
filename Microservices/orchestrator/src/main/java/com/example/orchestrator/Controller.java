package com.example.orchestrator;

import com.example.orchestrator.models.AuthModel;
import com.example.orchestrator.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {

    @Autowired
    AuthService authentication;

    private String token;

    @Autowired
    ProjectService projectService;
    @Autowired
    BurndownChart burndownChart;
    @Autowired
    CycleTimeService cycleTimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    AdoptedWorkService adoptedWorkService;
//    @Autowired
//    DeliveryOnTimeService deliveryOnTimeService;
    @Autowired
    FoundWorkService foundWorkService;


    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestParam String username, @RequestParam String password) {
        AuthModel authModel = authentication.authenticate(username, password);
        if(authModel != null && authModel.getMemberID() != null) {
            return ResponseEntity.ok(authModel.getMemberID());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/projectList/{memberID}")
    @ResponseBody
    public Object getProjectList(@PathVariable("memberID") Integer memberID) {
        return projectService.getProjectList(memberID, token);
    }

    @GetMapping("/projects/{projectID}")
    @ResponseBody
    public Object getProjectDetails(@PathVariable("projectID") Integer projectID) {
        return projectService.getPojectDetails(projectID, token);
    }

    @GetMapping("/projects/by-slug/{slug}")
    @ResponseBody
    public Object getProjectDetailsSlug(@PathVariable("slug") String Slug) {
        return projectService.getProjectDetailsSlug(Slug, token);
    }

    @GetMapping("/burndownchart/{milestoneID}")
    @ResponseBody
    public Object getBurndownValues(@PathVariable("milestoneID") Integer milestoneID,
                                          @RequestParam Boolean totalSum, @RequestParam Boolean partialSum,
                                          @RequestParam Boolean BVSum) {
        return burndownChart.getBurndownValues(milestoneID, totalSum, partialSum, BVSum, token);

    }

    @GetMapping("/cycleTime/US/{milestoneID}")
    @ResponseBody
    public Object getUSCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getUSCycleTime(milestoneID, token);
    }

    @GetMapping("/cycleTime/Task/{milestoneID}")
    @ResponseBody
    public Object getTaskCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getTaskCycleTime(milestoneID, token);
    }

    @GetMapping("/cycleTime/US/byTime/{projectId}")
    @ResponseBody
    public Object getUSCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
                                                    @RequestParam LocalDate endDate) {
        return cycleTimeService.calculateUSCycleTimebyDates(projectId, startDate, endDate, token);
    }

    @GetMapping("/cycleTime/Task/byTime/{projectId}")
    @ResponseBody
    public Object getTaskCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
                                           @RequestParam LocalDate endDate) {
        return cycleTimeService.calculateTaskCycleTimebyDates(projectId, startDate, endDate, token);
    }

    @GetMapping("/leadTime/US/{milestoneID}")
    @ResponseBody
    public Object getLeadTimeUS(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeUS(milestoneID, token);
    }

    @GetMapping("/leadTime/Task/{milestoneID}")
    @ResponseBody
    public Object getLeadTimeTask(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeTask(milestoneID, token);
    }

    @GetMapping("/customleadTime/US")
    @ResponseBody
    public Object getLeadTimeUSbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return taskService.calculateLeadTimeUSbyTime(projectId, startDate, endDate, token);
    }

    @GetMapping("/customleadTime/Task")
    @ResponseBody
    public Object getLeadTimeTaskbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return taskService.calculateLeadTimeTaskbyTime(projectId, startDate, endDate, token);
    }

    @GetMapping("/adoptedWork/{milestoneID}")
    @ResponseBody
    public Object getUSAddedAfterSprintPlanning(@PathVariable("milestoneID") Integer milestoneID) {
        return adoptedWorkService.getUSAddedAfterSprintPlanning(milestoneID, token);
    }

    @Cacheable(value = "adoptedWorkByProject", key = "#projectID")
    @GetMapping("/adoptedWork/project/{projectID}")
    @ResponseBody
    public Object getAdoptedWorkForAllSprints(@PathVariable("projectID") Integer projectID) {
        return adoptedWorkService.getAdoptedWorkForAllSprints(projectID, token);
    }

//    @GetMapping("/DoT/{projectID}")
//    @ResponseBody
//    public Object getClosedMilestonesbyID(@PathVariable("projectID") Integer projectID) {
//        return projectService.getClosedMilestonesbyID(projectID);
//    }
//
//    @GetMapping("/DoT/by-slug/{Slug}")
//    @ResponseBody
//    public Object getClosedMilestonesbySlug(@PathVariable("Slug") String Slug) {
//        return projectService.getClosedMilestonesbySlug(Slug);
//    }
//
//    @GetMapping("DoT/{projectID}/BV")
//    public @ResponseBody Object getClosedMilestonesforBV(@PathVariable("projectID") Integer projectID) {
//        return deliveryOnTimeService.getClosedMilestonesbyID(projectID);
//    }
//
//    @GetMapping("DoT/by-slug/{Slug}/BV")
//    public @ResponseBody Object getClosedMilestonesforBV(@PathVariable("Slug") String slug) {
//        return deliveryOnTimeService.getClosedMilestonesbySlug(slug);
//    }
//
    @GetMapping("/foundWork/{milestoneID}")
    @ResponseBody
    public Object getFoundWorkByID(@PathVariable("milestoneID") Integer milestoneID) {
        return foundWorkService.getFoundWork(milestoneID);
    }

}
