package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SemesterRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Semester> findAll(){
        return jdbc.query("SELECT * FROM Semesters", (rs, numRow)->
                new Semester(
                        rs.getLong("sem_no"),
                        rs.getString("sem_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                )
        );
    }

    public List<Semester> findAllCurrent(){
        return jdbc.query("SELECT * FROM Semesters WHERE start_date >= CURDATE()", (rs, numRow)->
                new Semester(
                        rs.getLong("sem_no"),
                        rs.getString("sem_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                )
        );
    }

    public Semester findById(Long semId){
        return jdbc.queryForObject("SELECT * FROM Semester WHERE sem_no=?", new Object[]{semId}, (rs, numRow)->
                new Semester(
                        rs.getLong("sem_no"),
                        rs.getString("sem_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                )
        );
    }
}
