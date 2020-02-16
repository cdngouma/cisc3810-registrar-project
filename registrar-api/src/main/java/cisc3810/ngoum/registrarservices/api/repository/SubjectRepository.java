package cisc3810.ngoum.registrarservices.api.repository;

import cisc3810.ngoum.registrarservices.api.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> { }
