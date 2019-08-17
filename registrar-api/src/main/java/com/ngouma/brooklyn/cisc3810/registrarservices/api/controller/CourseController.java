package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable(value = "id") Long courseNo){
        return courseRepo.findById(courseNo);
    }

    @PostMapping
    public Course createCourse(@Valid @RequestBody Course course){
        return courseRepo.save(course);
    }

    @PutMapping(path = "/{id}")
    public Course updateCourse(@PathVariable(name = "id") Long courseId, @Valid @RequestBody Course details){
        Course course = courseRepo.findById(courseId);
        course.setSubjectId(details.getSubjectId());
        course.setLevel(details.getLevel());
        course.setName(details.getName());
        course.setUnits(details.getUnits());
        course.setDescription(details.getDescription());
        courseRepo.update(course);

        return course;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCourse(Long courseId){
        courseRepo.deleteById(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
