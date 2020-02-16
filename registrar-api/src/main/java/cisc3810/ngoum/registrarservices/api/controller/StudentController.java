package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.FailedCreateEntityException;
import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.model.Student;
import cisc3810.ngoum.registrarservices.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class StudentController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents(@RequestParam(name = "lastName", required = false) String lastName,
                                        @RequestParam(name = "major", required = false) Integer majorId) {
        return studentRepository.findAll(lastName, majorId);
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable(name = "id") Long studentId) throws NotFoundException {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%d' was not found", studentId)));
    }

    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student studentInfo) throws FailedCreateEntityException {
        try {
            Student student = studentRepository.findById(studentRepository.save(studentInfo).getId())
                    .orElseThrow(() -> new FailedCreateEntityException("Failed to generate new ID"));
            LOG.log(Level.INFO, String.format("Created new student: %s", student.toString()));
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedCreateEntityException("Failed to create new student");
        }
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable(name = "id") Long studentId, @RequestParam Student studentInfo) throws NotFoundException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));

        if (studentInfo.getFirstName() != null) student.setFirstName(studentInfo.getFirstName());
        if (studentInfo.getLastName() != null) student.setFirstName(studentInfo.getLastName());
        if (studentInfo.getDateOfBirth() != null) student.setDateOfBirth(studentInfo.getDateOfBirth());
        if (studentInfo.getGender() != null) student.setGender(studentInfo.getGender());
        if (studentInfo.getMajor() != null) student.setMajor(studentInfo.getMajor());

        return studentRepository.save(student);
    }
}
