package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.helper.Response;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/semesters")
public class SemesterController {
    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping
    public ResponseEntity<?> getAllSemesters(@RequestParam(value = "active", required = false) boolean active){
        List<Semester> semesters = semesterRepository.findAll(active);
        if(semesters.size() > 0) {
            return new ResponseEntity<>(new Response(semesters), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSemester(@PathVariable(value = "id") Integer id){
        System.err.println(id);
        Semester semester = semesterRepository.findById(id);
        return new ResponseEntity<>(semester, HttpStatus.OK);
    }
}
