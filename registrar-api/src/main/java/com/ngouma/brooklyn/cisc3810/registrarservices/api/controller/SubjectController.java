package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.helper.Response;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepository;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        if(subjects.size() > 0) {
            return new ResponseEntity<>(new Response(subjects), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/id}")
    public ResponseEntity<?> getSubject(@PathVariable(value = "id") Integer id) {
        Subject subject = subjectRepository.findById(id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }
}
