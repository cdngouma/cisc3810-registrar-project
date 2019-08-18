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

    /**
     * @param c (course)
     * @return the newly created course
     */
    public Course save(Course c) {
        String SQL = "INSERT INTO Courses(subj_no,course_level,course_name,units,course_desc) VALUE(?,?,?,?,?)";
        jdbc.update(SQL, c.getSubjectId(), c.getLevel(), c.getName(), c.getUnits(), c.getDescription());
        /* get last added row */
        return jdbc.queryForObject("SELECT * FROM Courses WHERE course_no=(SELECT MAX(course_no) FROM Courses)", (rs, numRow) ->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc")
                )
        );
    }

    /**
     * @param courseId
     * @param c (course)
     * @return the updated course
     */
    public Course updateCourse(Long courseId, Course c) {
        try {
            /* update course */
            String UPDATE_COURSE = "UPDATE Courses SET subj_no=COALESCE(?,subj_no), course_level=COALESCE(?,course_level)," +
                    "course_name=COALESCE(?,course_name), units=COALESCE(?,units), course_desc=COALESCE(?,course_desc) WHERE course_no=?";
            jdbc.update(UPDATE_COURSE, c.getSubjectId(), c.getLevel(), c.getName(), c.getUnits(), c.getDescription(), c.getId());
            /* retrun object */
            return findCourseById(courseId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * @param subjId
     * @return a list of all course under to the given subject
     */
    public List<Course> findAllCourses(Long subjId) {
        String SQL_GET_COURSES = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf' " +
                "FROM Courses C LEFT JOIN Prerequisites P ON C.course_no=P.course_no LEFT JOIN Conflicting_Courses K " +
                "ON C.course_no=K.course_no WHERE C.subj_no=? GROUP BY C.course_no";
        return jdbc.query(SQL_GET_COURSES, new Object[]{subjId}, (rs, numRow) ->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc"),
                        rs.getInt("numPrereq"),
                        rs.getInt("numConf")
                )
        );
    }

    /**
     * @return all courses
     */
    public List<Course> findAllCourses() {
        String SQL_GET_COURSES = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf' " +
                "FROM Courses C LEFT JOIN Prerequisites P ON C.course_no=P.course_no LEFT JOIN Conflicting_Courses K " +
                "ON C.course_no=K.course_no GROUP BY C.course_no";
        return jdbc.query(SQL_GET_COURSES, (rs, numRow) ->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc"),
                        rs.getInt("numPrereq"),
                        rs.getInt("numConf")
                )
        );
    }

    public Course findCourseById(Long courseId) {
        String SQL_GET_COURSES = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'\n" +
                "FROM Courses C LEFT JOIN Prerequisites P ON C.course_no=P.course_no LEFT JOIN Conflicting_Courses K\n" +
                "ON C.course_no=K.course_no WHERE C.course_no=? GROUP BY C.course_no;";
        try {
            Course course = jdbc.queryForObject(SQL_GET_COURSES, new Object[]{courseId}, (rs, numRow) ->
                    new Course(
                            rs.getLong("course_no"),
                            rs.getLong("subj_no"),
                            rs.getInt("course_level"),
                            rs.getString("course_name"),
                            rs.getDouble("units"),
                            rs.getString("course_desc"),
                            rs.getInt("numPrereq"),
                            rs.getInt("numConf")
                    )
            );

            if (course != null) {
                course.setNumPrerequisites(findNumPrerequisites(course.getId()));
                course.setNumConflictingCourses(findNumConflictingCourses(course.getId()));
            }
            return course;
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean deleteCourseById(Long courseId) {
        return jdbc.update("DELETE FROM Courses WHERE course_no=?", courseId) > 0;
    }

    // PREREQUISITES AND CONFLICTING COURSES

    /**
     * @param courseId
     * @return all the given course prerequisites
     */
    public List<CourseWrapper> findAllPrereq(Long courseId) {
        String SQL_GET_PREREQ = "SELECT P.prereq_no, P.sec_course_no AS courseId, CONCAT(S.subj_abv,' ',C.course_level) AS courseShort, P.prereq_group\n" +
                "FROM Prerequisites P JOIN Courses C ON P.sec_course_no = C.course_no JOIN Subjects S ON C.subj_no = S.subj_no WHERE P.course_no=?";
        return jdbc.query(SQL_GET_PREREQ, new Object[]{courseId}, (rs, numRow) ->
                new CourseWrapper(
                        rs.getLong("P.prereq_no"),
                        rs.getLong("courseId"),
                        rs.getString("courseShort"),
                        rs.getInt("P.prereq_group")
                )
        );
    }

    /**
     * @param courseId
     * @return all courses conflicting with the given course
     */
    public List<CourseWrapper> findAllConflicting(Long courseId) {
        String SQL_GET_CONFLICTING = "SELECT K.conf_no, K.sec_course_no AS courseId, CONCAT(S.subj_abv,' ',C.course_level) AS courseShort\n" +
                "FROM Conflicting_Courses K JOIN Courses C ON K.sec_course_no = C.course_no JOIN Subjects S ON C.subj_no = S.subj_no WHERE K.course_no=?";
        return jdbc.query(SQL_GET_CONFLICTING, new Object[]{courseId}, (rs, numRow) ->
                new CourseWrapper(
                        rs.getLong("K.conf_no"),
                        rs.getLong("courseId"),
                        rs.getString("courseShort")
                )
        );
    }

    /**
     * add prerequisite to a course
     * @param courseId
     * @param prereq
     * @return updated course
     */
    public Course addPrerequisite(Long courseId, CourseWrapper prereq) {
        try {
            /* update prereq */
            jdbc.update("INSERT INTO Prerequisites(course_no,sec_course_no,prereq_group) VALUES(?,?,?)", courseId, prereq.getCourseNo(), prereq.getGroup());
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
    public Course addConflictingCourse(Long courseId, CourseWrapper confCourse) {
        try {
            /* update conflicting course */
            jdbc.update("INSERT INTO Conflicting_Course(course_no,sec_course_no) VALUES(?,?)", courseId, confCourse.getCourseNo());
            /* return updated course */
            return this.findCourseById(courseId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private int findNumPrerequisites(Long courseId) {
        return jdbc.queryForObject("SELECT COUNT(*) AS numPrereq FROM Prerequisites WHERE course_no=?",
                new Object[]{courseId}, (rs, numRow) -> rs.getInt("numPrereq")
        );
    }

    private int findNumConflictingCourses(Long courseId) {
        return jdbc.queryForObject("SELECT COUNT(*) AS numConf FROM Conflicting_Courses WHERE course_no=?",
                new Object[]{courseId}, (rs, numRow) -> rs.getInt("numConf")
        );
    }

    public boolean deletePrereqById(Long id) {
        return jdbc.update("DELETE FROM Prerequisites WHERE prereq_no=?", id) > 0;
    }

    public boolean deleteConflictingById(Long id) {
        return jdbc.update("DELETE FROM Conflicting_Courses WHERE conf_no=?", id) > 0;
    }
}
