package com.ngoum.cisc3810.registrar.registrarserver.model.mapper;

import com.ngoum.cisc3810.registrar.registrarserver.model.CourseSubject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRowMapper implements RowMapper<CourseSubject> {
    @Override
    public CourseSubject mapRow(ResultSet rs, int numRows) throws SQLException {
        CourseSubject courseSubject = new CourseSubject( rs.getInt("subject_no"),
                rs.getString("subject_abv"),
                rs.getString("subject_name")
        );

        System.out.println("----------" + courseSubject.toString());

        return courseSubject;
    }
}
