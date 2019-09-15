package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepo;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping(path = "/subjects")
    public ResponseEntity<?> getAllSubjects() {
        List<Subject> subjects = subjectRepo.findAll();
        if(subjects.size() < 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping(path = "/subjects/{id}")
    public ResponseEntity<?> getSubject(@PathVariable(value = "id") Integer subjId) {
        Subject subject = subjectRepo.findById(subjId);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }
}
