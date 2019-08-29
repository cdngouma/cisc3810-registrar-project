DELIMITER $$
CREATE PROCEDURE `EDIT_SUBJECTS` (IN _subjNo int, _subjName varchar(255), _subjAbv varchar(4))
BEGIN
	IF subjNo IS NULL THEN
		INSERT INTO `Subjects`(subj_name, subj_abv) VALUES(_subjName, _subjAbv);
	ELSE
		UPDATE `Subjects` SET subj_name=COALESCE(subjName, subj_name), subj_abv=COALESCE(_subjAbv, subj_abv)
        WHERE subj_no = _subjNo;
	END IF;
    
    SELECT * FROM `Subjects` WHERE subj_no = COALESCE(_subjNo, (SELECT MAX(subj_no) FROM `Subjects`));
    
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `EDIT_SEMESTERS` (IN _semId int, _semName varchar(255), _startDate date, _endDate date)
BEGIN
	IF _semId IS NULL THEN
		INSERT INTO `Semesters`(sem_name, start_date, end_date) VALUES(_semName, _startDate, _endDate);
	ELSE
		UPDATE Semesters SET sem_name=COALESCE(_semName,sem_name), start_date=COALESCE(_startDate,start_date), end_date=COALESCE(_endDate,end_date)
        WHERE sem_no=_semId;
	END IF;
    
    SELECT * FROM `Semesters` WHERE sem_no = COALESCE(_semId, (SELECT MAX(sem_no) FROM `Semesters`));    
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `EDIT_COURSES` (
	IN _courseId int,
    IN _subjNo int,
    IN _level int,
    IN _courseName varchar(255),
    IN _units float,
    IN _desc varchar(600)
)
BEGIN
	IF _courseId IS NULL THEN
		INSERT INTO `Courses`(subj_no, course_level, course_name, units, course_desc) VALUES(_subjNo, _level, _courseName, _units, _desc);
	ELSE
		UPDATE `Courses` SET subj_no=COALESCE(_subjNo,subj_no), course_level=COALESCE(_startDate,course_level),
        course_name=COALESCE(_courseName,course_name), units=COALESCE(_units,units), course_desc=COALESCE(_desc,course_desc)
        WHERE course_no=_courseId;
	END IF;
    
    SELECT * FROM `Courses` WHERE course_no = COALESCE(_courseId, (SELECT MAX(course_no) FROM `Courses`));    
END; $$
DELIMITER ;

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
CREATE PROCEDURE `FIND_ALL_COURSES` (IN subjNo int)
BEGIN
	IF subjNo IS NOT NULL THEN
    /* Fetch all courses */
		SELECT C.*, S.subj_abv, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConflicting'
		FROM Courses C
		JOIN Subjects S ON S.subj_no=C.subj_no
        LEFT JOIN Prerequisites P ON C.course_no=P.course_no
		LEFT JOIN ConflictingCourses K ON C.course_no=K.course_no
        WHERE C.subj_no=subjNo
        GROUP BY C.course_no
        ORDER BY C.course_no;
	ELSE
	/* Fetch all courses per subject */
		SELECT C.*, S.subj_abv, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConflicting'
		FROM Courses C
		JOIN Subjects S ON S.subj_no=C.subj_no
        LEFT JOIN Prerequisites P ON C.course_no=P.course_no
		LEFT JOIN ConflictingCourses K ON C.course_no=K.course_no
        GROUP BY C.course_no
        ORDER BY C.course_no;
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_ALL_COURSE_PREREQ` (IN courseNo int)
BEGIN
	SELECT P.prereq_no, P.sec_course_no AS course_no, CONCAT(S.subj_abv,' ',C.course_level) AS course, P.prereq_group AS `group`
	FROM Prerequisites P
	JOIN Courses C on P.sec_course_no = C.course_no
	JOIN Subjects S on C.subj_no = S.subj_no
	WHERE P.course_no=courseNo
	ORDER BY `group`, course_no;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_ALL_CONFLICTING_COURSES` (IN courseNo int)
BEGIN
	SELECT K.conf_no, K.sec_course_no AS course_no, CONCAT(S.subj_abv,' ',C.course_level) AS course
	FROM ConflictingCourses K
	JOIN Courses C on K.sec_course_no = C.course_no
	JOIN Subjects S on C.subj_no = S.subj_no
	WHERE K.course_no=courseNo
	ORDER BY course_no;
END; $$
DELIMITER ;

/* semId = semester ID, subj = subject abreviation (4 letters), range = less or greater than */
DELIMITER $$
CREATE PROCEDURE `FIND_ALL_CLASSES`(IN semId int, IN subj varchar(4), IN `range` varchar(7), IN `level` int, IN opened boolean)
BEGIN
	/* K = classes, C = courses, S = subjects, Z = semesters */
    SELECT K.class_no, CONCAT(S.subj_abv,' ',C.course_level) AS course_code, C.course_name, K.instr_name, 
	CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS semester, K.start_time, K.end_time, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened
	FROM Classes K
	JOIN Courses C on C.course_no=K.Course_no
	JOIN Subjects S on S.subj_no=C.subj_no
	JOIN Semesters Z on Z.sem_no=K.sem_no
	WHERE K.sem_no = semId
		/* If a subject is specified return all classes with given subject */
		AND (subj IS NULL OR C.subj_no=(SELECT subj_no FROM Subjects WHERE UPPER(subj_abv)=UPPER(subj)))
        /* If a range and level is provided, */
        AND ((`range` IS NULL AND `level` IS NULL) OR (
				UPPER(`range`) NOT IN ('GREATER','LESS')
				/* return all classes with courses >= than the level */
				OR (UPPER(`range`)='GREATER' AND C.course_level >= `level`)
				/* or all classes with courses <= the level */
				OR (UPPER(`range`)='LESS' AND C.course_level <= `level`)
				)
			)
		/* If opened return all opened classes else include closed classes */
		AND (opened<>TRUE OR K.opened=TRUE);
END ;
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_CLASS_BY_ID` (IN classId int)
BEGIN
	SELECT K.class_no, CONCAT(S.subj_abv,' ',C.course_level) AS course_code, C.course_name, K.instr_name, 
	CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS semester, K.start_time, K.end_time, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened
	FROM Classes K
	JOIN Courses C on C.course_no=K.Course_no
	JOIN Subjects S on S.subj_no=C.subj_no
	JOIN Semesters Z on Z.sem_no=K.sem_no
	WHERE K.class_no = classId;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_COURSE_BY_ID` (IN courseId int)
BEGIN
	SELECT C.*, S.subj_abv, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConflicting'
	FROM Courses C
	JOIN Subjects S ON S.subj_no=C.subj_no
	LEFT JOIN Prerequisites P ON C.course_no=P.course_no
	LEFT JOIN ConflictingCourses K ON C.course_no=K.course_no
	WHERE C.course_no=courseId
	GROUP BY C.course_no;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `FIND_COURSE_FROM_CLASS` (IN classId int)
BEGIN
	CALL FIND_COURSE_BY_ID((SELECT course_no FROM Classes WHERE class_no=classId));
END; $$
DELIMITER ;