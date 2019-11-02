package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class CourseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Course> findAllBySubject(String subject) {
        final String QUERY = "SELECT courses.id, course_name, course_level , courses.subject_id, subject_name, subject_name_short, units,\n" +
                "(select COUNT(id) from prerequisites where course_id = courses.id) AS numPrereqs,\n" +
                "(select COUNT(id) from conflicting_courses where course_id = courses.id) AS numConflicting\n" +
                "FROM courses JOIN Subjects ON subjects.id = courses.subject_id\n" +
                "LEFT JOIN prerequisites ON prerequisites.course_id = courses.id " +
                "LEFT JOIN conflicting_courses ON conflicting_courses.course_id = courses.id\n" +
                "WHERE subjects.subject_name_short = ?";
        return jdbcTemplate.query(QUERY, new Object[]{subject}, (rs, numRow) ->
                new Course(
                        rs.getInt("id"),
                        new Subject(
                                rs.getString("subject_name"),
                                rs.getString("subject_name_short")
                        ),
                        rs.getShort("course_level"),
                        rs.getString("course_name"),
                        rs.getFloat("units"),
                        rs.getByte("numPrereqs"),
                        rs.getByte("numConflicting")
                )
        );
    }

    public Course findCourseById(Integer id) {
        try {
            final String QUERY = "SELECT C.id, C.course_name, C.course_level, C.subject_id, S.subject_name, S.subject_name_short, C.units, C.course_desc\n" +
                    "FROM Courses C JOIN Subjects S ON S.id = C.subject_id WHERE C.id = ?;";
            Course course = jdbcTemplate.queryForObject(QUERY, new Object[]{id}, (rs, numRow) ->
                    new Course(
                            rs.getInt("id"),
                            new Subject(
                                    rs.getString("subject_name"),
                                    rs.getString("subject_name_short")
                            ),
                            rs.getShort("course_level"),
                            rs.getString("course_name"),
                            rs.getFloat("units"),
                            rs.getString("course_desc")
                    )
            );
            if (course != null) {
                course.queryForPrerequisites(jdbcTemplate);
                course.queryForConflictingCourses(jdbcTemplate);
            }
            return course;
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException();
        }
    }
}
