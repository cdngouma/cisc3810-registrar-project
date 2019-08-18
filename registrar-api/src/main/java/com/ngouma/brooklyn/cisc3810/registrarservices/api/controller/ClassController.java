package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassController {
    @Autowired
    private ClassRepo classRepo;

    @GetMapping("/semesters/{semId}/classes")
    public List<ClassEntity> getAllClasses(@PathVariable(value = "semId") Long semId,
                                           @RequestParam(value = "subject", required = false) String subj,
                                           @RequestParam(value = "r", required = false) String range,
                                           @RequestParam(value = "level", required = false) Integer level,
                                           @RequestParam(value = "opened", required = false) boolean opened){
        return classRepo.findAll(semId, subj, range, level, opened);
    }

    @GetMapping("/classes/{id}")
    public ClassEntity getClass(@PathVariable(value = "id") Long classId){
        return classRepo.findById(classId);
    }
}
