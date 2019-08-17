package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/semesters")
public class SemesterController {
    @Autowired
    private SemesterRepo semesterRepo;

    @GetMapping
    public List<Semester> getAllSemesters(@RequestParam(value = "current") boolean current){
        if(current) return semesterRepo.findAllCurrent();
        return semesterRepo.findAll();
    }

}
