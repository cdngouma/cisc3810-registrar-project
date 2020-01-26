package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.NotFoundException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SubjectController {
    private Logger LOG = Logger.getLogger(getClass().getName());
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/subjects/{id}")
    public Subject getSubjectById(@PathVariable(value = "id") Integer subjectId) throws NotFoundException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(String.format("Subject with id : '%d' not found", subjectId)));
        LOG.log(Level.INFO, subject.toString());
        return subject;
    }
}
