package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepo {
    @Autowired
    private JdbcTemplate jdbc;

    public Course save(Course c){
        String SQL = "INSERT INTO Courses(subj_no,course_level,course_name,units,course_desc) " +
                "VALUE(?,?,?,?,?)";
        if(jdbc.update(SQL, c.getSubjectId(), c.getLevel(), c.getName(), c. getUnits(), c.getDescription()) > 0){
            return jdbc.queryForObject("SELECT * FROM Courses WHERE course_no=(SELECT MAX(course_no) FROM Courses)", (rs, numRow)->
                    new Course(
                            rs.getLong("course_no"),
                            rs.getLong("subj_no"),
                            rs.getInt("course_level"),
                            rs.getString("course_name"),
                            rs.getDouble("units"),
                            rs.getString("course_desc"),
                            rs.getInt("numPrereq"),
                            rs.getInt("numConf")
                    )
            );
        }else {
            throw new EmptyResultDataAccessException(1);
        }
    }

    public void update(Course c) {
        String SQL = "UPDATE Courses SET subj_no=COALESCE(?,subj_no), course_level=COALESCE(?,course_level)," +
                "course_name=COALESCE(?,course_name), units=COALESCE(?,units), course_desc=COALESCE(?,course_desc)";
        jdbc.update(SQL, c);
    }

    public List<Course> findAll(Long subjId) {
        String SQL = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'\n" +
                "FROM Courses C \n" +
                "LEFT JOIN Prerequisits P ON C.course_no=P.course_no\n" +
                "LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no\n" +
                "WHERE subj_no = ? GROUP BY C.course_no";

        return jdbc.query(SQL, new Object[]{subjId}, (rs, numRow)->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc"),
                        rs.getInt("numPrereq"),
                        rs.getInt("numConf")
                )
        );
    }

    public List<Course> findAll() throws DataAccessException{
        String SQL = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'\n" +
                "FROM Courses C \n" +
                "LEFT JOIN Prerequisits P ON C.course_no=P.course_no\n" +
                "LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no\n" +
                "GROUP BY C.course_no";

        return jdbc.query(SQL,(rs, numRow)->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc"),
                        rs.getInt("numPrereq"),
                        rs.getInt("numConf")
                )
        );
    }

    public Course findById(Long courseId) throws DataAccessException{
        String SQL = "SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'\n" +
                "FROM Courses C \n" +
                "LEFT JOIN Prerequisits P ON C.course_no=P.course_no\n" +
                "LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no\n" +
                "WHERE C.course_no=? GROUP BY C.course_no";

        Course course = jdbc.queryForObject(SQL, new Object[]{courseId}, (rs, numRow)->
                new Course(
                        rs.getLong("course_no"),
                        rs.getLong("subj_no"),
                        rs.getInt("course_level"),
                        rs.getString("course_name"),
                        rs.getDouble("units"),
                        rs.getString("course_desc"),
                        rs.getInt("numPrereq"),
                        rs.getInt("numConf")
                )
        );

        if(course != null) {
            course.setNumPrerequisites(findNumPrereq(course.getId()));
            course.setNumConflictingCourses(findNumConfCourse(course.getId()));
        }

        return course;
    }

    public void deleteById(Long courseId) {
        jdbc.update("DELETE FROM Courses WHERE course_no=?", courseId);
    }

    private int findNumPrereq(Long courseId) throws DataAccessException{
        return jdbc.queryForObject("SELECT COUNT(*) AS numPrereq FROM Prerequisits WHERE course_no=?",
                new Object[]{courseId}, (rs, numRow) ->
                        rs.getInt("numPrereq")
        );
    }

    private int findNumConfCourse(Long courseId) throws DataAccessException{
        return jdbc.queryForObject("SELECT COUNT(*) AS numConf FROM Conflicting_Courses WHERE course_no=?",
                new Object[]{courseId}, (rs, numRow) ->
                        rs.getInt("numConf")
        );
    }
}
