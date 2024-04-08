package com.example.adoptedWork;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AdoptedWorkController {
    @Autowired
    AdoptedWorkService adoptedWorkService;

    @Cacheable(value = "adoptedWorkByMilestone", key = "#milestoneID")
    @GetMapping("/adoptedWork/{milestoneID}")
    @ResponseBody
    public AdoptedWorkDTO getUSAddedAfterSprintPlanning(@PathVariable("milestoneID") Integer milestoneID, HttpServletRequest request) {
        String token = request.getHeader("token");
        return adoptedWorkService.getUSAddedAfterSprintPlanning(milestoneID, token);
    }
    @Cacheable(value = "adoptedWorkByProject", key = "#projectID")
    @GetMapping("/adoptedWork/project/{projectID}")
    @ResponseBody
    public List<AdoptedWorkDTO> getAdoptedWorkForAllSprints(@PathVariable("projectID") Integer projectID,  HttpServletRequest request) {
        String token = request.getHeader("token");
        return adoptedWorkService.getAdoptedWorkForAllSprints(projectID, token);
    }
}
