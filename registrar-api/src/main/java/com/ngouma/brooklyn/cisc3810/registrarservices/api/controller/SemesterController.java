package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.NotFoundException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.AcademicPeriod;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SemesterController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping("/semesters")
    public List<AcademicPeriod> getAllSemesters(@RequestParam(value = "current", required = false) Boolean active) {
        if (active != null && active) return semesterRepository.findAllActive();
        return semesterRepository.findAll();
    }

    @GetMapping("/semesters/{id}")
    public AcademicPeriod getSemesterById(@PathVariable(value = "id") Integer periodId) throws NotFoundException {
        AcademicPeriod semester = semesterRepository.findById(periodId)
                .orElseThrow(() -> new NotFoundException(String.format("Academic period with id '%d'", periodId)));
        LOG.log(Level.INFO, semester.toString());
        return semester;
    }
}
