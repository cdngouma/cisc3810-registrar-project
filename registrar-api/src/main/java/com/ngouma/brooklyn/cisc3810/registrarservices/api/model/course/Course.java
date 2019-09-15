package com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    private Integer id;
    private Subject subject;
    @Positive
    private short level;
    private String courseName;
    @Positive
    private float units;
    private byte numPrerequisites;
    private byte numConflictingCourses;
    private List<Prerequisite> prerequisites;
    private List<ConflictingCourse> conflictingCourses;
    private String description;

    public Course(){ }

    public Course(Integer id, @NotNull String courseName, @Positive short level, Subject subject, @Positive float units, byte numPrerequisites, byte numConflictingCourses) {
        this.id = id;
        this.courseName = courseName;
        this.level = level;
        this.subject = subject;
        this.units = units;
        this.numPrerequisites = numPrerequisites;
        this.numConflictingCourses = numConflictingCourses;
    }

    public Course(Integer id, @NotNull String courseName, @Positive short level, Subject subject, @Positive float units, String description) {
        this.id = id;
        this.courseName = courseName;
        this.level = level;
        this.subject = subject;
        this.units = units;
        this.description = description;
    }

    public Course(Integer id, @NotNull String courseName, @Positive short level, Subject subject, @Positive float units, byte numPrerequisites,
                  byte numConflictingCourses, List<Prerequisite> prerequisites, List<ConflictingCourse> conflictingCourses, String description) {
        this.id = id;
        this.courseName = courseName;
        this.level = level;
        this.subject = subject;
        this.units = units;
        this.numPrerequisites = numPrerequisites;
        this.numConflictingCourses = numConflictingCourses;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getUnits() {
        return units;
    }

    public void setUnits(float units) {
        this.units = units;
    }

    public List<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public byte getNumPrerequisites() {
        return numPrerequisites;
    }

    public void setNumPrerequisites(byte numPrerequisites) {
        this.numPrerequisites = numPrerequisites;
    }

    public byte getNumConflictingCourses() {
        return numConflictingCourses;
    }

    public void setNumConflictingCourses(byte numConflictingCourses) {
        this.numConflictingCourses = numConflictingCourses;
    }

    public List<ConflictingCourse> getConflictingCourses() {
        return conflictingCourses;
    }

    public void setConflictingCourses(List<ConflictingCourse> conflictingCourses) {
        this.conflictingCourses = conflictingCourses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
