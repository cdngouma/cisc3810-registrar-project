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

    /**
     * @param subject
     * @return the newly added subject
     */
    public Subject save(Subject subject) {
        jdbc.update("INSERT INTO Subjects(subj_name, subj_abv) VALUES(?,?)", subject.getName(), subject.getNameShort());
        // find last inserted row
        return jdbc.queryForObject("SELECT * FROM Subjects WHERE subj_no=(SELECT MAX(subj_no) FROM Subjects)", (rs, rowNum) ->
                new Subject(
                        rs.getInt("subj_no"),
                        rs.getString("subj_name"),
                        rs.getString("subj_abv")
                )
        );
    }

    /**
     *
     * @param subjectId
     * @param subject
     * @return the updated subject
     */
    public Subject update(Integer subjectId, Subject subject) {
        try {
            String SQL = "UPDATE Subjects SET subj_name=COALESCE(?,subj_name), subj_abv=COALESCE(?,subj_abv) WHERE subj_no=?";
            jdbc.update(SQL, subject.getName(), subject.getNameShort(), subjectId);
            return this.findById(subjectId);
        } catch (EmptyResultDataAccessException ex) {
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
