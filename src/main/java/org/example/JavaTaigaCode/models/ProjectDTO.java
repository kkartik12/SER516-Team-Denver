package org.example.JavaTaigaCode.models;

import java.time.LocalDate;
import java.util.List;

public class ProjectDTO {
    Integer projectID;
    String projectName;
    String slug;
    LocalDate createdDate;
    String description;
    String owner;
    List<String> members;
    List<String> milestones;

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

    public String getDescription() {
        return description;
    }

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

    public ProjectDTO() {
    }

    public ProjectDTO(Integer projectID, String projectName, String slug) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
    }

    public ProjectDTO(Integer projectID, String projectName, String slug, LocalDate createdDate, String description, String owner, List<String> members, List<String> milestones) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
        this.createdDate = createdDate;
        this.description = description;
        this.owner = owner;
        this.members = members;
        this.milestones = milestones;
    }

    @Override
    public String toString() {
        return "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", slug='" + slug + '\'';
   }
}
