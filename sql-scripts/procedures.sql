/**
/* TODO: Optimize if-else statement
**/
DELIMITER $$
CREATE PROCEDURE `find_all_classes`(IN subjId int, IN range_dir varchar(8), IN lvl int, IN opened boolean)
BEGIN
	/* retrun courses with level greather/lower than given level */
	IF range_dir IS NOT NULL AND lvl IS NOT NULL THEN
		IF range_dir = 'GREATHER' THEN
			IF subjId IS NOT NULL THEN
                SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
				FROM Courses C 
				LEFT JOIN Prerequisits P ON C.course_no=P.course_no
				LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
				WHERE course_level > lvl AND subj_no=subjId
				GROUP BY C.course_no;
			ELSE
                SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
				FROM Courses C 
				LEFT JOIN Prerequisits P ON C.course_no=P.course_no
				LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
				WHERE course_level > lvl
				GROUP BY C.course_no;
			END IF;
		ELSEIF range_dir = 'LESS' THEN
			IF subjId IS NOT NULL THEN
                SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
				FROM Courses C 
				LEFT JOIN Prerequisits P ON C.course_no=P.course_no
				LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
				WHERE course_level < lvl AND subj_no=subjId
				GROUP BY C.course_no;
			ELSE
                SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
				FROM Courses C 
				LEFT JOIN Prerequisits P ON C.course_no=P.course_no
				LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
				WHERE course_level < lvl
				GROUP BY C.course_no;
			END IF;
		END IF;
        
    /* return all courses of the given level and subject */
    ELSEIF subjId IS NOT NULL AND range_dir IS NULL AND lvl IS NOT NULL THEN
        SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
		FROM Courses C 
		LEFT JOIN Prerequisits P ON C.course_no=P.course_no
		LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
        WHERE course_level = lvl AND subj_no = subjId
		GROUP BY C.course_no;
        
	/* return all courses of the given level */
	ELSEIF subjId IS NULL AND range_dir IS NULL AND lvl IS NOT NULL THEN
		SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
		FROM Courses C 
		LEFT JOIN Prerequisits P ON C.course_no=P.course_no
		LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
        WHERE course_level = lvl
		GROUP BY C.course_no;
        
     /* return all courses corresponding to a given subject */    
	ELSEIF subjId IS NOT NULL AND range_dir IS NULL AND lvl IS NULL THEN
        SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
		FROM Courses C 
		LEFT JOIN Prerequisits P ON C.course_no=P.course_no
		LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
        WHERE subj_no = subjId
		GROUP BY C.course_no;
        
	/* return all courses if no filter specified */
	ELSEIF subjId IS NULL AND range_dir IS NULL AND lvl IS NULL THEN
		SELECT C.*, COUNT(P.prereq_no) AS 'numPrereq', COUNT(K.conf_no) AS 'numConf'
		FROM Courses C 
		LEFT JOIN Prerequisits P ON C.course_no=P.course_no
		LEFT JOIN Conflicting_Courses K ON C.course_no=K.course_no
		GROUP BY C.course_no
        ORDER BY subj_no, course_level;
	END IF;
END; $$
DELIMITER ;

/*
SELECT K.class_no, K.course_no, CONCAT(S.subj_abv,' ',C.course_level,' - ',C.course_name) AS courseName, K.instr_name, 
	CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS sem, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened, K.start_time, K.end_time
FROM Classes K
JOIN Courses C on C.course_no=K.course_no
JOIN Subjects S on S.subj_no=C.subj_no
JOIN Semesters Z on Z.sem_no=K.sem_no
WHERE K.sem_no=7 AND K.opened=TRUE;

SELECT K.class_no, K.course_no, CONCAT(S.subj_abv,' ',C.course_level,' - ',C.course_name) AS courseName, K.instr_name, 
	CONCAT(Z.sem_name,' ',YEAR(Z.start_date)) AS sem, K.room, K.capacity, K.num_enrolled, K.`mode`, K.opened, K.start_time, K.end_time
FROM Classes K
JOIN Courses C on C.course_no=K.course_no
JOIN Subjects S on S.subj_no=C.subj_no
JOIN Semesters Z on Z.sem_no=K.sem_no
WHERE K.sem_no=7
	AND K.opened=TRUE
	AND C.course_level <= 3000;
    AND C.subj_no = 3; 
/*
CALL FIND_ALL_COURSES(null, null, null);
CALL FIND_ALL_COURSES(null, 'GREATHER', 2000);
CALL FIND_ALL_COURSES(null, 'LESS', 2000);
CALL FIND_ALL_COURSES(3, 'GREATHER', 4000);
CALL FIND_ALL_COURSES(null, null, 3130);
*/