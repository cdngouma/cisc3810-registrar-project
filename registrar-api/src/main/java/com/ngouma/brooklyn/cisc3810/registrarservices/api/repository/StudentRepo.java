package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class StudentRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Student> findAll() {
        try {
            return jdbc.query("SELECT * FROM Students", (rs, numRow) ->
                    new Student(
                            rs.getInt("student_no"),
                            rs.getString("email_address"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getString("degree"),
                            rs.getString("major"),
                            rs.getString("division"),
                            rs.getDouble("gpa")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Student findById(Integer studentId) {
        try {
            return jdbc.queryForObject("SELECT * FROM Students WHERE student_no=?", new Object[]{studentId}, (rs, numRow) ->
                    new Student(
                            rs.getInt("student_no"),
                            rs.getString("email_address"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getString("degree"),
                            rs.getString("major"),
                            rs.getString("division"),
                            rs.getDouble("gpa")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Student save(Student s) {
        try {
            return jdbc.queryForObject("CALL EDIT_STUDENTS(?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{s.getId(), s.getEmail(), s.getFirstName(), s.getLastName(), s.getGender(),
                            s.getDateOfBith(), s.getDegree(), s.getMajor(), s.getDivision(), s.getGpa()},
                    (rs, numRow) -> new Student(
                            rs.getInt("student_no"),
                            rs.getString("email_address"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getString("degree"),
                            rs.getString("major"),
                            rs.getString("division"),
                            rs.getDouble("gpa")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Student update(Integer studentId, Student s) {
        try {
            return jdbc.queryForObject("CALL EDIT_STUDENTS(?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{studentId, s.getEmail(), s.getFirstName(), s.getLastName(), s.getGender(),
                            s.getDateOfBith(), s.getDegree(), s.getMajor(), s.getDivision(), s.getGpa()},
                    (rs, numRow) -> new Student(
                            rs.getInt("student_no"),
                            rs.getString("email_address"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getString("degree"),
                            rs.getString("major"),
                            rs.getString("division"),
                            rs.getDouble("gpa")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean delete(Integer studentId){
        return jdbc.update("DELETE FROM Students WHERE student_no=?", studentId) > 0;
    }
}