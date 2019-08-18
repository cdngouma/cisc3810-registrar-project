package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

public class Course {
    private Long id;
    private Long subjectId;
    @Positive
    private int level;
    private String name;
    @Positive
    private double units;
    private String desc;

    private int numConflictingCourses;
    private int numPrerequisites;

    public Course(){}

    public Course(Long id, Long subjectId, int level, String name, double units, String desc) {
        this.id = id;
        this.subjectId = subjectId;
        this.level = level;
        this.name = name;
        this.units = units;
        this.desc = desc;
    }

    public Course(Long id, Long subjectId, int level, String name, double units, String desc, int numPrereq, int numConf) {
        this.id = id;
        this.subjectId = subjectId;
        this.level = level;
        this.name = name;
        this.units = units;
        this.desc = desc;
        this.numPrerequisites = numPrereq;
        this.numConflictingCourses = numConf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public int getNumPrerequisites() {
        return numPrerequisites;
    }

    public void setNumPrerequisites(int prerequisites) {
        this.numPrerequisites = prerequisites;
    }

    public int getNumConflictingCourses() {
        return numConflictingCourses;
    }

    public void setNumConflictingCourses(int conflictingCourses) {
        this.numConflictingCourses = conflictingCourses;
    }
}
