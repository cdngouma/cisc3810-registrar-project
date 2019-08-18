/* PREREQUISITS */
DELIMITER $$
CREATE TRIGGER `ins_prereq` BEFORE INSERT ON `Prerequisits`
FOR EACH ROW
BEGIN
	IF
		/**	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
		/*	and check that inserted course is not a prerequisit of inserted prerequisit
		/*	return 0 if constraint is violated. return 1 otherwise
		**/
		(SELECT 1 FROM Courses C1, Courses C2
		WHERE C1.course_no=NEW.course_no AND C2.course_no=NEW.sec_course_no
		AND C1.subj_no=C2.subj_no && C2.course_level > C1.course_level) = 1 OR
		(SELECT 1 FROM Prerequisits WHERE course_no=NEW.sec_course_no AND sec_course_no=NEW.course_no) = 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ambiguous pre-requisit relationship: for columns \'course_no\' and \'sec_course_no\'';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_prereq` BEFORE UPDATE ON `Prerequisits`
FOR EACH ROW
BEGIN
	/* prevent user to update column 'course_no' */
	IF NEW.course_no IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Column cannot be updated: for column \'course_no\'';
	ELSEIF
		/**	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
		/*	and check that inserted course is not a prerequisit of inserted prerequisit
		/*	return 0 if constraint is violated. return 1 otherwise
		**/
		(SELECT 1 FROM Courses C1, Courses C2
		WHERE C1.course_no=OLD.course_no AND C2.course_no=NEW.sec_course_no
		AND C1.subj_no=C2.subj_no && C2.course_level > C1.course_level) = 1 OR
		(SELECT 1 FROM Prerequisits WHERE course_no=NEW.sec_course_no AND sec_course_no=OLD.course_no) = 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Ambiguous pre-requisit relationship: for columns \'course_no\' and \'sec_course_no\'';
	END IF;
END; $$
DELIMITER ;

/*
DELIMITER $$
CREATE TRIGGER `ins_class` BEFORE INSERT ON `Classes`
FOR EACH ROW
BEGIN
	IF (SELECT 1 FROM Classes C 
		WHERE NEW.start_time >= C.start_time AND NEW.start_time <= C.end_time 
        OR NEW.end_time >= C.start_time AND NEW.end_time <= C.end_time
        AND 
END;
DELIMITER ;
*/