
package org.example.JavaTaigaCode.models;

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
    private Double totalPoints;
    private Integer bvTotal;
    private Integer bvCompleted;
    private Boolean errorCondition = false;

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

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public List<BurndownChartDTO> getTotalSumValue() {
        return totalSumValue;
    }

    public void setTotalSumValue(List<BurndownChartDTO> totalSumValue) {
        this.totalSumValue = totalSumValue;
    }

    public List<BurndownChartDTO> getPartialSumValue() {
        return partialSumValue;
    }

    public void setPartialSumValue(List<BurndownChartDTO> partialSumValue) {
        this.partialSumValue = partialSumValue;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public LocalDate getStart_date() {
        return start_date;
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

    public Integer getBvTotal() {
        return bvTotal;
    }

    public void setBvTotal(Integer bvTotal) {
        this.bvTotal = bvTotal;
    }

    public Integer getBvCompleted() {
        return bvCompleted;
    }

    public void setBvCompleted(Integer bvCompleted) {
        this.bvCompleted = bvCompleted;
    }

    public Boolean getErrorCondition() {
        return errorCondition;
    }

    public void setErrorCondition(Boolean errorCondition) {
        this.errorCondition = errorCondition;
    }

    public MilestoneDTO() {
    }

    public MilestoneDTO(Integer milestoneID, String milestoneName, LocalDate start_date, LocalDate end_date,
            List<BurndownChartDTO> totalSumValue, List<BurndownChartDTO> partialSumValue, Double totalPoints) {
        this.milestoneID = milestoneID;
        this.milestoneName = milestoneName;
        this.start_date = start_date;
        this.end_date = end_date;
        this.totalSumValue = totalSumValue;
        this.partialSumValue = partialSumValue;
        this.totalPoints = totalPoints;
    }
}
