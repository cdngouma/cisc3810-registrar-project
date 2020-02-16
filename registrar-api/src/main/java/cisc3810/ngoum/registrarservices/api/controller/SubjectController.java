package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.model.Subject;
import cisc3810.ngoum.registrarservices.api.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SubjectController {
    private Logger LOG = Logger.getLogger(getClass().getName());
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

//    @GetMapping("/subjects/{id}")
//    public Subject getSubjectById(@PathVariable(value = "id") Integer subjectId) throws NotFoundException {
//        return subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new NotFoundException(String.format("Subject with id : '%d' not found", subjectId)));
//    }
}
