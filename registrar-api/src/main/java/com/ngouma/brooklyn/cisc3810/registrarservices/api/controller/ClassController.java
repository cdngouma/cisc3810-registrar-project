package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class ClassController {
    @Autowired
    private ClassRepo classRepo;

    @GetMapping("/semesters/{semId}/classes")
    public List<ClassEntity> getAllClasses(@PathVariable(value = "semId") Integer semId,
                                           @RequestParam(value = "subject", required = false) String subj,
                                           @RequestParam(value = "r", required = false) String range,
                                           @RequestParam(value = "level", required = false) Integer level,
                                           @RequestParam(value = "opened", required = false) boolean opened){
        return classRepo.findAll(semId, subj, range, level, opened);
    }

    @GetMapping("/classes/{id}")
    public ClassEntity getClass(@PathVariable(value = "id") Integer classId){
        return classRepo.findById(classId);
    }

    @PostMapping("/semesters/{semId}/classes")
    public ClassEntity createClass(@PathVariable(value = "semId") Integer semId, @Valid @RequestBody ClassEntity classEntity){
        return classRepo.save(semId, classEntity);
    }

    @PutMapping("/classes/{id}")
    public ClassEntity updateClassInfo(@PathVariable(value = "id") Integer classId, @RequestBody ClassEntity details){
        return classRepo.update(classId, details);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<?> updateClassInfo(@PathVariable(value = "id") Integer classId){
        if(classRepo.delete(classId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
