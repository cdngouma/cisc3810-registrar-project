package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.model.Major;
import cisc3810.ngoum.registrarservices.api.repository.MajorRepository;
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

//    @GetMapping("/majors/{id}")
//    public Major getMajorById(@PathVariable(name = "id") Integer majorId) throws NotFoundException {
//        return  majorRepository.findById(majorId)
//                .orElseThrow(() -> new NotFoundException(String.format("Major with id '%s' was not found", majorId)));
//    }

//    @PostMapping("/majors")
//    public Major createMajor(@Valid @RequestBody Major majorInfo) {
//        return majorRepository.save(majorInfo);
//    }
}
