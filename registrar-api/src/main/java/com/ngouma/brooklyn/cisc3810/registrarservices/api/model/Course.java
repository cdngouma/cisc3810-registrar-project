package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    private Integer id;
    private Integer subjectId;
    private String subjectName;
    @Positive
    private short level;
    private String name;
    @Positive
    private float units;
    private String desc;

    private byte numConflictingCourses;
    private byte numPrerequisites;

    public Course(){}

    public Course(Integer id, String courseNameLong){
        this.id = id;
        this.name = courseNameLong;
    }

    public Course(Integer id, Integer subjectId, short level, String name, float units, String desc) {
        this.id = id;
        this.subjectId = subjectId;
        this.level = level;
        this.name = name;
        this.units = units;
        this.desc = desc;
    }

    public Course(Integer id, Integer subjectId, String subjectName, short level, String name, float units, String desc, byte numPrereq, byte numConf) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.level = level;
        this.name = name;
        this.units = units;
        this.desc = desc;
        this.numPrerequisites = numPrereq;
        this.numConflictingCourses = numConf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnits() {
        return units;
    }

    public void setUnits(float units) {
        this.units = units;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public byte getNumPrerequisites() {
        return numPrerequisites;
    }

    public void setNumPrerequisites(byte prerequisites) {
        this.numPrerequisites = prerequisites;
    }

    public byte getNumConflictingCourses() {
        return numConflictingCourses;
    }

    public void setNumConflictingCourses(byte conflictingCourses) {
        this.numConflictingCourses = conflictingCourses;
    }
}
