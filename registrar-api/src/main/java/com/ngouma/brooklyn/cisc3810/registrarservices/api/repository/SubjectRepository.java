package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubjectRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Subject> findAll() {
        final String QUERY = "SELECT id, subject_name, subject_name_short FROM Subjects";
        return jdbcTemplate.query(QUERY, this::constructNewSubject);
    }

    public Subject findById(Integer subjectId) {
        try {
            final String QUERY = "SELECT id, subject_name, subject_name_short FROM Subjects WHERE id = ?";
            return jdbcTemplate.queryForObject(QUERY, new Object[]{subjectId}, this::constructNewSubject);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Subject constructNewSubject(ResultSet rs, int numRow) throws SQLException {
        return new Subject(
                rs.getString("subject_name"),
                rs.getString("subject_name_short")
        );
    }
}
