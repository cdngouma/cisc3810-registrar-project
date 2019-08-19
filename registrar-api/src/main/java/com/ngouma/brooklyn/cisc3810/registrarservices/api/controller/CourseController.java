package com.ngouma.brooklyn.cisc3810.registrarservices.api.controller;

import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.Course;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.model.CourseWrapper;
import com.ngouma.brooklyn.cisc3810.registrarservices.api.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping(path = "/courses")
    public List<Course> getAllCourses(){
        return courseRepo.findAllCourses(null);
    }

    @GetMapping(path = "/subjects/{id}/courses")
    public List<Course> getAllCourses(@PathVariable(value = "id") Integer subjId) {
        return courseRepo.findAllCourses(subjId);
    }

    @GetMapping(path = "/courses/{id}")
    public Course getCourse(@PathVariable(value = "id") Integer courseNo){
        return courseRepo.findCourseById(courseNo);
    }

    @GetMapping("/courses/{id}/prerequisites")
    public List<CourseWrapper> getPrerequisites(@PathVariable(value = "id") Integer courseNo){
        return courseRepo.findAllPrerequisites(courseNo);
    }

    @GetMapping("/courses/{id}/conflicting")
    public List<CourseWrapper> getConflicting(@PathVariable(value = "id") Integer courseNo) {
        return courseRepo.findAllConflicting(courseNo);
    }

    @PostMapping(path = "/courses")
    public Course createCourse(@RequestBody Course course){
        return courseRepo.save(course);
    }

    @PostMapping(path = "/courses/{id}/prerequisites")
    public Course addPrerequisite(@PathVariable(value = "id") Integer courseId, @Valid @RequestBody CourseWrapper details){
        return courseRepo.addPrerequisite(courseId, details);
    }

    @PostMapping(path = "/courses/{id}/conflicting")
    public Course addConflictingCourses(@PathVariable(value = "id") Integer courseId, @Valid @RequestBody CourseWrapper details){
        return courseRepo.addConflictingCourse(courseId, details);
    }

    @PutMapping(path = "/courses/{id}")
    public Course updateCourse(@PathVariable(name = "id") Integer courseId, @Valid @RequestBody Course details){
        return courseRepo.updateCourse(courseId, details);
    }

    @DeleteMapping(path = "/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Integer courseId){
        if(courseRepo.deleteCourseById(courseId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/prerequisites/{id}")
    public ResponseEntity<?> deletePrerequisite(@PathVariable(value = "id") Integer prereqId){
        if(courseRepo.deletePrerequisiteById(prereqId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/conflicting/{id}")
    public ResponseEntity<?> deleteConflictingCourse(@PathVariable(value = "id") Integer confId){
        if(courseRepo.deleteConflictingById(confId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
