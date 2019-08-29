package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.CourseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class CourseRepo {
    @Autowired
    private JdbcTemplate jdbc;

    // COURSE CORE

    public Course save(Course c) {
        try {
            return jdbc.queryForObject("CALL EDIT_COURSES(?,?,?,?,?,?)", new Object[]{c.getSubjectId(), c.getLevel(),
                    c.getName(), c.getUnits(), c.getDescription()}, (rs, numRow) ->
                    new Course(
                            rs.getInt("course_no"),
                            rs.getInt("subj_no"),
                            rs.getShort("course_level"),
                            rs.getString("course_name"),
                            rs.getFloat("units"),
                            rs.getString("course_desc")
                    )
            );
        }catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Course updateCourse(Integer courseId, Course c) {
        try {
            return jdbc.queryForObject("CALL EDIT_COURSES(?,?,?,?,?,?)", new Object[]{c.getSubjectId(), c.getLevel(),
                    c.getName(), c.getUnits(), c.getDescription()}, (rs, numRow) ->
                    new Course(
                            rs.getInt("course_no"),
                            rs.getInt("subj_no"),
                            rs.getShort("course_level"),
                            rs.getString("course_name"),
                            rs.getFloat("units"),
                            rs.getString("course_desc")
                    )
            );
        }catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * @param subjId
     * @return if subject Id is provided return a list of all course under to the given subject
     * else return a list of all courses
     */
    public List<Course> findAllCourses(Integer subjId) {
        return jdbc.query("CALL FIND_ALL_COURSES(?)", new Object[]{subjId}, (rs, numRow) ->
                new Course(
                        rs.getInt("C.course_no"),
                        rs.getInt("C.subj_no"),
                        rs.getString("S.subj_abv"),
                        rs.getShort("C.course_level"),
                        rs.getString("C.course_name"),
                        rs.getFloat("C.units"),
                        rs.getString("C.course_desc"),
                        rs.getByte("numPrereq"),
                        rs.getByte("numConflicting")
                )
        );
    }

    public Course findCourseById(Integer courseId) {
        try {
            return jdbc.queryForObject("CALL FIND_COURSE_BY_ID(?)", new Object[]{courseId}, (rs, numRow) ->
                    new Course(
                            rs.getInt("C.course_no"),
                            rs.getInt("C.subj_no"),
                            rs.getString("S.subj_abv"),
                            rs.getShort("C.course_level"),
                            rs.getString("C.course_name"),
                            rs.getFloat("C.units"),
                            rs.getString("C.course_desc"),
                            rs.getByte("numPrereq"),
                            rs.getByte("numConflicting")
                    )
            );
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean deleteCourseById(Integer courseId) {
        return jdbc.update("DELETE FROM Courses WHERE course_no=?", courseId) > 0;
    }

    // PREREQUISITES AND CONFLICTING COURSES

    /**
     * @param courseId
     * @return all the given course prerequisites
     */
    public List<CourseWrapper> findAllPrerequisites(Integer courseId) {
        return jdbc.query("CALL FIND_ALL_COURSE_PREREQ(?)", new Object[]{courseId}, (rs, numRow) ->
                new CourseWrapper(
                        rs.getInt("P.prereq_no"),
                        rs.getInt("course_no"),
                        rs.getString("course"),
                        rs.getByte("group")
                )
        );
    }

    /**
     * @param courseId
     * @return all courses conflicting with the given course
     */
    public List<CourseWrapper> findAllConflicting(Integer courseId) {
        return jdbc.query("CALL FIND_ALL_CONFLICTING_COURSES(?)", new Object[]{courseId}, (rs, numRow) ->
                new CourseWrapper(
                        rs.getInt("K.conf_no"),
                        rs.getInt("course_no"),
                        rs.getString("course")
                )
        );
    }

    /**
     * add prerequisite to a course
     * @param courseId
     * @param prereq
     * @return updated course
     */
    public Course addPrerequisite(Integer courseId, CourseWrapper prereq) {
        try {
            /* update prereq */
            jdbc.update("INSERT INTO Prerequisites(course_no,sec_course_no,prereq_group) VALUES(?,?,?)",
                    courseId, prereq.getCourseNo(), prereq.getGroup());
            /* return updated course */
            return this.findCourseById(courseId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * add conflicting courses to a course
     * @param courseId
     * @param confCourse
     * @return updated course
     */
    public Course addConflictingCourse(Integer courseId, CourseWrapper confCourse) {
        try {
            /* update conflicting course */
            jdbc.update("INSERT INTO ConflictingCourses(course_no,sec_course_no) VALUES(?,?)",
                    courseId, confCourse.getCourseNo());
            /* return updated course */
            return this.findCourseById(courseId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean deletePrerequisiteById(Integer id) {
        return jdbc.update("DELETE FROM Prerequisites WHERE prereq_no=?", id) > 0;
    }

    public boolean deleteConflictingById(Integer id) {
        return jdbc.update("DELETE FROM Conflicting_Courses WHERE conf_no=?", id) > 0;
    }
}
