package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class SemesterController {
    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private ClassRepo classRepo;

    @GetMapping(path = "/semesters")
    public ResponseEntity<?> getAllSemesters(@RequestParam(value = "current") boolean current){
        List<Semester> semesters = semesterRepo.findAll(current);
        if(semesters.size() < 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

    @GetMapping(path = "/semesters/{id}")
    public ResponseEntity<?> getSemester(@PathVariable(value = "id") Integer semId){
        Semester semester = semesterRepo.findById(semId);
        return new ResponseEntity<>(semester, HttpStatus.OK);
    }
}
