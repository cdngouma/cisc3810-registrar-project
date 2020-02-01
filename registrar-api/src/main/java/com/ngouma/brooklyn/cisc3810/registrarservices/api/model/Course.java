package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "courses")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    @NotBlank
    @Column(unique = true)
    private String courseCode;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @NotBlank
    private String courseName;

    @PositiveOrZero
    private short courseLevel;

    @PositiveOrZero
    @Column(name = "course_units")
    private float units;

    private String prerequisites;

    private String conflictingCourses;

    @Column(name = "course_desc")
    private String courseDesc;

    @OneToMany(mappedBy = "course")
    private List<ClassEntity> classes;

    protected Course() {}

    public Course(int id, Subject subject, String courseName, short courseLevel, float units, String courseDesc) {
        this.id = id;
        this.subject = subject;
        this.courseName = courseName;
        this.courseLevel = courseLevel;
        this.units = units;
        this.courseDesc = courseDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public short getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(short courseLevel) {
        this.courseLevel = courseLevel;
    }

    public float getUnits() {
        return units;
    }

    public void setUnits(float units) {
        this.units = units;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getConflictingCourses() {
        return conflictingCourses;
    }

    public void setConflictingCourses(String conflictingCourses) {
        this.conflictingCourses = conflictingCourses;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    @Override
    public String toString() {
        return String.format("Course : {id : %d, subjectId : d, courseName : \"%s\", courseLevel : %d, units : %f}",
                this.id, this.courseName, this.courseLevel, this.units);
    }
}