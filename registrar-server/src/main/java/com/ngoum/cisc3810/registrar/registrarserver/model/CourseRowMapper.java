package com.ngoum.cisc3810.registrar.registrarserver.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int numRows) throws SQLException {
        Course course = new Course(
                rs.getInt("course_no"),
                rs.getString("subject_abv"),
                rs.getInt("course_level"),
                rs.getString("course_name"),
                rs.getDouble("units")
        );

        return course;
    }
}
