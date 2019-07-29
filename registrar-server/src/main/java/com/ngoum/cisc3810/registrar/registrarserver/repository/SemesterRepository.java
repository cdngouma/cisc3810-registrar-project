package com.ngoum.cisc3810.registrar.registrarserver.repository;

import com.ngoum.cisc3810.registrar.registrarserver.model.Semester;
import com.ngoum.cisc3810.registrar.registrarserver.model.mapper.SemesterRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SemesterRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Semester> findActiveEnrollmentPeriods() {
        return jdbc.query("CALL get_current_enrollment_periods()", new SemesterRowMapper());
    }
}
