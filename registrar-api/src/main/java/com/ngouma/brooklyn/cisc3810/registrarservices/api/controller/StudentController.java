package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Student;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @GetMapping(path = "/students")
    public ResponseEntity<?> getAllStudents(){
        List<Student> students = studentRepo.findAll();
        if(students.size() > 0) return new ResponseEntity<>(students, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/students/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable(value = "studentId") Integer id){
        Student student = studentRepo.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
