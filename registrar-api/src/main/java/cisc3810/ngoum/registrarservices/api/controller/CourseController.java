package cisc3810.ngoum.registrarservices.api.controller;

import cisc3810.ngoum.registrarservices.api.model.Course;
import cisc3810.ngoum.registrarservices.api.repository.CourseRepository;
import cisc3810.ngoum.registrarservices.api.exception.NotFoundException;
import cisc3810.ngoum.registrarservices.api.exception.FailedCreateEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@RestController
public class CourseController {
    private Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public List<Course> getAllCourses(@RequestParam(value = "subject", required = false) String subjectCode) {
        if (subjectCode != null) return courseRepository.findAllBySubject(subjectCode);
        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable(value = "id") Integer courseNo) throws NotFoundException {
        return courseRepository.findById(courseNo)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id '%d' was not found", courseNo)));
    }

//    @PostMapping("/courses")
//    public Course createCourse(@Valid @RequestBody Course courseInfo) {
//        System.err.println(String.format("Course Info ->  %s", courseInfo.toString()));
//        Course course = courseRepository.save(courseInfo);
//        LOG.log(Level.INFO, String.format("New course created -> %s", course.toString()));
//        return course;
//    }
//
//    @PostMapping("/courses/{courseId}/prereqs")
//    public Course addPrerequisite(@PathVariable(value = "courseId") Integer courseId, @RequestBody List<String> prqsCourseCodeList) throws NotFoundException, SaveEntityFailedException {
//        try {
//            Course course = courseRepository.findById(courseId)
//                    .orElseThrow(() -> new NotFoundException(String.format("Course with id '%s' was not found", courseId)));
//
//            List<String> coursePrqs = new ArrayList<>();
//            if (course.getPrerequisites() != null) coursePrqs = Arrays.asList(course.getPrerequisites().split(";"));
//
//            for (String prqCourseCode : prqsCourseCodeList) {
//                if (Pattern.matches("^([A-Z]{4})-([0-9]{4})$", prqCourseCode)
//                        && courseRepository.validateCoursePrerequisite(courseId, prqCourseCode) != null) {
//                    if (coursePrqs.size() > 0) {
//                        coursePrqs.add(";" + prqCourseCode);
//                    } else {
//                        coursePrqs.add(prqCourseCode);
//                    }
//                } else {
//                    throw new SaveEntityFailedException(String.format
//                            ("Course with code [%s] could not be added as prerequisite to course [Id: %s]", prqCourseCode, courseId));
//                }
//            }
//
//            Collections.sort(coursePrqs);
//            course.setPrerequisites(String.join(";", coursePrqs));
//            return courseRepository.save(course);
//
//        } catch(SaveEntityFailedException | NotFoundException e) {
//            throw e;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SaveEntityFailedException(String.format
//                    ("Failed to add prerequisites [%s] to course with id '%s'", String.join("; ", prqsCourseCodeList), courseId));
//        }
//    }
//
    @PutMapping("/courses/{courseId}/prereqs")
    public Course updatePrerequisiteList(@PathVariable(value = "courseId") Integer courseId, @RequestBody List<String> prqsCourseCodeList) throws NotFoundException, FailedCreateEntityException {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new NotFoundException(String.format("Course with id '%s' was not found", courseId)));

            for (String prqCourseCode : prqsCourseCodeList) {
                if (!Pattern.matches("^([A-Z]{4})-([0-9]{4})$", prqCourseCode)
                        || courseRepository.validateCoursePrerequisite(courseId, prqCourseCode) == null) {
                    System.err.println(courseRepository.validateCoursePrerequisite(courseId, prqCourseCode));
                    throw new FailedCreateEntityException(String.format
                            ("Course with code [%s] could not be added as prerequisite to course [Id: %s]", prqCourseCode, courseId));
                }
            }

            Collections.sort(prqsCourseCodeList);
            course.setPrerequisites(String.join(";", prqsCourseCodeList));
            return courseRepository.save(course);

        } catch(FailedCreateEntityException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedCreateEntityException(String.format
                    ("Failed to add prerequisites [%s] to course with id '%s'", String.join(";", prqsCourseCodeList), courseId));
        }
    }
}
