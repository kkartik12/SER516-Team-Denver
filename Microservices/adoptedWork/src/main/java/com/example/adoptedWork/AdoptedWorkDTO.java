package com.example.adoptedWork;

public class AdoptedWorkDTO {
    Integer adoptedWork;
    Integer sprintTotalPoints;
    Integer milestoneID;
    String milestoneName;

    public Integer getAdoptedWork() {
        return adoptedWork;
    }

    public void setAdoptedWork(Integer adoptedWork) {
        this.adoptedWork = adoptedWork;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }
    
    public AdoptedWorkDTO(Integer adoptedWork, Integer sprintTotalPoints, Integer milestoneID) {
        this.adoptedWork = adoptedWork;
        this.sprintTotalPoints = sprintTotalPoints;
        this.milestoneID = milestoneID;
    }
}
