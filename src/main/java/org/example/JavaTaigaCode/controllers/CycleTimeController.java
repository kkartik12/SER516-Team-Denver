package org.example.JavaTaigaCode.controllers;

import java.util.List;

import org.example.JavaTaigaCode.models.TaskDTO;
import org.example.JavaTaigaCode.models.UserStoryDTO;
import org.example.JavaTaigaCode.service.CycleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CycleTimeController {
    @Autowired
    CycleTimeService cycleTimeService;

    @GetMapping("/cycleTime/US/{milestoneID}")
    @ResponseBody
    public List<UserStoryDTO> getUSCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getUSCycleTime(milestoneID);
    }

    @GetMapping("/cycleTime/Task/{milestoneID}")
    @ResponseBody
    public List<TaskDTO> getTaskCycleTime(@PathVariable("milestoneID") Integer milestoneID) {
        return cycleTimeService.getTaskCycleTime(milestoneID);
    }

}
