package cisc3810.ngoum.registrarservices.api.repository;

import cisc3810.ngoum.registrarservices.api.model.AcademicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SemesterRepository extends JpaRepository <AcademicPeriod, Integer> {
    @Query(value = "SELECT * FROM academic_periods WHERE start_date >= CURRENT_DATE()", nativeQuery = true)
    List<AcademicPeriod> findAllActive();
}
