package com.ngoum.cisc3810.registrar.registrarserver.controller;

import com.ngoum.cisc3810.registrar.registrarserver.model.Course;
import com.ngoum.cisc3810.registrar.registrarserver.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/courses")
public class CourseController {
    @Autowired
    private CourseRepository repository;

    @GetMapping
    public List<Course> getAllCoursesBySubject(@RequestParam(value="subject", required = false) String courseSubject){
        return repository.findAllCoursesBySubject(courseSubject);
    }

    @GetMapping(path="/{id}")
    public Course getCourseById(@PathVariable(value="id") int courseNo){
        return repository.findCourseById(courseNo);
    }
}
