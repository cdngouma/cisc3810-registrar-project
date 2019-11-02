package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassEntity {
    private Integer id;
    private Course course;
    private String instructor;
    private String semester;
    private Date startDate;
    private Date endDate;
    private List<Byte> days;
    private Time startTime;
    private Time endTime;
    private String mode;
    private String room;
    private short capacity;
    private short numEnrolledStudents;
    private String status;

    public ClassEntity(Integer id, Course course, String instructor, String semester, Date startDate, Date endDate, Time startTime,
                       Time endTime, String mode, String room, short capacity, short numEnrolledStudents, String status) {
        this.id = id;
        this.course = course;
        this.instructor = instructor;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.room = room;
        this.capacity = capacity;
        this.numEnrolledStudents = numEnrolledStudents;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Byte> getDays() {
        return days;
    }

    public void setDays(List<Byte> days) {
        this.days = days;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
