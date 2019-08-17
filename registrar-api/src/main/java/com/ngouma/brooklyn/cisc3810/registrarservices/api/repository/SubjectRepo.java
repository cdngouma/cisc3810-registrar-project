package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public Subject save(Subject subject) {
        if(jdbc.update("INSERT INTO Subjects(subj_name, subj_abv) VALUES(?,?)", subject.getName(), subject.getNameShort()) > 0){
            // find last inserted row
            return jdbc.queryForObject("SELECT * FROM Subjects WHERE subj_no=(SELECT MAX(subj_no) FROM Subjects)", (rs, rowNum)->
                    new Subject(
                            rs.getLong("subj_no"),
                            rs.getString("subj_name"),
                            rs.getString("subj_abv")
                    )
            );
        }else {
            throw new EmptyResultDataAccessException(1);
        }
    }

    public int update(Subject subject) throws DataAccessException {
        String SQL = "UPDATE Subjects SET subj_name=COALESCE(?,subj_name), subj_abv=COALESCE(?,subj_abv) WHERE subj_no=?";
        return jdbc.update(SQL, subject.getName(), subject.getNameShort(), subject.getId());
    }

    public List<Subject> findAll() {
        return jdbc.query("SELECT * FROM Subjects", (rs, rowNum) ->
                new Subject(
                        rs.getLong("subj_no"),
                        rs.getString("subj_name"),
                        rs.getString("subj_abv")
                )
        );
    }

    public Subject findById(Long subjectNo) {
        return jdbc.queryForObject("SELECT * FROM Subjects WHERE subj_no = ?", new Object[]{subjectNo}, (rs, numRow) ->
                new Subject(
                        rs.getLong("subj_no"),
                        rs.getString("subj_name"),
                        rs.getString("subj_abv")
                )
        );
    }

    public void deleteById(Long subjectNo) {
        jdbc.update("DELETE FROM Subjects WHERE subj_no=?", subjectNo);
    }

    public void deleteAll() {
        jdbc.update("DELETE * FROM Subjects WHERE subj_no IS NOT NULL");
    }
}
