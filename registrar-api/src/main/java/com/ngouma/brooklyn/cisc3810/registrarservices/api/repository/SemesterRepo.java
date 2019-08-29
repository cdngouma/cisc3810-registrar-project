package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class SemesterRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public Semester save(Semester s) {
        try {
            return jdbc.queryForObject("CALL EDIT_SEMESTERS(?,?,?,?)", new Object[]{s.getId(), s.getSemester(), s.getStartDate(), s.getEndDate()},
                    (rs, numRow) -> new Semester(
                            rs.getInt("sem_no"),
                            rs.getString("sem_name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")
                    ));
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Semester update(Integer semId, Semester semester) {
        try {
            return jdbc.queryForObject("CALL EDIT_SEMESTERS(?,?,?,?)", new Object[]{semId, semester.getSemester(), semester.getStartDate(), semester.getEndDate()},
                    (rs, numRow) -> new Semester(
                            rs.getInt("sem_no"),
                            rs.getString("sem_name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")
                    ));
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * @return a list of all semesters
     */
    public List<Semester> findAll() {
        return jdbc.query("SELECT * FROM Semesters", (rs, numRow) ->
                new Semester(
                        rs.getInt("sem_no"),
                        rs.getString("sem_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                )
        );
    }

    /**
     * @return a list of all current semester available during enrollment period (start date > now)
     */
    public List<Semester> findAllCurrent() {
        return jdbc.query("SELECT * FROM Semesters WHERE start_date >= CURDATE()", (rs, numRow) ->
                new Semester(
                        rs.getInt("sem_no"),
                        rs.getString("sem_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                )
        );
    }

    public Semester findById(Long semId) {
        try {
            return jdbc.queryForObject("SELECT * FROM Semesters WHERE sem_no=?", new Object[]{semId}, (rs, numRow) ->
                    new Semester(
                            rs.getInt("sem_no"),
                            rs.getString("sem_name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean deleteById(Long semId) {
        return jdbc.update("DELETE FROM Semesters WHERE sem_no=?", semId) > 0;
    }
}
