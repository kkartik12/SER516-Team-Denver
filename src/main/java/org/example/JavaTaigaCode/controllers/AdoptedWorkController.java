package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.AdoptedWorkDTO;
import org.example.JavaTaigaCode.service.AdoptedWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://54.89.25.90:8080"})
@RequestMapping("/api")
public class AdoptedWorkController {
    @Autowired
    AdoptedWorkService adoptedWorkService;

    @Cacheable(value = "adoptedWorkByMilestone", key = "#milestoneID")
    @GetMapping("/adoptedWork/{milestoneID}")
    @ResponseBody
    public AdoptedWorkDTO getUSAddedAfterSprintPlanning(@PathVariable("milestoneID") Integer milestoneID) {
        return adoptedWorkService.getUSAddedAfterSprintPlanning(milestoneID);
    }
    @Cacheable(value = "adoptedWorkByProject", key = "#projectID")
    @GetMapping("/adoptedWork/project/{projectID}")
    @ResponseBody
    public List<AdoptedWorkDTO> getAdoptedWorkForAllSprints(@PathVariable("projectID") Integer projectID) {
        return adoptedWorkService.getAdoptedWorkForAllSprints(projectID);
    }
}
