package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.NotFoundException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ClassController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/classes")
    private List<ClassEntity> getAllClasses(@RequestParam(value = "q", required = false) String filters) {
        // TODO: Implement search with filters
        // if (filters != null) return classRepository.findAllFiltered(filters);
        return classRepository.findAll();
    }

    @GetMapping("/classes/{id}")
    private ClassEntity getClassById(@PathVariable(value = "id") Integer classId) throws NotFoundException {
        //ClassEntity classEntity = classRepository.findById(classId)
        //        .orElseThrow(() -> new NotFoundException(String.format("Class with id '%d' was not found", classId)));
        //LOG.log(Level.INFO, String.format("Class created ->  %s", classEntity.toString()));
        //return classEntity;
        return null;
    }
}
