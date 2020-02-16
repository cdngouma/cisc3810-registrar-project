package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.FailedCreateEntityException;
import cisc3810.ngoum.registrarservices.api.model.ClassEntity;
import cisc3810.ngoum.registrarservices.api.exception.ConflictingEntityException;
import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

//    @PostMapping("/classes")
//    public ClassEntity createClass(@Valid @RequestBody ClassEntity classInfo) throws ConflictingEntityException, FailedCreateEntityException {
//        String classDays = classInfo.getMeetingDays();
//        StringBuilder classDaysRegex = new StringBuilder();
//
//        for (int i = 0; i < classDays.length(); i++) {
//            classDaysRegex.append(classDays.charAt(i));
//            if (i % 2  != 0 && i < classDays.length() - 1) classDaysRegex.append("|");
//        }
//
//        boolean valid = classRepository.findConflictWithClass(classInfo.getSemester().getId(), classInfo.getEndTime(),
//                classInfo.getStartTime(), classDaysRegex.toString(), classInfo.getInstructor().getId(), classInfo.getRoom()) == null;
//
//        if (!valid) {
//            throw new ConflictingEntityException("The class submitted overlap with other classes");
//        }
//
//        return classRepository.findById(classRepository.save(classInfo).getId())
//                .orElseThrow(() -> new FailedCreateEntityException("Failed to create class"));
//    }
}
