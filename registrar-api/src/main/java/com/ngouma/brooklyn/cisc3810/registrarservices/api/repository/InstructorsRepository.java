package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InstructorsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Instructor> findAll() {
        try {
            final String QUERY = "SELECT instructors.id, first_name, last_name, subject_name\n" +
                    "FROM instructors JOIN subjects ON subjects.id = instructors.subject_id";
            return jdbcTemplate.query(QUERY, this::constructNewInstructor);
        } catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException();
        }
    }

    public List<Instructor> findAllByDepartment(String dept) {
        try {
            final String QUERY = "SELECT instructors.id, first_name, last_name, subject_name\n" +
                    "FROM instructors JOIN subjects ON subjects.id = instructors.subject_id\n" +
                    "WHERE subjects.subject_name_short = ?";
            return jdbcTemplate.query(QUERY, new Object[]{dept}, this::constructNewInstructor);
        } catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException();
        }
    }

    public Instructor findById(Integer id) {
        try {
            final String QUERY = "SELECT instructors.id, first_name, last_name, subject_name\n" +
                    "FROM instructors JOIN subjects ON subjects.id = instructors.subject_id\n" +
                    "WHERE instructors.id = ?";
            return jdbcTemplate.queryForObject(QUERY, new Object[]{id}, this::constructNewInstructor);
        } catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException();
        }
    }

    private Instructor constructNewInstructor(ResultSet rs, int numRow) throws SQLException {
        return new Instructor(
                rs.getInt("id"),
                rs.getString("subject_name"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
