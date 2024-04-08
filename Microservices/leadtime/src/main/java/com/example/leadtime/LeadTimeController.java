// package org.example.JavaTaigaCode.controllers;
package com.example.leadtime;

// import org.example.JavaTaigaCode.models.TaskDTO;
// import org.example.JavaTaigaCode.models.UserStoryDTO;
// import org.example.JavaTaigaCode.service.Tasks;
// import com.example.leadTime.TaskDTO;
// import com.example.leadTime.UserStoryDTO;
// import com.example.leadTime.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LeadTimeController {
    @Autowired
    Tasks taskService;

    @Cacheable(value="leadTimeUS", key = "#milestoneID")
    @GetMapping("/leadTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getLeadTimeUS(@PathVariable("milestoneID") Integer  milestoneID, HttpServletRequest request) {
      String token = request.getHeader("token"); 
      return taskService.calculateLeadTimeUS(milestoneID, token);
    }

    @Cacheable(value="leadTimeTask", key = "#milestoneID")
    @GetMapping("/leadTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTask(@PathVariable("milestoneID") Integer  milestoneID, HttpServletRequest request) {
      String token = request.getHeader("token");   
      return taskService.calculateLeadTimeTask(milestoneID, token);
    }

    @Cacheable(value="leadTimeUSbyTime")
    @GetMapping("/customleadTime/US")
    @ResponseBody
    public List<UserStoryDTO> getLeadTimeUSbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, HttpServletRequest request) {
      String token = request.getHeader("token");  
      return taskService.calculateLeadTimeUSbyTime(projectId, startDate, endDate, token);
    }

    @Cacheable(value="leadTimeTaskbyTime")
    @GetMapping("/customleadTime/Task")
    @ResponseBody
    public List<TaskDTO> getLeadTimeTaskbyTime(@RequestParam Integer projectId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, HttpServletRequest request) {
      String token = request.getHeader("token");
      return taskService.calculateLeadTimeTaskbyTime(projectId, startDate, endDate, token);
    }
}
