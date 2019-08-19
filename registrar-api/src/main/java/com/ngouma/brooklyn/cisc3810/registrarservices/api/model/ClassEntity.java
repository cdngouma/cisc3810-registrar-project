package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Time;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassEntity {
    private Integer id;
    private Integer courseId;
    private String courseCode;
    private String courseName;
    private String instructor;

    private Integer semesterId;
    private String semester;
    private Time startTime;
    private Time endTime;
    private String mode;
    private String room;
    private short capacity;
    private short numEnrolledStudents;
    private boolean opened;

    public ClassEntity(){ }

    public ClassEntity(Integer id, Integer courseId, String instructor, Integer semesterId, Time startTime, Time endTime, String mode,
                       String room, short capacity, short numEnrolledStudents, boolean opened) {
        this.id = id;
        this.courseId = courseId;
        this.instructor = instructor;
        this.semesterId = semesterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.room = room;
        this.capacity = capacity;
        this.numEnrolledStudents = numEnrolledStudents;
        this.opened = opened;
    }

    public ClassEntity(Integer id, String courseCode, String courseName, String instructor, String semester, Time startTime, Time endTime,
                       String mode, String room, short capacity, short numEnrolledStudents, boolean opened) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        this.semester = semester;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.room = room;
        this.capacity = capacity;
        this.numEnrolledStudents = numEnrolledStudents;
        this.opened = opened;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public int getNumEnrolledStudents() {
        return numEnrolledStudents;
    }

    public void setNumEnrolledStudents(short numEnrolledStudents) {
        this.numEnrolledStudents = numEnrolledStudents;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
