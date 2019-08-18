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
@RequestMapping(path = "/courses")
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseRepo.findAllCourses();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable(value = "id") Long courseNo){
        return courseRepo.findCourseById(courseNo);
    }

    @GetMapping("/{id}/prereq")
    public List<CourseWrapper> getPrerequisites(@PathVariable(value = "id") Long courseNo){
        return courseRepo.findAllPrereq(courseNo);
    }

    @GetMapping("/{id}/conf")
    public List<CourseWrapper> getConflicting(@PathVariable(value = "id") Long courseNo) {
        return courseRepo.findAllConflicting(courseNo);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course){
        return courseRepo.save(course);
    }

    @PostMapping(path = "/{id}/prereq")
    public Course addPrerequisite(@PathVariable(value = "id") Long courseId, @Valid @RequestBody CourseWrapper details){
        return courseRepo.addPrerequisite(courseId, details);
    }

    @PostMapping(path = "/{id}/conf")
    public Course addConflictingCourses(@PathVariable(value = "id") Long courseId, @Valid @RequestBody CourseWrapper details){
        return courseRepo.addConflictingCourse(courseId, details);
    }

    @PutMapping(path = "/{id}")
    public Course updateCourse(@PathVariable(name = "id") Long courseId, @Valid @RequestBody Course details){
        return courseRepo.updateCourse(courseId, details);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long courseId){
        if(courseRepo.deleteCourseById(courseId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}/prereq")
    public ResponseEntity<?> deletePrerequisite(@PathVariable(value = "id") Long prereqId){
        if(courseRepo.deletePrereqById(prereqId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}/conf")
    public ResponseEntity<?> deleteConflictingCourse(@PathVariable(value = "id") Long confId){
        if(courseRepo.deleteConflictingById(confId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
