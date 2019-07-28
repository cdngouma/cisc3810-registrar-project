package com.ngoum.cisc3810.registrar.registrarserver.model;

import java.util.List;

public class Course {
    private int courseNo;
    private String courseSubject;
    private int courseLevel;
    private String courseName;
    private double units;
    private String courseDesc;

    private List<Course> prerequisits;
    private List<Course> conflictingCourses;

    public Course(){ }

    public Course(int courseNo, String courseSubject, int courseLevel, String courseName, double units,
                  String courseDesc, List<Course> prerequisits, List<Course> conflictingCourses) {
        this.courseNo = courseNo;
        this.courseSubject = courseSubject;
        this.courseLevel = courseLevel;
        this.courseName = courseName;
        this.units = units;
        this.courseDesc = courseDesc;
        this.prerequisits = prerequisits;
        this.conflictingCourses = conflictingCourses;
    }

    public Course(int courseNo, String courseSubject, int courseLevel, String courseName, double units) {
        this.courseNo = courseNo;
        this.courseSubject = courseSubject;
        this.courseLevel = courseLevel;
        this.courseName = courseName;
        this.units = units;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public int getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(int courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public List<Course> getPrerequisits() {
        return prerequisits;
    }

    public void setPrerequisits(List<Course> prerequisits) {
        this.prerequisits = prerequisits;
    }

    public List<Course> getConflictingCourses() {
        return conflictingCourses;
    }

    public void setConflictingCourses(List<Course> conflictingCourses) {
        this.conflictingCourses = conflictingCourses;
    }
}
