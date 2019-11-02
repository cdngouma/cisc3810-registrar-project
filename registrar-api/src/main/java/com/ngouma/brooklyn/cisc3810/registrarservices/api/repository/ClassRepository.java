package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class ClassRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClassEntity> findAll(String semester, String subject, String levelRange, boolean isOpened) {
        final String QUERY = "";
        return jdbcTemplate.query(QUERY, new Object[]{semester, subject, levelRange, isOpened}, (rs, numRow) ->
                new ClassEntity(
                        rs.getInt("id"),
                        new Course(
                                rs.getInt("course_id"),
                                rs.getString("course_name"),
                                new Subject(
                                        rs.getString("subject_name"),
                                        rs.getString("subject_name_short")
                                ),
                                rs.getShort("course_level"),
                                rs.getFloat("units")
                        ),
                        rs.getString("instructor_name"),
                        rs.getString("semester_full_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("class_mode"),
                        rs.getString("room"),
                        rs.getShort("capacity"),
                        rs.getShort("num_enrolled"),
                        rs.getString("class_status")
                )
        );
    }

    public ClassEntity findById(Integer classId) {
        try {
            final String QUERY = "";
            CourseRepository courseRepository = new CourseRepository();
            return jdbcTemplate.queryForObject(QUERY, new Object[]{classId}, (rs, numRow) ->
                    new ClassEntity(
                            rs.getInt("id"),
                            courseRepository.findCourseById(rs.getInt("course_id")),
                            rs.getString("instructor_name"),
                            rs.getString("semester_full_name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getTime("start_time"),
                            rs.getTime("end_time"),
                            rs.getString("class_mode"),
                            rs.getString("room"),
                            rs.getShort("capacity"),
                            rs.getShort("num_enrolled"),
                            rs.getString("class_status")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }
}
