package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.NotFoundException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Instructor;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class InstructorController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("/instructors")
    public List<Instructor> getAllInstructors(@RequestParam(value = "subject", required = false) String subjectCode,
                                              @RequestParam(value = "lastName", required = false) String lastName) {
        return instructorRepository.findAll(subjectCode, lastName);
    }

    @GetMapping("/instructors/{id}")
    public Instructor getInstructorById(@PathVariable(name = "id") Long instructorId) throws NotFoundException {
        return instructorRepository.findById(instructorId).
                orElseThrow(() -> new NotFoundException(String.format("The instructor with id '%s' was not found", instructorId)));
    }

//    @PostMapping("/instructors")
//    public Instructor createInstructor(@Valid @RequestBody Instructor instructorInfo) {
//        return instructorRepository.save(instructorInfo);
//    }
}
