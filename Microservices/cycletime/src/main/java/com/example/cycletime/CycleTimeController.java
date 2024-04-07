package com.example.cycletime;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://54.89.25.90:8080"})
@RequestMapping("/api")
public class CycleTimeController {
    @Autowired
    CycleTimeService cycleTimeService;

    @Cacheable(value = "cycleTimeForUS", key = "#milestoneID")
    @GetMapping("/cycleTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getUSCycleTime(@PathVariable("milestoneID") Integer milestoneID, HttpServletRequest request) {
        String token = request.getHeader("token");
        return cycleTimeService.getUSCycleTime(milestoneID, token);
    }

    @Cacheable(value = "cycleTimeForTask", key = "#milestoneID")
    @GetMapping("/cycleTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getTaskCycleTime(@PathVariable("milestoneID") Integer milestoneID, HttpServletRequest request) {
        String token = request.getHeader("token");
        return cycleTimeService.getTaskCycleTime(milestoneID, token);
    }

    @Cacheable(value = "USCycleTimeByDates")
    @GetMapping("/cycleTime/US/byTime/{projectId}")
    @ResponseBody
    public List<UserStoryDTO> getUSCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate, HttpServletRequest request) {
        String token = request.getHeader("token");
        return cycleTimeService.calculateUSCycleTimebyDates(projectId, startDate, endDate, token);
    }

    @Cacheable(value = "taskCycleTimeByDates")
    @GetMapping("/cycleTime/Task/byTime/{projectId}")
    @ResponseBody
    public List<TaskDTO> getTaskCycleTimebyDates(@PathVariable Integer projectId, @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate, HttpServletRequest request) {
        String token = request.getHeader("token");
        return cycleTimeService.calculateTaskCycleTimebyDates(projectId, startDate, endDate, token);
    }

}
