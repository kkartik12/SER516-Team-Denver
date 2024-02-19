package org.example.JavaTaigaCode.models;

import java.time.LocalDate;

public class UserStoryDTO {
    private Integer userStoryID;
    private Integer businessValue;
    private boolean isClosed;
    private LocalDate finishDate;
    private LocalDate createdDate;
    private Long leadTime;
    private Long cycleTime;

    public Integer getUserStoryID() {
        return userStoryID;
    }

    public LocalDate  getCreatedDate() {
        return createdDate;
    }

    public Long getLeadTime() {
        return leadTime;
    }
    
    public void setLeadTime(Long leadTime) {
        this.leadTime = leadTime;
    }

    public void setCreatedDate(LocalDate createdDate) { 
        this.createdDate = createdDate; 
    }

    public void setUserStoryID(Integer userStoryID) {
        this.userStoryID = userStoryID;
    }

    public Integer getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(Integer businessValue) {
        this.businessValue = businessValue;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Long getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Long cycleTime) {
        this.cycleTime = cycleTime;
    }

    public UserStoryDTO() {
    }

    public UserStoryDTO(LocalDate createdAt, LocalDate closedAt) {
        this.createdDate = createdAt;
        this.finishDate = closedAt;
    }

    public UserStoryDTO(Integer userStoryID, Integer businessValue, boolean isClosed, LocalDate finishDate, LocalDate createdDate) {
        this.userStoryID = userStoryID;
        this.businessValue = businessValue;
        this.isClosed = isClosed;
        this.finishDate = finishDate;
        this.createdDate = createdDate;
    }
}
