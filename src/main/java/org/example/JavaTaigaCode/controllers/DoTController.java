package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.service.DeliveryOnTimeService;
import org.example.JavaTaigaCode.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow access from frontend server (React)
@RequestMapping("/api")
public class DoTController {

    @Autowired
    ProjectService projectService;

    @Autowired
    DeliveryOnTimeService deliveryOnTimeService;

    @GetMapping("/DoT/{projectID}")
    @ResponseBody
    public List<MilestoneDTO> getClosedMilestonesbyID(@PathVariable("projectID") Integer projectID) {
        return projectService.getClosedMilestonesbyID(projectID);
    }

    @GetMapping("/DoT/by-slug/{Slug}")
    @ResponseBody
    public List<MilestoneDTO> getClosedMilestonesbySlug(@PathVariable("Slug") String Slug) {
        return projectService.getClosedMilestonesbySlug(Slug);
    }

    @GetMapping("DoT/{projectID}/BV")
    @ResponseBody List<MilestoneDTO> getClosedMilestonesforBV(@PathVariable("projectID") Integer projectID) {
        return deliveryOnTimeService.getClosedMilestonesbyID(projectID);
    }
}
