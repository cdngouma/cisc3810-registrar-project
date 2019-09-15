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
public class SubjectRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Subject> findAll() {
        return jdbc.query("SELECT id, subject_name, subject_short FROM Subjects", this::constructNewSubject);
    }

    public Subject findById(Integer subjectNo) {
        try {
            return jdbc.queryForObject("SELECT id, subject_name, subject_short FROM Subjects WHERE id = ?", new Object[]{subjectNo}, this::constructNewSubject);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Subject constructNewSubject(ResultSet rs, int numRow) throws SQLException {
        return new Subject(
                rs.getInt("id"),
                rs.getString("subject_name"),
                rs.getString("subject_short")

        );
    }
}
