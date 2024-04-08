package com.example.project;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Cacheable(value = "projectList", key = "#memberID")
    @GetMapping("/projectList/{memberID}")
    @ResponseBody
    public List<ProjectDTO> getProjectList(@PathVariable("memberID") Integer memberID, HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            List<ProjectDTO> projects = projectService.getPojectList(memberID, token);

            if (projects != null) {
                for (ProjectDTO project : projects) {
                    System.out.println(project.toString());
                }
            } else {
                throw new RuntimeException("Unable to get Project List for user");
            }
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error: ", e);
        }

    }

    @Cacheable(value = "projectByID", key = "#projectID")
    @GetMapping("/projects/{projectID}")
    @ResponseBody
    public ProjectDTO getProjectDetails(@PathVariable("projectID") Integer projectID, HttpServletRequest request) {
        String token = request.getHeader("token");
        ProjectDTO project = projectService.getPojectDetails(projectID, token);

        if (project == null) {
            throw new RuntimeException("Unable to get Project List for user");
        }
        return project;
    }

    @Cacheable(value = "projectBySlug", key = "#Slug")
    @GetMapping("/projects/by-slug/{slug}")
    @ResponseBody
    public ProjectDTO getProjectDetailsSlug(@PathVariable("slug") String Slug, HttpServletRequest request) {
        String token = request.getHeader("token");
        ProjectDTO project = projectService.getProjectDetailsSlug(Slug, token);

        if (project == null) {
            throw new RuntimeException("Unable to get Project List for user");
        }
        return project;
    }

}
