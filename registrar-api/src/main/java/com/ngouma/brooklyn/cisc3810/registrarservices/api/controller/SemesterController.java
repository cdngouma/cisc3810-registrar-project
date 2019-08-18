package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepo;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/semesters")
public class SemesterController {
    @Autowired
    private SemesterRepo semesterRepo;

    @GetMapping
    public List<Semester> getAllSemesters(@RequestParam(value = "current") boolean current){
        if(current) return semesterRepo.findAllCurrent();
        return semesterRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public Semester getSemester(@PathVariable(value = "id") Long semId){
        return semesterRepo.findById(semId);
    }

    @PostMapping
    public Semester createSemester(@Valid @RequestBody Semester semester){
        return semesterRepo.save(semester);
    }

    @PutMapping(path = "/{id}")
    public Semester updateSemester(@PathVariable(value = "id") Long semId, @Valid @RequestBody Semester details){
        return semesterRepo.update(semId, semesterRepo.update(semId, details));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long semId){
        if(semesterRepo.deleteById(semId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
