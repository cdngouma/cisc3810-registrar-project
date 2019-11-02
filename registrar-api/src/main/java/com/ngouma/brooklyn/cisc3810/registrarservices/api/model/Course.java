package com.ngouma.brooklyn.cisc3810.registrarservices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.constraints.Positive;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    private Integer id;
    private String courseName;
    private Subject subject;
    @Positive
    private short level;
    @Positive
    private float units;
    private Byte numPrerequisites;
    private Byte numConflictingCourses;
    private List<Prerequisite> prerequisites;
    private List<ConflictingCourse> conflictingCourses;
    private String description;

    public Course(){ }

    public Course(Integer id, String courseName, Subject subject, @Positive short level, @Positive float units) {
        this.id = id;
        this.subject = subject;
        this.level = level;
        this.courseName = courseName;
        this.units = units;
    }

    public Course(Integer id, Subject subject, @Positive short level, String courseName, @Positive float units, byte numPrerequisites, byte numConflictingCourses) {
        this.id = id;
        this.subject = subject;
        this.level = level;
        this.courseName = courseName;
        this.units = units;
        this.numPrerequisites = numPrerequisites;
        this.numConflictingCourses = numConflictingCourses;
    }

    public Course(Integer id, Subject subject, @Positive short level, String courseName, @Positive float units, String description) {
        this.id = id;
        this.subject = subject;
        this.level = level;
        this.courseName = courseName;
        this.units = units;
        this.description = description;
    }

    public Course(int course_id, String course_name, Subject subject, short course_level, String course_name1, float units, String course_desc) {

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

    public boolean queryForPrerequisites(JdbcTemplate jdbcTemplate) {
        final String QUERY = "SELECT P.id, CONCAT(subjects.subject_name_short,' ', courses.course_level) AS courseName, P.prereq_group\n" +
                "FROM Prerequisites P JOIN courses ON P.prereq_id = courses.id JOIN subjects ON courses.subject_id = subjects.id WHERE P.id = ?";
        List<Prerequisite> list = jdbcTemplate.query(QUERY, new Object[]{this.id}, (rs, numRow) ->
                new Prerequisite(
                        rs.getInt("id"),
                        rs.getString("courseName"),
                        rs.getByte("prereq_group")
                )
        );
        if (list.size() > 0) {
            this.prerequisites = list;
            this.numPrerequisites = (byte) this.prerequisites.size();
            return true;
        }
        return false;
    }

    public boolean queryForConflictingCourses(JdbcTemplate jdbcTemplate) {
        final String QUERY = "SELECT C.id, CONCAT(subjects.subject_name_short,' ', courses.course_level) AS courseName\n" +
                "FROM conflicting_courses C JOIN courses ON C.conf_course_id = courses.id JOIN subjects ON courses.subject_id = subjects.id\n" +
                "WHERE C.course_id = ?";
        List<ConflictingCourse> list = jdbcTemplate.query(QUERY, new Object[]{this.id}, (rs, numRow) ->
                new ConflictingCourse(
                        rs.getInt("id"),
                        rs.getString("courseName")
                )
        );
        if (list.size() > 0) {
            this.conflictingCourses = list;
            this.numConflictingCourses = (byte) this.conflictingCourses.size();
            return true;
        }
        return false;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class Prerequisite {
    private Integer id;
    private byte group;
    private String courseName;

    public Prerequisite() { }

    public Prerequisite(Integer id, String courseName, byte group) {
        this.id = id;
        this.group = group;
        this.courseName = courseName;
    }

    public Integer getPrerequisiteId() {
        return id;
    }

    public void setPrerequisiteId(Integer prerequisiteId) {
        this.id = prerequisiteId;
    }

    public byte getGroup() {
        return group;
    }

    public void setGroup(byte group) {
        this.group = group;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class ConflictingCourse {
    private Integer id;
    private String courseName;

    public ConflictingCourse(){ }

    public ConflictingCourse(Integer id, String courseName) {
        this.id = id;
        this.courseName = courseName;
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
}
