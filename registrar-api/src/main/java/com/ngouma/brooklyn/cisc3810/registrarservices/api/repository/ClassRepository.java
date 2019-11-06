package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class ClassRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClassEntity> findAll(String semester, String subject, String levelRange, boolean isOpened) {
        final String QUERY = buildFindAllFilteredQuery(semester, subject, levelRange, isOpened);

        return jdbcTemplate.query(QUERY, new Object[]{semester, subject, levelRange, isOpened}, (rs, numRow) ->
                new ClassEntity(
                        rs.getInt("id"),
                        new Course(
                                rs.getInt("course_id"),
                                rs.getString("course_name"),
                                new Subject(
                                        rs.getString("subject_name"),
                                        rs.getString("subject_name_short")
                                ),
                                rs.getShort("course_level"),
                                rs.getFloat("units")
                        ),
                        rs.getString("instructor_name"),
                        rs.getString("semester_full_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("class_mode"),
                        rs.getString("room"),
                        rs.getShort("capacity"),
                        rs.getShort("num_enrolled"),
                        rs.getString("class_status")
                )
        );
    }

    private String buildFindAllFilteredQuery(String semester, final String SUBJECT, String levelRange, boolean isOpened) {
        final String BASE_QUERY = "select classes.id, classes.course_id, courses.course_name, courses.course_level, subjects.subject_name, subjects.subject_name_short," +
                "CONCAT(instructors.first_name,' ',last_name) AS instructor_name," +
                "CONCAT(semesters.sem_name,' ',YEAR(semesters.start_date)), semesters.start_date, semesters.end_date," +
                "classes.start_time, classes.end_time, CONCAT(classes.building,' ',classes.room) AS class_room, classes.capacity, classes.num_enrolled, classes.class_mode," +
                "classes.class_status from classes\n" +
                "join courses on courses.id = classes.course_id\n" +
                "join subjects on subjects.id = courses.subject_id\n" +
                "join semesters on semesters.id = classes.sem_id\n" +
                "join instructors on instructors.id = classes.instructor_id";

        StringBuilder finalQuery = new StringBuilder(BASE_QUERY);
        // filter by semester (required)
        final String SEMESTER = semester.substring(0,semester.length()-2);
        final int YEAR = Integer.parseInt(semester.substring(semester.length()-2, semester.length()));
        finalQuery.append("\nwhere semesters.sem_name = ").append(SEMESTER).append(" and YEAR(semesters.start_date) = ").append(YEAR);
        // filter by subject
        if (SUBJECT != null) {
            finalQuery.append("\nand courses.subject_id = (select id from subjects where subject_name_short = UPPER(").append(SUBJECT).append("))");
        }
        // filter by level
        if (levelRange != null) {
            final String REL = levelRange.substring(0,2);
            final String LEVEL = levelRange.substring(3,6);
            finalQuery.append("\nand courses.course_level ").append(REL).append(LEVEL);
        }
        // filter by status (OPENED only)
        if (isOpened) {
            finalQuery.append("\nand classes.class_status = OPENED");
        }

        finalQuery.append("\norder by courses.course_level, subjects.subject_name_short");

        return finalQuery.toString();
    }

    public ClassEntity findById(Integer classId) {
        try {
            final String QUERY = "";
            CourseRepository courseRepository = new CourseRepository();
            return jdbcTemplate.queryForObject(QUERY, new Object[]{classId}, (rs, numRow) ->
                    new ClassEntity(
                            rs.getInt("id"),
                            courseRepository.findCourseById(rs.getInt("course_id")),
                            rs.getString("instructor_name"),
                            rs.getString("semester_full_name"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getTime("start_time"),
                            rs.getTime("end_time"),
                            rs.getString("class_mode"),
                            rs.getString("room"),
                            rs.getShort("capacity"),
                            rs.getShort("num_enrolled"),
                            rs.getString("class_status")
                    )
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException();
        }
    }
}
