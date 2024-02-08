package org.example.JavaTaigaCode.models;

import java.time.LocalDate;

public class ProjectDTO {
    Integer projectID;
    String projectName;
    String slug;
    LocalDate createdDate;
    String description;

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

    public ProjectDTO() {
    }

    public ProjectDTO(Integer projectID, String projectName, String slug, LocalDate createdDate, String description) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
        this.description = description;
        this.createdDate = createdDate;
    }

    public ProjectDTO(Integer projectID, String projectName, String slug) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", slug='" + slug + '\'';
   }
}
