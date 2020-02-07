package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Major;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class MajorController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private MajorRepository majorRepository;

    @GetMapping("/majors")
    public List<Major> getAllMajors(@RequestParam(name = "degree", required = false) String degree) {
        return majorRepository.findAll(degree);
    }

    @PostMapping("/majors")
    public Major createMajor(@Valid @RequestBody Major majorInfo) {
        return majorRepository.save(majorInfo);
    }
}
