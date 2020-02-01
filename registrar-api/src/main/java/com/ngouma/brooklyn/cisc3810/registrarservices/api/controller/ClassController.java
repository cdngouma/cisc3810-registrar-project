package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.ConflictingEntityException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.NotFoundException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.exception.SaveEntityFailedException;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.ClassEntity;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        return classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(String.format("Class with id '%d' was not found", classId)));
    }

    @PostMapping("/classes")
    public ClassEntity createClass(@Valid @RequestBody ClassEntity classInfo) throws ConflictingEntityException, SaveEntityFailedException {
        String classDays = classInfo.getMeetingDays();
        StringBuilder classDaysRegex = new StringBuilder();

        for (int i = 0; i < classDays.length(); i++) {
            classDaysRegex.append(classDays.charAt(i));
            if (i % 2  != 0 && i < classDays.length() - 1) classDaysRegex.append("|");
        }

        boolean valid = classRepository.findConflictWithClass(classInfo.getSemester().getId(), classInfo.getEndTime(),
                classInfo.getStartTime(), classDaysRegex.toString(), classInfo.getInstructor().getId(), classInfo.getRoom()) == null;

        if (!valid) {
            throw new ConflictingEntityException("The class submitted overlap with other classes");
        }

        return classRepository.findById(classRepository.save(classInfo).getId())
                .orElseThrow(() -> new SaveEntityFailedException("Failed to create class"));
        //return null;
    }
}
