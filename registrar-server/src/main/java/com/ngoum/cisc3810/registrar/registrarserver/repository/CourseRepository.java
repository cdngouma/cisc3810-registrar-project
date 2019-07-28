package com.ngoum.cisc3810.registrar.registrarserver.repository;

import com.ngoum.cisc3810.registrar.registrarserver.model.Course;
import com.ngoum.cisc3810.registrar.registrarserver.model.CourseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public Course findCourseById(int courseNo){
        Course course = jdbc.queryForObject("CALL find_course_by_id(?)", new CourseRowMapper(), courseNo);
        return course;
    }

    public List<Course> findAllCoursesBySubject(String courseSubject){
        return jdbc.query("CALL find_course_by_subject(?)", new CourseRowMapper(), courseSubject);
    }

    public boolean deleteCourseById(int courseNo){
        jdbc.update("DELETE FROM Courses WHERE course_no = ?", courseNo);
        return true;
    }
}