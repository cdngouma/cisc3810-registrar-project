package com.ngoum.cisc3810.registrar.registrarserver.controller;

import com.ngoum.cisc3810.registrar.registrarserver.model.Semester;
import com.ngoum.cisc3810.registrar.registrarserver.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/semesters")
public class SemesterController {
    @Autowired
    private SemesterRepository repository;

    @RequestMapping
    public List<Semester> getActiveEnrollmentPeriods(){
        return repository.findActiveEnrollmentPeriods();
    }
}
