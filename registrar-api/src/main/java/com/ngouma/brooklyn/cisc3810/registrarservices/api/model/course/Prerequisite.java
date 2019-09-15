package com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prerequisite {
    private Integer prerequisiteId;
    private byte group;
    private String courseName;

    public Prerequisite() { }

    public Prerequisite(Integer prerequisiteId, String courseName, byte group) {
        this.prerequisiteId = prerequisiteId;
        this.group = group;
        this.courseName = courseName;
    }

    public Integer getPrerequisiteId() {
        return prerequisiteId;
    }

    public void setPrerequisiteId(Integer prerequisiteId) {
        this.prerequisiteId = prerequisiteId;
    }

    public byte getGroup() {
        return group;
    }

    public void setGroup(byte group) {
        this.group = group;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}


