package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Time;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassEntity {
    private Long id;
    private Course course;
    private String courseName;
    private String instructor;

    private String semester;
    private Time startTime;
    private Time endTime;
    private String mode;
    private String room;
    private int capacity;
    private int numEnrolledStudents;
    private boolean opened;

    public ClassEntity(Long id, Course course, String semester, Time startTime, Time endTime, String mode, boolean opened) {
        this.id = id;
        this.course = course;
        this.semester = semester;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.opened = opened;
    }

    public ClassEntity(Long id, Course course, String courseName, String instructor, Time startTime, Time endTime,
                       String mode, String room, int capacity, int numEnrolledStudents, boolean opened) {
        this.id = id;
        this.course = course;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumEnrolledStudents() {
        return numEnrolledStudents;
    }

    public void setNumEnrolledStudents(int numEnrolledStudents) {
        this.numEnrolledStudents = numEnrolledStudents;
    }

    public boolean getOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
