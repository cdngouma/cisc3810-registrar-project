package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SuppressWarnings("ConstantConditions")
@Repository
public class ClassRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public ClassEntity save(Integer semId, ClassEntity c) {
        String SQL = "INSERT INTO Classes(course_no,instr_name,sem_no,start_time,end_time,`mode`,room,capacity,num_enrolled,opened)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        jdbc.update(SQL, c.getCourseId(), c.getInstructor(), semId, c.getStartTime(), c.getEndTime(),
                c.getMode(), c.getRoom(), c.getCapacity(), c.getNumEnrolledStudents(), c.isOpened());
        /* get last inserted id */
        int classNo = jdbc.queryForObject("SELECT MAX(class_no) AS id FROM Classes", (rs, numRow)->rs.getInt("id"));
        return this.findById(classNo);
    }

    public ClassEntity update(Integer classId, ClassEntity c){
        try {
            String SQL = "UPDATE Classes SET course_no=COALESCE(?,course_no), instr_name=COALESCE(?,instr_name), sem_no=COALESCE(?,sem_no)," +
                    "start_time=COALESCE(?,start_time), end_time=COALESCE(?,end_time), `mode`=COALESCE(?,`mode`), room=COALESCE(?,room)," +
                    "capacity=COALESCE(?,capacity), num_enrolled=COALESCE(?,num_enrolled), opened=COALESCE(?,opened) WHERE class_no=?";

            jdbc.update(SQL, c.getCourseId(), c.getInstructor(), c.getSemesterId(), c.getStartTime(), c.getEndTime(),
                    c.getMode(), c.getRoom(), c.getCapacity(), c.getNumEnrolledStudents(), c.isOpened(), classId);

            return this.findById(classId);
        }catch (EmptyResultDataAccessException ex){
            throw new EntityNotFoundException();
        }
    }

    public List<ClassEntity> findAll(Integer semId, String subjAbv, String range, Integer level, boolean opened) {
        return jdbc.query("CALL FIND_ALL_CLASSES(?,?,?,?,?)", new Object[]{semId, subjAbv, range, level, opened}, (rs, numRow) ->
                new ClassEntity(
                        rs.getInt("K.class_no"),
                        rs.getString("course_code"),
                        rs.getString("C.course_name"),
                        rs.getString("K.instr_name"),
                        rs.getString("semester"),
                        rs.getTime("K.start_time"),
                        rs.getTime("K.end_time"),
                        rs.getString("K.mode"),
                        rs.getString("K.room"),
                        rs.getShort("K.capacity"),
                        rs.getShort("K.num_enrolled"),
                        rs.getBoolean("K.opened")
                )
        );
    }

    public ClassEntity findById(Integer classId) {
        try {
            String SQL = "CALL FIND_CLASS_BY_ID(?)";
            return jdbc.queryForObject(SQL, new Object[]{classId}, (rs, numRow) ->
                    new ClassEntity(
                            rs.getInt("K.class_no"),
                            rs.getString("course_code"),
                            rs.getString("C.course_name"),
                            rs.getString("K.instr_name"),
                            rs.getString("semester"),
                            rs.getTime("K.start_time"),
                            rs.getTime("K.end_time"),
                            rs.getString("K.mode"),
                            rs.getString("K.room"),
                            rs.getShort("K.capacity"),
                            rs.getShort("K.num_enrolled"),
                            rs.getBoolean("K.opened")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }

    public boolean delete(Integer classId) {
        return jdbc.update("DELETE FROM Classes WHERE class_no=?", classId) > 0;
    }
}
