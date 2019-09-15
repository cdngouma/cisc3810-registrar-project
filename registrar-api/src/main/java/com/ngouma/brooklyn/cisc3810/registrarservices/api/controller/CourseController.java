package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Subject;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.course.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping(path = "/subjects/{id}/courses")
    public ResponseEntity<?> getAllCoursesBySubject(@PathVariable(value = "id") Integer subjectId) {
        List<Course> subjects =  courseRepo.findAllBySubjectId(subjectId);
        if(subjects.size() < 1) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping(path = "/courses/{id}")
    public ResponseEntity<?> getCourse(@PathVariable(value = "id") Integer courseNo){
        Course course = courseRepo.findCourseById(courseNo);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
