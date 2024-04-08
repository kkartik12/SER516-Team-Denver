package com.example.dot;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // Allow access from frontend server (React)
@RequestMapping("/api")
public class DoTController {

    @Autowired
    ProjectService projectService;

    @Autowired
    DeliveryOnTimeService deliveryOnTimeService;

    @Cacheable(value="DoTbyProject", key = "#projectID")
    @GetMapping("/DoT/{projectID}")
    @ResponseBody
    public List<MilestoneDTO> getClosedMilestonesbyID(@PathVariable("projectID") Integer projectID, HttpServletRequest request) {
        String token = request.getHeader("token");
        return projectService.getClosedMilestonesbyID(projectID);
    }

    @Cacheable(value="DoTbySlug", key = "#Slug")
    @GetMapping("/DoT/by-slug/{Slug}")
    @ResponseBody
    public List<MilestoneDTO> getClosedMilestonesbySlug(@PathVariable("Slug") String Slug, HttpServletRequest request) {
        String token = request.getHeader("token");
        return projectService.getClosedMilestonesbySlug(Slug);
    }

    @Cacheable(value="DoTBVbyProject", key = "#projectID")
    @GetMapping("DoT/{projectID}/BV")
    public @ResponseBody List<MilestoneDTO> getClosedMilestonesforBV(@PathVariable("projectID") Integer projectID, HttpServletRequest request) {
        String token = request.getHeader("token");
        return deliveryOnTimeService.getClosedMilestonesbyID(projectID, token);
    }

    @Cacheable(value="DoTBVbySlug", key = "#projectID")
    @GetMapping("DoT/by-slug/{Slug}/BV")
    public @ResponseBody List<MilestoneDTO> getClosedMilestonesforBV(@PathVariable("Slug") String slug, HttpServletRequest request) {
        String token = request.getHeader("token");
        return deliveryOnTimeService.getClosedMilestonesbySlug(slug, token);
    }
}
