DELIMITER $$
CREATE PROCEDURE `get_prereq_by_course_abv`(IN course_subject varchar(4), IN course_level int)
BEGIN
	SELECT `Prerequisits`.course_no, sec_course_no, dept_abv, course_level, course_name, units, prereq_group
	FROM `Prerequisits`
	JOIN `Courses` ON `Prerequisits`.sec_course_no=`Courses`.course_no
	JOIN `AcadDept` ON `Courses`.dept_no=`AcadDept`.dept_no
	WHERE `Prerequisits`.course_no = (SELECT course_no FROM Courses WHERE dept_abv = course_subject AND Courses.course_level = course_level)
	ORDER BY `Prerequisits`.sec_course_no;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `get_conflicting_courses_by_course_abv`(IN course_subject varchar(4), IN course_level int)
BEGIN
	SELECT sec_course_no, dept_abv, course_level, course_name, units
	FROM `conflictingcourses`
	JOIN `Courses` ON `conflictingcourses`.sec_course_no=`Courses`.course_no
	JOIN `AcadDept` ON `Courses`.dept_no=`AcadDept`.dept_no
	WHERE `conflictingcourses`.course_no = (SELECT course_no FROM Courses WHERE dept_abv = course_subject AND Courses.course_level = course_level)
	ORDER BY `conflictingcourses`.course_no;
END; $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `get_courses_by_subject` (IN course_subject varchar(4))
BEGIN
	IF course_subject IS NOT NULL THEN
		SELECT course_no, dept_abv, course_level, course_name, units
		FROM `Courses`
		JOIN `AcadDept` ON `Courses`.dept_no=`AcadDept`.dept_no
        WHERE `Courses`.dept_no = (SELECT DISTINCT Courses.dept_no
								FROM Courses JOIN AcadDept ON Courses.dept_no=AcadDept.dept_no
								WHERE dept_abv = course_subject)
		ORDER BY dept_abv, course_level;
	ELSE
		SELECT course_no, dept_abv, course_level, course_name, units
		FROM `Courses`
		JOIN `AcadDept` ON `Courses`.dept_no=`AcadDept`.dept_no
		ORDER BY dept_abv, course_level;
	END IF;
END; $$
DELIMITER;

DELIMITER $$
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

select CONCAT('Charles', '.', 'Brown', 10000%100, '@ENS.STEM.EDU');