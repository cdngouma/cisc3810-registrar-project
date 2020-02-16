package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.model.AcademicPeriod;
import cisc3810.ngoum.registrarservices.api.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class SemesterController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping("/semesters")
    public List<AcademicPeriod> getAllSemesters(@RequestParam(value = "current") Boolean active) {
        if (active) return semesterRepository.findAllActive();
        return semesterRepository.findAll();
    }

//    @GetMapping("/semesters/{id}")
//    public AcademicPeriod getSemesterById(@PathVariable(value = "id") Integer periodId) throws NotFoundException {
//        return semesterRepository.findById(periodId)
//                .orElseThrow(() -> new NotFoundException(String.format("Academic period with id '%d'", periodId)));
//    }

//    @PostMapping("/semesters")
//    public AcademicPeriod createSemester(@Valid @RequestBody AcademicPeriod semesterInfo) {
//        return semesterRepository.save(semesterInfo);
//    }
//
//    @DeleteMapping("/semesters/{id}")
//    public ResponseEntity<?> deleteSemester(@PathVariable(name = "id") Integer periodId) throws NotFoundException {
//        AcademicPeriod semester = semesterRepository.findById(periodId)
//                .orElseThrow(() -> new NotFoundException(String.format("Academic period with id '%d'", periodId)));
//        semesterRepository.delete(semester);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
