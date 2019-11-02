package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.helper.Response;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<?> getAllCoursesBySubject(@RequestParam(value = "subject") String subject) {
        List<Course> subjects =  courseRepository.findAllBySubject(subject);
        if(subjects.size() > 0) {
            return new ResponseEntity<>(new Response(subjects), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCourse(@PathVariable(value = "id") Integer id){
        Course course = courseRepository.findCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
