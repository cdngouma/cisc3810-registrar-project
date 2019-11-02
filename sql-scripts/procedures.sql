DELIMITER $$
CREATE PROCEDURE `EDIT_STUDENTS`(
	IN _studentId int,
    IN _email varchar(255),
    IN _firstName varchar(255),
    IN _lastName varchar(255),
    IN _gender varchar(6),
    IN _dob date,
    IN _degree varchar(10),
    IN _major varchar(255),
    IN _division char(1),
    IN _gpa double(3,2)
)
BEGIN
	IF _studentId IS NULL THEN
		INSERT INTO `Students` VALUES(NULL, _email, _firstName, _lastName, _gender, _dob, _degree, _major, _division, _gpa);
	ELSE
		UPDATE `Students` SET email_address=COALESCE(_email,email_address), first_name=COALESCE(_firstName, first_name), 
        last_name=COALESCE(_lastName,last_name), gender=COALESCE(_gender,gender), dob=COALESCE(_dob,dob), degree=COALESCE(_dergee,degree), 
        major=COALESCE(_major,major), division=COALESCE(_division,division), gpa=COALESCE=(_gpa,gpa)
        WHERE student_no=_studentId;
    END IF;
    
    SELECT * FROM `Students` WHERE student_no=COALESCE(_studentId, (SELECT MAX(student_no) FROM `Students`));
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_COURSE_BY_ID` (IN courseId int)
BEGIN
	SELECT C.id, C.course_name, C.course_level, C.subject_id, S.subject_name, S.subject_short, C.units, C.`description`
	FROM Courses C
	JOIN Subjects S ON S.id=C.subject_id
	WHERE C.id = courseId;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_COURSES_BY_SUBJECT_ID` (IN subjNo int)
BEGIN
	SELECT Courses.id, course_name, course_level, Courses.subject_id, subject_name, subject_short, 
		COUNT(Prerequisites.id) AS numPrereqs, COUNT(ConflictingCourses.id) AS numConflicting
	FROM Courses
	JOIN Subjects S ON S.id = C.subject_id
    JOIN Prerequisites ON Prerequisites.course_id = Courses.id
    JOIN ConflictingCourses ON ConflictingCourses.course_id = Courses.id
	WHERE Courses.subject_id = subjectId
    GROUP BY Courses.id;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_PREREQUISITES` (IN courseNo int)
BEGIN
	SELECT P.id, P.prereq_id, CONCAT(Subjects.subject_short,' ', Courses.course_level) AS courseName, P.`group`
	FROM Prerequisites P
	JOIN Courses ON P.prereq_id = Courses.id
	JOIN Subjects ON Courses.subject_id = Subjects.id
	WHERE P.id = courseNo;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_CONFLICTING_COURSES` (IN courseNo int)
BEGIN
	SELECT C.id, C.conflicting_course_id, CONCAT(Subjects.subject_short,' ', Courses.course_level) AS courseName
	FROM ConflictingCourses C
	JOIN Courses ON C.conflicting_course_id = Courses.id
	JOIN Subjects ON Courses.subject_id = Subjects.subject_id
	WHERE C.course_id = courseNo;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_CLASSES`(IN semesterId int, IN courseSubject varchar(4), IN levelRange varchar(7), IN isOpened boolean)
BEGIN
	DECLARE rel char(2); 
    DECLARE courseLevel int;
    
    IF(REGEXP_LIKE(levelRange, '^(gt|lt|eq|GT|LT|EQ):([0-9]{4})$') <> 1) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Incorrect format: " for entity \'level range\'';
	END IF;
    
    SET rel := SUBSTRING(levelRange, 1, 2);
	SET courseLevel := SUBSTRING(levelRange, 4, 4);
    
    SELECT Classes.id, Classes.course_id, Courses.course_name, CONCAT(Subjects.subject_short,' ',Courses.course_level) AS course_code,
		CONCAT(Instructors.first_name,' ',Instructors.last_name) AS instructor_name,
        CONCAT(Semesters.semester,' ',YEAR(Semesters.start_date)) AS semester, Semesters.start_date, Semesters.end_date,
        Classes.start_time, Classes.end_time, Classes.room, Classes.capacity, Classes.num_enrolled, Classes.class_mode, Classes.isopened
	FROM Classes
	JOIN Courses ON Courses.id = Classes.Course_id
	JOIN Subjects ON Subjects.id = Courses.subject_id
	JOIN Semesters ON Semesters.id = Classes.semester_id
    JOIN Instructors ON Instructors.id = Classes.instructor_id
	WHERE Classes.semester_id = semesterId
		-- If a subject is specified return all classes with given subject
		AND (courseSubject IS NULL OR Courses.subject_id = (SELECT id FROM Subjects WHERE UPPER(subject_short)=UPPER(courseSubject)))
        -- If a range and level is provided:
        AND ((rel IS NULL AND courseLevel IS NULL) OR (
				UPPER(rel) NOT IN ('GT','LT','EQ')
				-- if rel = gt return all classes with course level Greather Than the level
				OR (UPPER(rel)='GT' AND Courses.course_level > courseLevel)
				-- if rel = lt return all classes with course level Less Than the level
				OR (UPPER(rel)='LT' AND Courses.course_level < courseLevel)
                -- if rel = eq return all classes with course level EQual to the level
                OR (UPPER(rel)='EQ' AND Courses.course_level = courseLevel)
			))
		-- If opened = TRUE return all opened classes. Include closed classesin the return otherwise
		AND (isOpened IS NULL OR isOpened=FALSE OR Classes.isopened=TRUE);
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_CLASS_BY_ID` (IN classId int)
BEGIN
	SELECT Classes.id, Classes.course_id, Courses.course_name, CONCAT(Subjects.subject_short,' ',Courses.course_level) AS course_code,
		CONCAT(Instructors.first_name,' ',Instructors.last_name) AS instructor_name,
        CONCAT(Semesters.semester,' ',YEAR(Semesters.start_date)) AS semester, Semesters.start_date, Semesters.end_date,
        Classes.start_time, Classes.end_time, Classes.room, Classes.capacity, Classes.num_enrolled, Classes.class_mode, Classes.isopened
	FROM Classes
	JOIN Courses ON Courses.id = Classes.Course_id
	JOIN Subjects ON Subjects.id = Courses.subject_id
	JOIN Semesters ON Semesters.id = Classes.semester_id
    JOIN Instructors ON Instructors.id = Classes.instructor_id
	WHERE Classes.id = classId;
END; $$
DELIMITER ;