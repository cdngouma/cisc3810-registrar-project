package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * TODO: find a better and more descriptive name for this class
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseWrapper {
    private Long id;
    private Long courseNo;
    private String courseName;
    private int group;

    public CourseWrapper() { }

    public CourseWrapper(Long courseNo, int group) {
        this.courseNo = courseNo;
        this.group = group;
    }

    public CourseWrapper(Long id, Long courseNo, int group) {
        this.id = id;
        this.courseNo = courseNo;
        this.group = group;
    }

    public CourseWrapper(Long id, Long courseNo, String courseName, int group) {
        this.id = id;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.group = group;
    }

    public CourseWrapper(Long id, Long courseNo, String courseName) {
        this.id = id;
        this.courseNo = courseNo;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Long courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
