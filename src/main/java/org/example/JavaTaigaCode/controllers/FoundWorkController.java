package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.service.FoundWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow access from frontend server (React)
@RequestMapping("/api")
public class FoundWorkController {

    @Autowired
    FoundWorkService foundWorkService;

    @Cacheable(value="foundWork", key = "#milestoneID")
    @GetMapping("/foundWork/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getFoundWorkByID(@PathVariable("milestoneID") Integer milestoneID) {
        return foundWorkService.getFoundWork(milestoneID);
    }

}
