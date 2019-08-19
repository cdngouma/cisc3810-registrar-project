package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * TODO: find a better and more descriptive name for this class
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseWrapper {
    private Integer id;
    private Integer courseNo;
    private String courseName;
    private byte group;

    public CourseWrapper() { }

    public CourseWrapper(Integer courseNo, byte group) {
        this.courseNo = courseNo;
        this.group = group;
    }

    public CourseWrapper(Integer id, Integer courseNo, byte group) {
        this.id = id;
        this.courseNo = courseNo;
        this.group = group;
    }

    public CourseWrapper(Integer id, Integer courseNo, String courseName, byte group) {
        this.id = id;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.group = group;
    }

    public CourseWrapper(Integer id, Integer courseNo, String courseName) {
        this.id = id;
        this.courseNo = courseNo;
        this.courseName = courseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Integer courseNo) {
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

    public void setGroup(byte group) {
        this.group = group;
    }
}
