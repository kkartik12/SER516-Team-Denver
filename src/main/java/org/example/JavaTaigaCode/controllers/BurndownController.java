package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.models.MilestoneDTO;
import org.example.JavaTaigaCode.service.BurndownChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class BurndownController {
    @Autowired
    BurndownChart burndownChart;

    @Cacheable(value="burnDown")
    @GetMapping("/burndownchart/{milestoneID}")
    @ResponseBody
    public MilestoneDTO getBurndownValues(@PathVariable("milestoneID") Integer milestoneID,
                                          @RequestParam Boolean totalSum, @RequestParam Boolean partialSum,
                                          @RequestParam Boolean BVSum) {
        MilestoneDTO milestoneDTO = null;
        if(totalSum) {
            milestoneDTO = burndownChart.calculateTotalRunningSum(milestoneID);
        }
        if(partialSum) {
            if(milestoneDTO == null) {
                milestoneDTO = burndownChart.calculatePartialRunningSum(milestoneID);
            } else {
               MilestoneDTO partialSumDTO =  burndownChart.calculatePartialRunningSum(milestoneID);
               milestoneDTO.setPartialSumValue(partialSumDTO.getPartialSumValue());
            }
        }
        if(BVSum) {
            if(milestoneDTO == null) {
                milestoneDTO = burndownChart.getTotalBusinessValue(milestoneID);
            } else {
                MilestoneDTO BVSumDTO =  burndownChart.getTotalBusinessValue(milestoneID);
                milestoneDTO.setTotalSumBV(BVSumDTO.getTotalSumBV());
                milestoneDTO.setBvTotal(BVSumDTO.getTotalPoints().intValue());
            }
        }

        return milestoneDTO;
    }

}