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
public class SemesterRepo {
    @Autowired
    private JdbcTemplate jdbc;

    /**
     * @return if current == true: a list of all semester available for registration
     *          else return a list of all semesters
     */
    public List<Semester> findAll(boolean current) {
        if(current) return jdbc.query("SELECT id, semester, start_date, end_date FROM Semesters WHERE start_date >= CURDATE()", this::constructNewSubject);
        return jdbc.query("SELECT * FROM Semesters", this::constructNewSubject);
    }

    public Semester findById(Integer semesterId) {
        try {
            return jdbc.queryForObject("SELECT id, semester, start_date, end_date FROM Semesters WHERE id=?", new Object[]{semesterId}, this::constructNewSubject);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Semester constructNewSubject(ResultSet rs, int numRow) throws SQLException {
        return new Semester(
                rs.getInt("id"),
                rs.getString("semester"),
                rs.getDate("start_date"),
                rs.getDate("end_date")
        );
    }
}
