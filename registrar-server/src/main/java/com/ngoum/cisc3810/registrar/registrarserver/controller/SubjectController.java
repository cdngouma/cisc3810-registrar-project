package com.ngoum.cisc3810.registrar.registrarserver.controller;

import com.ngoum.cisc3810.registrar.registrarserver.model.CourseSubject;
import com.ngoum.cisc3810.registrar.registrarserver.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/subjects")
public class SubjectController {
    @Autowired
    private SubjectRepository repository;

    @RequestMapping
    public List<CourseSubject> getAllSubjects(){
        System.out.println("getAllSubjects called");
        return repository.findAllSubjects();
    }
}
