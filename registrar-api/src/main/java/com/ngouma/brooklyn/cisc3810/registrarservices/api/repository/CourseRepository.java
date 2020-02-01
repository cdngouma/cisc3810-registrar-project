package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository <Course, Integer> {
    @Query(value = "SELECT * FROM courses c JOIN subjects s on c.subject_id = s.id WHERE s.subject_code = UPPER(?1)", nativeQuery = true)
    List<Course> findAllBySubject(String subjectCode);

    @Query(value = "select 1\n" +
            "from courses C1, courses C2\n" +
            "where C1.id = ?1 and C2.course_code = ?2 and C1.id <> C2.id\n" +
            // check for duplicates
            "and (C1.prerequisites IS NULL OR C1.prerequisites NOT LIKE(CONCAT('%', C2.id, '%')))\n" +
            // check if course is not prerequisite of prerequisite
            "and (C2.prerequisites IS NULL OR C2.prerequisites NOT LIKE(CONCAT('%', C1.id, '%')))\n" +
            // make sure prerequisite level is lower than course level
            "and (C1.subject_id = C2.subject_id and C1.course_level > C2.course_level or C1.subject_id <> C2.subject_id);",
            nativeQuery = true)
    String validateCoursePrerequisite(int courseId, String prereqId);
}
