package com.ngoum.cisc3810.registrar.registrarserver.model;

public class Course {
    private int courseNo;
    private String courseSubject;
    private int courseLevel;
    private String courseName;
    private double units;
    private String courseDesc;

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

    /*@Override
    public String toString(){
        return String.format("[courseNo: %d, courseSubject: %s, courseLevel %d, courseName %s, units %f, courseDesc %s",
                this.courseNo, this.courseSubject, this.courseLevel, this.courseName, this.units, this.courseDesc);
    }*/
}
