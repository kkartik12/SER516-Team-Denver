package com.example.burndown;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://54.89.25.90:8080"})
@RequestMapping("/api")
public class BurndownController {
    @Autowired
    BurndownChart burndownChart;

    @Cacheable(value="burnDown")
    @GetMapping("/burndownchart/{milestoneID}")
    @ResponseBody
    public MilestoneDTO getBurndownValues(@PathVariable("milestoneID") Integer milestoneID,
                                          @RequestParam Boolean totalSum, @RequestParam Boolean partialSum,
                                          @RequestParam Boolean BVSum, HttpServletRequest request) {
        String token = request.getHeader("token");
        MilestoneDTO milestoneDTO = null;
        if(totalSum) {
            milestoneDTO = burndownChart.calculateTotalRunningSum(milestoneID, token);
        }
        if(partialSum) {
            if(milestoneDTO == null) {
                milestoneDTO = burndownChart.calculatePartialRunningSum(milestoneID, token);
            } else {
               MilestoneDTO partialSumDTO =  burndownChart.calculatePartialRunningSum(milestoneID, token);
               milestoneDTO.setPartialSumValue(partialSumDTO.getPartialSumValue());
            }
        }
        if(BVSum) {
            if(milestoneDTO == null) {
                milestoneDTO = burndownChart.getTotalBusinessValue(milestoneID, token);
            } else {
                MilestoneDTO BVSumDTO =  burndownChart.getTotalBusinessValue(milestoneID, token);
                milestoneDTO.setTotalSumBV(BVSumDTO.getTotalSumBV());
                milestoneDTO.setBvTotal(BVSumDTO.getTotalPoints().intValue());
            }
        }

        return milestoneDTO;
    }

}
