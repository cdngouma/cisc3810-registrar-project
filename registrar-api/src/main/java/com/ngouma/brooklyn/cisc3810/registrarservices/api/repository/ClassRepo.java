package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class ClassRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<ClassEntity> findAll(Long semId, String subjAbv, String range, Integer level, boolean opened) {
        String SQL = "SELECT K.class_no, K.course_no, CONCAT(S.subj_abv,' ',C.course_level,' - ',C.course_name) AS courseName, K.instr_name,\n" +
                "CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS sem, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened, K.start_time, K.end_time\n" +
                "FROM Classes K JOIN Courses C on C.course_no=K.course_no JOIN Subjects S on S.subj_no=C.subj_no JOIN Semesters Z on Z.sem_no=K.sem_no\n" +
                "WHERE K.sem_no=?";

        if(level != null  && range != null && level > 0){
            if(range.compareToIgnoreCase("GREATER") == 0) SQL = SQL + " AND C.course_level >= " + level;
            else if(range.compareToIgnoreCase("LESS") == 0) SQL = SQL + " ND C.course_level <= " + level;
        }else if(level != null && level != 0){
            SQL = SQL + " AND C.course_level = " + level;
        }

        if(subjAbv != null){
            SQL = SQL + " AND C.subj_no = (SELECT subj_no FROM Subjects WHERE subj_abv = " + subjAbv;
        }

        if(opened){
            SQL = SQL + " AND K.opened = TRUE";
        }

        System.err.println(SQL);

        return jdbc.query(SQL, new Object[]{semId}, (rs, numRow) ->
                new ClassEntity(
                        rs.getLong("K.class_no"),
                        new Course(
                                rs.getLong("K.course_no"),
                                rs.getString("courseName")
                        ),
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

    public ClassEntity findById(Long classId) {
        try {
            String SQL = "SELECT K.class_no, K.course_no, CONCAT(S.subj_abv,' ',C.course_level,' - ',C.course_name) AS courseName, K.instr_name,\n" +
                    "CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS sem, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened, K.start_time, K.end_time\n" +
                    "FROM Classes K JOIN Courses C on C.course_no=K.course_no JOIN Subjects S on S.subj_no=C.subj_no JOIN Semesters Z on Z.sem_no=K.sem_no\n" +
                    "WHERE K.class_no=?";
            return jdbc.queryForObject(SQL, new Object[]{classId}, (rs, numRow) ->
                    new ClassEntity(
                            rs.getLong("K.class_no"),
                            new Course(
                                    rs.getLong("K.course_no"),
                                    rs.getString("courseName")
                            ),
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
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }
}
