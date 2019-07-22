package com.ngoum.cisc3810.registrar.registrarserver.repository;

import com.ngoum.cisc3810.registrar.registrarserver.model.Course;
import com.ngoum.cisc3810.registrar.registrarserver.model.CourseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {
    @Autowired
    private JdbcTemplate jdbc;
    //private final Logger LOG = LoggerFactory

    public Course findById(int courseNo){
        String query = "SELECT course_no, dept_abv, course_level, course_name, units\n" +
                "FROM `Courses`\n" +
                "JOIN `AcadDept` ON `Courses`.dept_no=`AcadDept`.dept_no\n" +
                "WHERE course_no=?";

        return jdbc.queryForObject(query, new CourseRowMapper(), courseNo);
    }

    public List<Course> findAllCourses(String courseSubject){
        if(courseSubject != null) courseSubject = "\'" + courseSubject + "\'";

        String query = "CALL get_courses_by_subject(" + courseSubject + ")";

        return jdbc.query(query, new CourseRowMapper());
    }
}
