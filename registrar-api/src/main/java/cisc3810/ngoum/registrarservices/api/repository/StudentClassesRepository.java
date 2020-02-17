package cisc3810.ngoum.registrarservices.api.repository;

import cisc3810.ngoum.registrarservices.api.model.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface StudentClassesRepository extends JpaRepository <StudentClass, Integer> {
    @Query(value = "SELECT * FROM student_classes WHERE completion_status = ?1", nativeQuery = true)
    List<StudentClass> findAll(String status);

    @Query(value = "SELECT co.course_code FROM student_classes s\n" +
            "JOIN classes c ON s.class_id = c.id\n" +
            "JOIN courses co ON c.course_id = co.id\n" +
            "WHERE s.completion_status <> 'FORGIVEN' AND s.student_id = ?1 AND co.id = ?2", nativeQuery = true)
    String checkForDuplicateCourse(Long studentId, Integer courseId);

    @Query(value = "SELECT DISTINCT COUNT(c.id)\n" +
            "FROM student_classes s, classes c1, classes c2\n" +
            "JOIN classes ON s.class_id = c.id\n" +
            "WHERE c.student_id.id = ?1 AND c.id = ?2 AND \n" +
            "AND s.completion_status = 'ENROLLED' AND REGEXP_LIKE(c.meeting_days, ?3) <> 0", nativeQuery = true)
    Set<String> findAllWithConflictingMeetingDays(Long studentId, Integer classId, String meetingDaysRegex);
}
