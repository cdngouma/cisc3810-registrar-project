package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Class;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassRepo classRepo;

    @GetMapping("/{semId}")
    public List<Class> getAllClasses(@PathVariable(value = "semId") Long semesterId){
        return classRepo.findAll(semesterId);
    }
}
