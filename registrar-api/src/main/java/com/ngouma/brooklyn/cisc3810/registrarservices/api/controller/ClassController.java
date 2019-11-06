package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.helper.Response;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/api/classes")
public class ClassController {
    @Autowired
    private ClassRepository classRepository;

    @GetMapping
    public ResponseEntity<?> getAllClasses(@RequestParam(value = "semester") String semester,
                                           @RequestParam(value = "subject", required = false) String subject,
                                           @RequestParam(value = "level", required = false) String levelRange,
                                           @RequestParam(value = "opened", required = false) boolean opened){
        if(levelRange != null && !Pattern.matches("^(gt|lt|eq|GT|LT|EQ):([0-9]{4})$", levelRange)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ClassEntity> classes = classRepository.findAll(semester, subject, levelRange, opened);
        if(classes.size() > 0) {
            return new ResponseEntity<>(new Response(classes), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClass(@PathVariable(value = "id") Integer id){
        ClassEntity classEntity = classRepository.findById(id);
        return new ResponseEntity<>(classEntity, HttpStatus.OK);
    }
}