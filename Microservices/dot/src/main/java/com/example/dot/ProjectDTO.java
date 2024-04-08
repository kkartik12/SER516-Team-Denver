package com.example.dot;

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

    public Integer getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSlug() {
        return slug;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public String getDescription() {
        return description;
    }

//    public List<String> getMilestoneIds() {
//        return milestoneIds;
//    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<String> milestones) {
        this.milestones = milestones;
    }

    public double getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(double businessValue) {
        this.businessValue = businessValue;
    }

    public double getPartialRunningSum() {
        return partialRunningSum;
    }

    public void setPartialRunningSum(double partialRunningSum) {
        this.partialRunningSum = partialRunningSum;
    }

    public double getTotalRunningSum() {
        return totalRunningSum;
    }

    public void setTotalRunningSum(double totalRunningSum) {
        this.totalRunningSum = totalRunningSum;
    }

    public Integer getBvCustomAttributeID() {
        return bvCustomAttributeID;
    }

    public void setBvCustomAttributeID(Integer bvCustomAttributeID) {
        this.bvCustomAttributeID = bvCustomAttributeID;
    }

//    public void setMilestoneIds(List<String> milestoneIds) {
//        this.milestoneIds = milestoneIds;
//    }
//
//    public List<Boolean> getIsClosed() {
//        return isClosed;
//    }
//
//    public void setIsClosed(List<Boolean> isClosed) {
//        this.isClosed = isClosed;
//    }


    public List<MilestoneDTO> getMilestoneDetails() {
        return milestoneList;
    }

    public void setMilestoneDetails(List<MilestoneDTO> milestoneDetails) {
        this.milestoneList = milestoneDetails;
    }

    public ProjectDTO() {
    }

    public ProjectDTO(Integer projectID, String projectName, String slug, String description) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
        this.description = description;
    }

    public ProjectDTO(Integer projectID, String projectName, String slug,
                      LocalDate createdDate, LocalDate updatedDate, String description,
                      String owner, List<String> members, List<String> milestones){
//            , List<String> milestoneIds,
//            List<Boolean> isClosed) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.description = description;
        this.owner = owner;
        this.members = members;
        this.milestones = milestones;
//        this.milestoneIds = milestoneIds;
//        this.isClosed = isClosed;
    }

    @Override
    public String toString() {
        return "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'';
    }
}