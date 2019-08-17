package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Class;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Class> findAll(Long semesterId){
        // K = class, C = course, S = subject, Z = semester
        String SQL = "SELECT K.class_no, K.course_no, CONCAT(S.subj_abv,' ',C.course_level,' - ',C.course_name) AS courseName,\n" +
                "K.instr_name, CONCAT(Z.sem_name,\' \',YEAR(Z.start_date)) AS sem, K.room, K.capacity,\n" +
                "K.num_enrolled, K.`mode`, K.opened, K.start_time, K.end_time\n" +
                "FROM Classes K\n" +
                "JOIN Courses C on C.course_no=K.course_no\n" +
                "JOIN Subjects S on S.subj_no=C.subj_no\n" +
                "JOIN Semesters Z on Z.sem_no=K.sem_no\n" +
                "WHERE K.sem_no=?";

        return jdbc.query(SQL, new Object[]{semesterId}, (rs, numRow)->
                new Class(
                        rs.getLong("K.class_no"),
                        null,
                        rs.getString("courseName"),
                        rs.getString("K.instr_name"),
                        rs.getString("sem"),
                        rs.getTime("K.start_time"),
                        rs.getTime("K.end_time"),
                        rs.getString("K.mode"),
                        rs.getString("K.room"),
                        rs.getInt("K.capacity"),
                        rs.getInt("K.num_enrolled"),
                        rs.getBoolean("K.opened")
                )
        );
    }
}
