package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.service.CycleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CycleTimeController {
    @Autowired
    CycleTimeService cycleTimeService;

    @Cacheable(value = "cycleTimeForUS", key = "#milestoneID")
    @GetMapping("/cycleTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getUSCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getUSCycleTime(milestoneID);
    }

    @Cacheable(value = "cycleTimeForTask", key = "#milestoneID")
    @GetMapping("/cycleTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getTaskCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getTaskCycleTime(milestoneID);
    }

    @Cacheable(value = "USCycleTimeByDates")
    @GetMapping("/cycleTime/US/byTime/{projectId}")
    @ResponseBody
    public List<UserStoryDTO> getUSCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return cycleTimeService.calculateUSCycleTimebyDates(projectId, startDate, endDate);
    }

    @Cacheable(value = "taskCycleTimeByDates")
    @GetMapping("/cycleTime/Task/byTime/{projectId}")
    @ResponseBody
    public List<TaskDTO> getTaskCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return cycleTimeService.calculateTaskCycleTimebyDates(projectId, startDate, endDate);
    }

}
