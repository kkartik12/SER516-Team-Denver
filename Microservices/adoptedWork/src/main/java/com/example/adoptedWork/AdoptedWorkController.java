package org.example.adoptedWork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
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
