package com.example.adoptedWork;

import java.time.LocalDate;
import java.util.List;

public class MilestoneDTO {
    private Integer milestoneID;
    private String milestoneName;

    private LocalDate start_date;
    private LocalDate end_date;
    private Boolean isClosed;
    private List<BurndownChartDTO> totalSumValue;
    private List<BurndownChartDTO> partialSumValue;
    private List<BurndownChartDTO> totalSumBV;
    private Double totalPoints;
    private Integer bvTotal;
    private Integer bvCompleted;
    private Boolean errorCondition = false;
    private Double spCompleted;

    public Integer getMilestoneID() {
        return milestoneID;
    }

    public void setMilestoneID(Integer milestoneID) {
        this.milestoneID = milestoneID;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public void setClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }


    public void setSpCompleted(Double spCompleted) {
        this.spCompleted = spCompleted;
    }

    public MilestoneDTO() {
    }

    public MilestoneDTO(Integer milestoneID, String milestoneName, LocalDate start_date, LocalDate end_date,
            List<BurndownChartDTO> totalSumValue, List<BurndownChartDTO> totalSumBV,  List<BurndownChartDTO> partialSumValue, Double totalPoints) {
        this.milestoneID = milestoneID;
        this.milestoneName = milestoneName;
        this.start_date = start_date;
        this.end_date = end_date;
        this.totalSumValue = totalSumValue;
        this.totalSumBV = totalSumBV;
        this.partialSumValue = partialSumValue;
        this.totalPoints = totalPoints;
    }
}
