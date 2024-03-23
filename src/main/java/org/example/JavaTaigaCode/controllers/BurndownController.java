package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class BurndownController {
    @Autowired
    BurndownChart burndownChart;

//    @GetMapping("/projects/{projectID}/businessValue/{userStoryID}")
//    @ResponseBody
//    public Integer getBusinessValueForUserStory(@PathVariable("userStoryID") Integer userStoryID) {
//        return burndownChart.getBusinessValueForUserStory(userStoryID);
//
//    }

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
