package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SemesterRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Semester> findAll(boolean current) {
        final String QUERY = "SELECT id, sem_name, start_date, end_date FROM semesters WHERE start_date >= CURDATE()";
        if(current) {
            return jdbcTemplate.query(QUERY, this::constructNewSubject);
        }
        return jdbcTemplate.query("SELECT id, sem_name, start_date, end_date FROM Semesters", this::constructNewSubject);
    }

    public Semester findById(Integer semesterId) {
        try {
            final String QUERY = "SELECT id, sem_name, start_date, end_date FROM semesters WHERE id = ?";
            return jdbcTemplate.queryForObject(QUERY, new Object[]{semesterId}, this::constructNewSubject);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Semester constructNewSubject(ResultSet rs, int numRow) throws SQLException {
        return new Semester(
                rs.getInt("id"),
                rs.getString("sem_name"),
                rs.getDate("start_date"),
                rs.getDate("end_date")
        );
    }
}
