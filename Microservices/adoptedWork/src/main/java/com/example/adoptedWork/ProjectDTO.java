package com.example.adoptedWork;

import java.time.LocalDate;
import java.util.List;

public class ProjectDTO {
    Integer projectID;
    String projectName;
    String slug;
    LocalDate createdDate;
    LocalDate updatedDate;
    String description;
    String owner;
    List<String> members;
    List<String> milestones;
//    List<String> milestoneIds;
//    List<Boolean> isClosed;
    List<MilestoneDTO> milestoneList;
    Double businessValue = 0.0;
    Double partialRunningSum = 0.0;
    Double totalRunningSum = 0.0;
    Integer bvCustomAttributeID;

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setMilestones(List<String> milestones) {
        this.milestones = milestones;
    }

    public void setBvCustomAttributeID(Integer bvCustomAttributeID) {
        this.bvCustomAttributeID = bvCustomAttributeID;
    }

    public List<MilestoneDTO> getMilestoneDetails() {
        return milestoneList;
    }

    public void setMilestoneDetails(List<MilestoneDTO> milestoneDetails) {
        this.milestoneList = milestoneDetails;
    }

    public ProjectDTO() {
    }

    @Override
    public String toString() {
        return "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'';
    }
}
