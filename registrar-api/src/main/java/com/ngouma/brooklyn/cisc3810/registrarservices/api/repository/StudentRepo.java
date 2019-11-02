package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Student> findAll() {
        try {
            return jdbc.query("SELECT student_no, email_address, last_name, gender, dob, degree, major, division, gpa\n" +
                    "FROM Students", this::constructNewStudent);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Student findById(Integer studentId) {
        try {
            return jdbc.queryForObject("SELECT student_no, email_address, last_name, gender, dob, degree, major, division, gpa\n" +
                    "FROM Students WHERE id = ?", new Object[]{studentId}, this::constructNewStudent);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Student constructNewStudent(ResultSet rs, int numRow) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("email_address"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"),
                rs.getDate("dob"),
                rs.getString("degree"),
                rs.getString("major"),
                rs.getString("division"),
                rs.getDouble("gpa")
        );
    }
}