package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Class;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/api")
public class ClassController {
    @Autowired
    private ClassRepo classRepo;

    @GetMapping("/semesters/{semId}/classes")
    public ResponseEntity<?> getAllClasses(@PathVariable(value = "semId") Integer semesterId,
                                           @RequestParam(value = "subject", required = false) String subject,
                                           @RequestParam(value = "level", required = false) String levelRange,
                                           @RequestParam(value = "opened", required = false) boolean opened){
        if(levelRange != null && !Pattern.matches("^(gt|lt|eq|GT|LT|EQ):([0-9]{4})$", levelRange))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Class> classes = classRepo.findAll(semesterId, subject, levelRange, opened);
        if(classes.size() < 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<?> getClass(@PathVariable(value = "id") Integer classId){
        return new ResponseEntity<>(classRepo.findById(classId), HttpStatus.OK);
    }
}