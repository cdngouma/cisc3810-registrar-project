package com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConflictingCourse {
    private Integer id;
    private String courseName;

    public ConflictingCourse(){ }

    public ConflictingCourse(Integer id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
