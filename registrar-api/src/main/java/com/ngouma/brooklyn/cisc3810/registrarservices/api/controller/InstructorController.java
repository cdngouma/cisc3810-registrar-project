package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.helper.Response;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Instructor;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.InstructorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/instructors")
public class InstructorController {
    @Autowired
    private InstructorsRepository instructorsRepository;

    @GetMapping
    public ResponseEntity<?> getAllSemesters(@RequestParam(value = "dept", required = false) String dept){
        List<Instructor> instructors = new ArrayList<>();
        if (dept == null) {
            instructors = instructorsRepository.findAll();
        } else {
            instructors = instructorsRepository.findAllByDepartment(dept);
        }

        if(instructors.size() > 0) {
            return new ResponseEntity<>(new Response(instructors), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSemester(@PathVariable(value = "id") Integer id){
        System.err.println(id);
        Instructor instructor = instructorsRepository.findById(id);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }
}
