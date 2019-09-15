package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClassRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Class> findAll(Integer semesterId, String subject, String levelRange, boolean isOpened) {
        return jdbc.query("CALL FIND_CLASSES(?,?,?,?)", new Object[]{semesterId, subject, levelRange, isOpened}, this::constructNewClass);
    }

    public Class findById(Integer classId) {
        try {
            return jdbc.queryForObject("CALL FIND_CLASS_BY_ID(?)", new Object[]{classId}, this::constructNewClass);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    private Class constructNewClass(ResultSet rs, int numRow) throws SQLException {
        return new Class(
                rs.getInt("id"),
                rs.getString("course_name"),
                rs.getString("course_code"),
                rs.getString("instructor_name"),
                rs.getString("semester"),
                rs.getDate("start_date"),
                rs.getDate("end_date"),
                null,
                rs.getTime("start_time"),
                rs.getTime("end_time"),
                rs.getString("class_mode"),
                rs.getString("room"),
                rs.getShort("capacity"),
                rs.getShort("num_enrolled"),
                rs.getBoolean("isopened")
        );
    }
}
