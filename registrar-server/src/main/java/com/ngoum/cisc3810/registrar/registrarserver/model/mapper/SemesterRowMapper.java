package com.ngoum.cisc3810.registrar.registrarserver.model.mapper;

import com.ngoum.cisc3810.registrar.registrarserver.model.Semester;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SemesterRowMapper implements RowMapper<Semester> {
    @Override
    public Semester mapRow(ResultSet rs, int numRows) throws SQLException {
        Semester semester = new Semester(
                rs.getInt("period_no"),
                rs.getString("semester"),
                rs.getDate("start_date"),
                rs.getDate("end_date")
        );

        semester.setYear(rs.getInt("sem_year"));
        return semester;
    }
}
