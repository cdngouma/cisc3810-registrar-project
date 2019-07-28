DELIMITER $$
CREATE PROCEDURE `find_course_by_subject`(IN courseSubject varchar(4))
BEGIN
	IF courseSubject IS NULL THEN
		SELECT course_no, subject_abv, course_level, course_name, units, course_desc
        FROM Courses
        JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no
        ORDER BY subject_abv, course_level;
	ELSE
		SELECT course_no, subject_abv, course_level, course_name, units, course_desc
		FROM Courses
		JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no
		WHERE Courses.subject_no = (SELECT DISTINCT Courses.subject_no
			FROM Courses JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no WHERE subject_abv=courseSubject)
		ORDER BY course_level;
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `find_course_by_id` (IN id int)
BEGIN
	SELECT course_no, subject_abv, course_level, course_name, units, course_desc 
	FROM Courses
	JOIN CourseSubjects ON Courses.subject_no = CourseSubjects.subject_no
	WHERE Courses.course_no = id;
END; $$
DELIMITER ;

CALL find_course_by_id(016);
CALL find_course_by_subject('CISC');

DELIMITER $$
CREATE PROCEDURE `find_class` (IN courseSubject varchar(4), IN courseLevel int, IN lvl_range varchar(8))

BEGIN
	
	IF ref IS NULL THEN
		SELECT course_no AS courseno, subject_abv, course_level, course_name, units
        FROM Courses
        JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no;
	
	ELSEIF REGEXP_LIKE(ref, '^([0-9]+)') > 0 THEN
		SELECT course_no AS courseno, subject_abv, course_level, course_name, units
        FROM Courses
        JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no
        WHERE Courses.course_no = ref;
	
	ELSE
		SELECT course_no AS courseno, subject_abv, course_level, course_name, units
		FROM Courses
		JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no
		WHERE Courses.subject_no = (SELECT DISTINCT Courses.subject_no
			FROM Courses JOIN CourseSubjects ON Courses.subject_no=CourseSubjects.subject_no WHERE subject_abv = ref);
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `find_prerequisit`(IN courseno int)
BEGIN
	SELECT `Prerequisits`.sec_course_no AS courseno, subject_abv, course_level, course_name
	FROM `Prerequisits`
	JOIN `Courses` ON `Prerequisits`.sec_course_no=`Courses`.course_no
	JOIN `CourseSubjects` ON `Courses`.subject_no=`CourseSubjects`.subject_no
	WHERE `Prerequisits`.course_no = courseno;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `find_conf_courses`(IN courseno int)
BEGIN
	SELECT sec_course_no AS courseno, subject_abv, course_level, course_name
	FROM `ConflictingCourses`
	JOIN `Courses` ON `ConflictingCourses`.sec_course_no=`Courses`.course_no
	JOIN `CourseSubjects` ON `Courses`.subject_no=`CourseSubjects`.subject_no
	WHERE `ConflictingCourses`.course_no = courseno;
END; $$
DELIMITER ;

/*DELIMITER $$
CREATE PROCEDURE `addStudent`(
	IN occupation varchar(10),
    IN first_name varchar(100),
    IN last_name varchar(100),
    IN gender varchar(7),
    IN dob date,
    IN degree varchar(7),
    IN major varchar(100),
    IN division char
    )
BEGIN
	DECLARE user_no int;
    DECLARE username varchar(250);
    DECLARE user_password varchar(100);
    SET user_no = (select MAX(user_no) from `Users`);
    SET username = (select CONCAT(firstname, '.', lastname, userno%100, '@STEM.LOGIN'));
    SET user_password = PASSWORD(CONCAT(MONTH(dob), DAY(dob), userno%1000);
    
    insert into `Users` values(userno, UPPER(username), user_password, UPPER(occupation));
    insert into `Students` values(userno, UPPER(firstname), UPPER(lastname), UPPER(gender), dob, UPPER(degree), UPPER(major), UPPER(division), transfCr, NULL, NULL, NULL, CURRENT_DATE());
END;
DELIMITER ;

CALL addStudent('STUDENT', 'Chrys', 'Ngouma', 'MALE', '1996-07-28', 'BS', 'COMPUTER SCIENCE', 'U', 50);
CALL addStudent('STUDENT', 'Alicia', 'Lynch', 'FEMALE', '1998-11-06', 'BS', 'COMPUTER INFORMATION SYSTEMS', 'U', NULL);

DELIMITER $$
CREATE PROCEDURE `addInstructor`(
	IN user_password varchar(100),
	IN occupation varchar(10),
    IN firstname varchar(100),
    IN lastname varchar(100),
    IN gender varchar(7),
    IN dob date,
    IN dept_abv varchar(4)
    )
BEGIN
	DECLARE userno int;
    DECLARE username varchar(250);
    DECLARE deptno int;
    SET deptno = (select deptno from AcadDept where AcadDept.dept_abv = dept_abv);
    SET userno = (select MAX(userno) from `Users`);
    SET username = (select CONCAT(firstname, '.', lastname, userno%100, '@ENS.STEM.EDU'));
    
    insert into `Users` values(userno, UPPER(username), user_password, UPPER(occupation));
    insert into `Instructors` values(userno, deptno, UPPER(firstname), UPPER(lastname), UPPER(gender));
END;
DELIMITER ;

CALL addInstructor('test1234', 'INSTRUCTOR', 'Charles', 'Brown', 'male', '1983-08-16', 'CISC');

SHOW PROCEDURE STATUS WHERE db = 'cisc3810@registrar';

select CONCAT('Charles', '.', 'Brown', 10000%100, '@ENS.STEM.EDU');*/