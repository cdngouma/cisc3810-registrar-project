package cisc3810.ngoum.registrarservices.api.repository;

import cisc3810.ngoum.registrarservices.api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository <Student, Long> {
    @Query(value = "SELECT * FROM students s WHERE (?1 IS NULL OR s.last_name LIKE ?1) AND (?2 IS NULL OR s.major_id = ?2);", nativeQuery = true)
    List<Student> findAll(String lastName, Integer majorId);
}
