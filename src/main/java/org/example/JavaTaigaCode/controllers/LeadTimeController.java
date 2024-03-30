package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.service.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://54.89.25.90:8080"})
@RequestMapping("/api")
public class LeadTimeController {
    @Autowired
    Tasks taskService;

    @Cacheable(value="leadTimeUS", key = "#milestoneID")
    @GetMapping("/leadTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getLeadTimeUS(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeUS(milestoneID);
    }

    @Cacheable(value="leadTimeTask", key = "#milestoneID")
    @GetMapping("/leadTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTask(@PathVariable("milestoneID") Integer  milestoneID) {
        return taskService.calculateLeadTimeTask(milestoneID);
    }

    // @Cacheable(value="leadTimeUSbyTime")
    // @GetMapping("/leadTime/US?project={projectId}&startDate={startDate}&endDate={endDate}")
    // @ResponseBody
    // public List<UserStoryDTO> getLeadTimeUSbyTime(@PathVariable("projectId") Integer projectId, @PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
    //     return taskService.calculateLeadTimeUSbyTime(projectId, startDate, endDate);
    // }

    @Cacheable(value="leadTimeUSbyTime")
    @GetMapping("/customleadTime/US")
    @ResponseBody
    public List<UserStoryDTO> getLeadTimeUSbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return taskService.calculateLeadTimeUSbyTime(projectId, startDate, endDate);
    }

    // @Cacheable(value="leadTimeTaskbyTime")
    // @GetMapping("/leadTime/Task?project={projectId}&startDate={startDate}&endDate={endDate}")
    // @ResponseBody
    // public List<TaskDTO> getLeadTimeTaskbyTime(@PathVariable("projectId") Integer projectId, @PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
    //     return taskService.calculateLeadTimeTaskbyTime(projectId, startDate, endDate);
    // }

    @Cacheable(value="leadTimeTaskbyTime")
    @GetMapping("/customleadTime/Task")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTaskbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return taskService.calculateLeadTimeTaskbyTime(projectId, startDate, endDate);
    }
}
