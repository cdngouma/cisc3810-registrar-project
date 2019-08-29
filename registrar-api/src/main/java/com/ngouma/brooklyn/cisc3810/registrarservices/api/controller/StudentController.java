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
        if(students.size() > 0){
            return new ResponseEntity<>(students, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No students available", HttpStatus.OK);
        }
    }

    @GetMapping(path = "/students/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable(value = "studentId") Integer id){
        Student student = studentRepo.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(path = "/students")
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student){
        Student newStudent = studentRepo.save(student);
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }

    @PutMapping(path = "/students/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable(value = "studentId") Integer id, @RequestBody Student details){
        Student student = studentRepo.update(id, details);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(path = "/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "studentId") Integer id){
        if(studentRepo.delete(id))
            return new ResponseEntity<>("Student removed", HttpStatus.OK);
        return new ResponseEntity<>("No student to remove", HttpStatus.OK);
    }
}
