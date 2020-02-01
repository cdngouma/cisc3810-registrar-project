package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorRepository extends JpaRepository <Instructor, Long> {
    @Query(value = "SELECT * FROM instructors i JOIN subjects s ON i.subject_id = s.id " +
            "WHERE (s.subject_code = UPPER(?1) OR ?1 IS NULL) AND (i.last_name LIKE (?2) OR ?2 IS NULL)", nativeQuery = true)
    List<Instructor> findAll(String subjectCode, String lastName);
}
