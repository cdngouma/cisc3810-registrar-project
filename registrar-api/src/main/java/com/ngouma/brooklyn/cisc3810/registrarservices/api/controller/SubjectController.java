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
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public Subject getSubject(@PathVariable(value = "id") Long subjId) {
        return subjectRepo.findById(subjId);
    }

    @GetMapping(path = "/{id}/courses")
    public List<Course> getAllCourses(@PathVariable(value = "id") Long subjId) {
        return courseRepo.findAll(subjId);
    }

    @PostMapping
    public Subject createSubject(@Valid @RequestBody Subject subject) {
        return subjectRepo.save(subject);
    }

    @PutMapping(path = "/{id}")
    public Subject updateSubject(@PathVariable(value = "id") Long subjId, @Valid @RequestBody Subject subjectDetails) {
        Subject subject = subjectRepo.findById(subjId);
        subject.setName(subjectDetails.getName());
        subject.setNameShort(subjectDetails.getNameShort());
        return subject;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllSubject() {
        subjectRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable(value = "id") Long subjId) {
        subjectRepo.deleteById(subjId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
