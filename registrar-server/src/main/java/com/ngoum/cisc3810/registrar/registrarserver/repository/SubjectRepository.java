package com.ngoum.cisc3810.registrar.registrarserver.repository;

import com.ngoum.cisc3810.registrar.registrarserver.model.CourseSubject;
import com.ngoum.cisc3810.registrar.registrarserver.model.mapper.SubjectRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public CourseSubject findCourseSubjectById(int deptNo){
        String query = "SELECT * FROM CourseSubjects WHERE subject_no = ?";
        return jdbc.queryForObject(query, new SubjectRowMapper(), deptNo);
    }

    public List<CourseSubject> findAllSubjects(){
        String query = "SELECT * FROM CourseSubjects";
        List<CourseSubject> courseSubjects = new ArrayList<>(jdbc.query(query, new SubjectRowMapper()));
        System.out.println(courseSubjects.toString());
        return courseSubjects;
    }
}
