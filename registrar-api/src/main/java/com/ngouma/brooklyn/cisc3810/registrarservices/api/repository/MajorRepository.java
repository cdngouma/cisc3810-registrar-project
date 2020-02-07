package com.ngouma.brooklyn.cisc3810.registrarservices.api.repository;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MajorRepository extends JpaRepository <Major, Integer> {
    @Query(value = "SELECT * FROM majors WHERE ?1 IS NULL OR degree = UPPER(?1)", nativeQuery = true)
    List<Major> findAll(String degree);
}
