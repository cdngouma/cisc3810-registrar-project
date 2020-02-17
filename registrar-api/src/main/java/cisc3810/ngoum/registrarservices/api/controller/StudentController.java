package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.exception.*;
import cisc3810.ngoum.registrarservices.api.model.Student;
import cisc3810.ngoum.registrarservices.api.model.StudentClass;
import cisc3810.ngoum.registrarservices.api.repository.ClassRepository;
import cisc3810.ngoum.registrarservices.api.repository.StudentClassesRepository;
import cisc3810.ngoum.registrarservices.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RestController
public class StudentController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentClassesRepository studentClassesRepository;

    @Autowired
    private ClassRepository classRepository;

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

    @GetMapping("/students/{studentId}/classes")
    public List<StudentClass> getStudentClasses(@PathVariable(name = "studentId") Long studentId, @RequestParam(name = "status") String status) throws BadRequestException, NotFoundException {
        // Verify that student with given id exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));

        if (status.compareTo("COMPLETED") == 0 || status.compareTo("ENROLLED") == 0 || status.compareTo("FORGIVEN") == 0) {
            return studentClassesRepository.findAll(status);
        } else {
            throw new BadRequestException();
        }
    }

    @GetMapping("/students/{studentId}/classes/{classId}")
    public StudentClass getStudentClassById(@PathVariable(name = "id") Long studentId, @PathVariable(name = "classId") Integer classId) throws NotFoundException {
        // Verify that student with given id exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));

        return studentClassesRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(String.format("Class with id '%s' was not found", classId)));
    }

//    @PostMapping("/students")
//    public Student createStudent(@Valid @RequestBody Student studentInfo) throws FailedCreateEntityException {
//        try {
//            return studentRepository.findById(studentRepository.save(studentInfo).getId())
//                    .orElseThrow(() -> new FailedCreateEntityException("Failed to generate new ID"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new FailedCreateEntityException("Failed to create new student");
//        }
//    }

    @PostMapping("/students/{studentId}/classes")
    public StudentClass enrollInClass(@PathVariable(name = "studentId") Long studentId,
                                                 @Valid @RequestBody StudentClass classInfo) throws NotFoundException, DuplicatedEntryException, FailedCreateEntityException, ConflictingEntityException {
        // Verify that student with given id exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));

        // Check if student is already enrolled in this class
        if (studentClassesRepository.findById(classInfo.getClassEntity().getId()).isPresent()) {
            throw new DuplicatedEntryException(String.format("Student already enrolled in class with id '%d'", classInfo.getClassEntity().getId()));
        }

        // Check if course was already taken
        String courseCode = studentClassesRepository.checkForDuplicateCourse(studentId, classInfo.getClassEntity().getId());
        if (courseCode != null) {
            throw new FailedCreateEntityException(String.format("Course already completed [%s]", courseCode));
        }

        // Check for conflict with time and days
        String meetingDays = classRepository.findMeetingDays(classInfo.getClassEntity().getId());
        StringBuilder meetingDaysRegex = new StringBuilder();

        for (int i = 0; i < meetingDays.length(); i++) {
            meetingDaysRegex.append(meetingDays.charAt(i));
            if (i % 2 != 0 && i < meetingDays.length() - 1) meetingDaysRegex.append("|");
        }

        Set<String> conflictingClasses = studentClassesRepository.findAllWithConflictingMeetingDays(studentId, classInfo.getClassEntity().getId(), meetingDaysRegex.toString());

        if (!conflictingClasses.isEmpty()) {
            throw new ConflictingEntityException(String.format("(%d) Conflicting classes found %s", conflictingClasses.size(), conflictingClasses.toString()));
        }

        // Check for conflicting courses

        // Check for prerequisites

        // Force default value for Grade and Completion Status
        classInfo.setGrade(null);
        classInfo.setStatus(null);

        return studentClassesRepository.findById(studentClassesRepository.save(classInfo).getId())
                .orElseThrow(() -> new NotFoundException("An unexpected Error occurred"));
    }

//    @PutMapping("/students/{id}")
//    public Student updateStudent(@PathVariable(name = "id") Long studentId, @RequestParam Student studentInfo) throws NotFoundException {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));
//
//        if (studentInfo.getFirstName() != null) student.setFirstName(studentInfo.getFirstName());
//        if (studentInfo.getLastName() != null) student.setFirstName(studentInfo.getLastName());
//        if (studentInfo.getDateOfBirth() != null) student.setDateOfBirth(studentInfo.getDateOfBirth());
//        if (studentInfo.getGender() != null) student.setGender(studentInfo.getGender());
//        if (studentInfo.getMajor() != null) student.setMajor(studentInfo.getMajor());
//
//        return studentRepository.save(student);
//    }

    @DeleteMapping("/students/{studentId}/classes/{classId}")
    public ResponseEntity<?> dropFromClass(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "classId") Integer classId) throws NotFoundException {
        // Verify that student with given id exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id '%s' was not found", studentId)));
        // Verify that student is enroled in class
        StudentClass studentClass = studentClassesRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(String.format("Student is not enrolled in class with id '%d'", classId)));

        if (studentClass.getStatus().compareTo("ENROLLED") != 0) {
            throw new NotFoundException(String.format("Student is not enrolled in class with id '%d'", classId));
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
