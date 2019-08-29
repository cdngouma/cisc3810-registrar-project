package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepo;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping(path = "/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    @GetMapping(path = "/subjects/{id}")
    public Subject getSubject(@PathVariable(value = "id") Integer subjId) {
        return subjectRepo.findById(subjId);
    }

    @PostMapping(path = "/subjects")
    public Subject createSubject(@Valid @RequestBody Subject subject) {
        return subjectRepo.save(subject);
    }

    @PutMapping(path = "/subjects/{id}")
    public Subject updateSubject(@PathVariable(value = "id") Integer subjId, @RequestBody Subject details) {
        return subjectRepo.update(subjId, details);
    }

    @DeleteMapping(path = "/subjects/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable(value = "id") Integer subjId) {
        if(subjectRepo.deleteById(subjId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
