/* PREREQUISITS */
DELIMITER $$
CREATE TRIGGER `ins_prereq` BEFORE INSERT ON `Prerequisites`
FOR EACH ROW
BEGIN
	IF
		/**	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
		/*	and check that inserted course is not a prerequisit of inserted prerequisit
		/*	return 0 if constraint is violated. return 1 otherwise
		**/
		(SELECT 1 FROM Courses C1, Courses C2
		WHERE C1.course_id = NEW.course_id AND C2.course_id = NEW.prereq_id
		AND C1.subject_id = C2.subject_id && C2.course_level > C1.course_level) = 1 OR
		(SELECT 1 FROM Prerequisites WHERE course_id = NEW.prereq_id AND prereq_id = NEW.course_id) = 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Ambiguous prerequisite relationship: for columns \'course_id\' and \'prereq_id\'';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_prereq` BEFORE UPDATE ON `Prerequisites`
FOR EACH ROW
BEGIN
	/* prevent user to update column 'course_id' */
	IF NEW.course_id IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Column \'course_id\' cannot be updated';
	ELSEIF
		/**	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
		/*	and check that inserted course is not a prerequisit of inserted prerequisit
		/*	return 0 if constraint is violated. return 1 otherwise
		**/
		(SELECT 1 FROM Courses C1, Courses C2
		WHERE C1.course_id = OLD.course_id AND C2.course_id = NEW.prereq_id
		AND C1.subject_id = C2.subject_id && C2.course_level > C1.course_level) = 1 OR
		(SELECT 1 FROM Prerequisites WHERE course_id = NEW.prereq_id AND prereq_id = OLD.course_id) = 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Ambiguous prerequisite relationship: for columns \'course_id\' and \'prereq_id\'';
	END IF;
END; $$
DELIMITER ;