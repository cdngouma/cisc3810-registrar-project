package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Semester;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class SemesterController {
    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private ClassRepo classRepo;

    @GetMapping(path = "/semesters")
    public List<Semester> getAllSemesters(@RequestParam(value = "current") boolean current){
        if(current) return semesterRepo.findAllCurrent();
        return semesterRepo.findAll();
    }

    @GetMapping(path = "/semesters/{id}")
    public Semester getSemester(@PathVariable(value = "id") Long semId){
        return semesterRepo.findById(semId);
    }

    @PostMapping("/semesters")
    public Semester createSemester(@Valid @RequestBody Semester semester){
        return semesterRepo.save(semester);
    }

    @PutMapping(path = "/semesters/{id}")
    public Semester updateSemester(@PathVariable(value = "id") Long semId, @Valid @RequestBody Semester details){
        return semesterRepo.update(semId, semesterRepo.update(semId, details));
    }

    @DeleteMapping(path = "/semesters/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long semId){
        if(semesterRepo.deleteById(semId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
