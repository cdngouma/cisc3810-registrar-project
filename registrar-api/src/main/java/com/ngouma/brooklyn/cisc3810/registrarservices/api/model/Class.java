package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Class {
    private Integer id;
    private String courseName;
    private String courseCode;
    private String instructor;
    private String semester;
    private Date startDate;
    private Date endDate;
    private List<Byte> meetingDays;
    private Time startTime;
    private Time endTime;
    private String mode;
    private String room;
    private short capacity;
    private short numEnrolledStudents;
    private boolean isOpened;

    public Class(){ }

    public Class(Integer id, String courseName, String courseCode, String instructor, String semester,
                 Date startDate, Date endDate, List<Byte> meetingDays, Time startTime, Time endTime,
                 String mode, String room, short capacity, short numEnrolledStudents, boolean isOpened) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetingDays = meetingDays;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.room = room;
        this.capacity = capacity;
        this.numEnrolledStudents = numEnrolledStudents;
        this.isOpened = isOpened;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Byte> getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(List<Byte> meetingDays) {
        this.meetingDays = meetingDays;
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

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public short getNumEnrolledStudents() {
        return numEnrolledStudents;
    }

    public void setNumEnrolledStudents(short numEnrolledStudents) {
        this.numEnrolledStudents = numEnrolledStudents;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
