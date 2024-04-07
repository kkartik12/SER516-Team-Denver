package com.example.burndown;

import java.time.LocalDate;


public class TaskDTO {
    private Integer taskID;
    private String taskName;
    private Boolean isClosed;
    private LocalDate closedDate;
    private LocalDate createdDate;
    private Long leadTime;

    private Long cycleTime;

    public Integer getTaskID() {
        return taskID;
    }

    public LocalDate getCreatedDate() {
        return  createdDate;
    }

    public Long getLeadTime() {
        return  leadTime;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setCreatedDate(LocalDate date) {
        this.createdDate=date;
    }

    public void setLeadTime(Long duration) {
        this.leadTime = duration;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDate closedDate) {
        this.closedDate = closedDate;
    }

    public Long getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Long cycleTime) {
        this.cycleTime = cycleTime;
    }

    public TaskDTO() {
    }

    public TaskDTO(LocalDate createdDate, LocalDate closedDate) {
        this.createdDate = createdDate;
        this.closedDate = closedDate;
    }

    public TaskDTO(Integer taskID, String taskName, Boolean isClosed, LocalDate closedDate) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.isClosed = isClosed;
        this.closedDate = closedDate;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "taskID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", isClosed=" + isClosed +
                ", closedDate=" + closedDate +
                ", createdDate=" + createdDate +
                ", leadTime=" + leadTime +
                ", cycleTime=" + cycleTime +
                '}';
    }
}
