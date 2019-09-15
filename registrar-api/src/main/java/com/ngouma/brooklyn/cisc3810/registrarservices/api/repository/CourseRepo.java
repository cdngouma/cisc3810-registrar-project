package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course.ConflictingCourse;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course.Prerequisite;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param subjectId
     * @return if subject Id is provided return a list of all course under to the given subject
     * else return a list of all courses
     */
    public List<Course> findAllBySubjectId(Integer subjectId) {
        return jdbc.query("CALL FIND_COURSES_BY_SUBJECT_ID(?)", new Object[]{subjectId}, (rs, numRow) ->
                new Course(
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getShort("course_level"),
                        new Subject(
                                rs.getInt("subject_id"),
                                rs.getString("subject_name"),
                                rs.getString("subject_short")
                        ),
                        rs.getFloat("units"),
                        rs.getByte("numPrereqs"),
                        rs.getByte("numConflicting")
                )
        );
    }

    public Course findCourseById(Integer courseId) {
        try {
            Course course = jdbc.queryForObject("CALL FIND_COURSE_BY_ID(?)", new Object[]{courseId}, (rs, numRow) ->
                    new Course(
                            rs.getInt("id"),
                            rs.getString("course_name"),
                            rs.getShort("course_level"),
                            new Subject(
                                    rs.getInt("subject_id"),
                                    rs.getString("subject_name"),
                                    rs.getString("subject_short")
                            ),
                            rs.getFloat("units"),
                            rs.getString("course_desc")
                    )
            );

            if (course != null) {
                course.setPrerequisites(findPrerequisites(courseId));
                course.setConflictingCourses(findConflictingCourses(courseId));
            }

            return course;

        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException();
        }
    }

    private List<ConflictingCourse> findConflictingCourses(Integer courseId) {
        return jdbc.query("CALL FIND_CONFLICTING_COURSES(?)", new Object[]{courseId}, (rs, numRow) ->
                new ConflictingCourse(
                        rs.getInt("id"),
                        rs.getString("courseName")
                )
        );
    }

    private List<Prerequisite> findPrerequisites(Integer courseId) {
        return jdbc.query("CALL FIND_PREREQUISITES(?)", new Object[]{courseId}, (rs, numRow) ->
                new Prerequisite(
                        rs.getInt("id"),
                        rs.getString("courseName"),
                        rs.getByte("group")
                )
        );
    }
}
