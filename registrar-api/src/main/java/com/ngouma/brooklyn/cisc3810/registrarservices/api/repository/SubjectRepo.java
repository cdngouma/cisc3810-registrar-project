package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class SubjectRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public Subject save(Subject s) {
        try {
            return jdbc.queryForObject("CALL EDIT_SUBJECTS(?,?,?)", new Object[]{s.getId(), s.getName(), s.getNameShort()},
                    (rs, numRow) ->
                            new Subject(
                                    rs.getInt("subj_no"),
                                    rs.getString("subj_name"),
                                    rs.getString("subj_abv")
                            )
            );
        }catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public Subject update(Integer id, Subject s) {
        try {
            return jdbc.queryForObject("CALL EDIT_SUBJECTS(?,?,?)", new Object[]{id, s.getName(), s.getNameShort()},
                    (rs, numRow) ->
                            new Subject(
                                    rs.getInt("subj_no"),
                                    rs.getString("subj_name"),
                                    rs.getString("subj_abv")
                            )
            );
        }catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public List<Subject> findAll() {
        return jdbc.query("SELECT * FROM Subjects", (rs, rowNum) ->
                new Subject(
                        rs.getInt("subj_no"),
                        rs.getString("subj_name"),
                        rs.getString("subj_abv")
                )
        );
    }

    public Subject findById(Integer subjectNo) {
        try {
            return jdbc.queryForObject("SELECT * FROM Subjects WHERE subj_no = ?", new Object[]{subjectNo}, (rs, numRow) ->
                    new Subject(
                            rs.getInt("subj_no"),
                            rs.getString("subj_name"),
                            rs.getString("subj_abv")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean deleteById(Integer subjectNo) {
        return jdbc.update("DELETE FROM Subjects WHERE subj_no=?", subjectNo) > 0;
    }
}
