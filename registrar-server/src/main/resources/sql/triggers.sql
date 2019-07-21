/* TRIGGERS */

/* SYSTEM */
/* users */
DELIMITER $$
CREATE TRIGGER `ins_users` BEFORE INSERT ON `Users`
FOR EACH ROW
BEGIN
	IF UPPER(NEW.occupation) NOT IN('STUDENT', 'INSTRUCTOR', 'STAFF') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'occupation\'';
	END IF;
    IF REGEXP_LIKE(UPPER(NEW.username), '^([A-Z]+).([A-Z]+)([0-9]{2})@(STEM.LOGIN)$') <> 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Invalid format for key \'username\'';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_users` BEFORE UPDATE ON `Users`
FOR EACH ROW
BEGIN
	IF UPPER(NEW.occupation) NOT IN('STUDENT', 'INSTRUCTOR', 'STAFF') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'occupation\'';
	END IF;
    IF REGEXP_LIKE(UPPER(NEW.username), '^([A-Z]+).([A-Z]+)([0-9]{2})@(STEM.LOGIN)$') <> 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Invalid format for key \'username\'';
	END IF;
END; $$
DELIMITER ;

/* user devices */
DELIMITER $$
CREATE TRIGGER `ins_user_device` BEFORE INSERT ON `User_Devices`
FOR EACH ROW
BEGIN
	IF REGEXP_LIKE(NEW.ip_address, '^([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3})$') <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'ip_address\'';
	END IF;
END; $$

CREATE TRIGGER `upd_user_device` BEFORE UPDATE ON `User_Devices`
FOR EACH ROW
BEGIN
	IF REGEXP_LIKE(NEW.ip_address, '^([0-9]{1,3}).([0-9]{1,3}).([0-9]{1,3})$') <> 1 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'ip_address\'';
	END IF;
END; $$
DELIMITER ;

/* REGISTRAR */
/* acaddemic departments */
DELIMITER $$
CREATE TRIGGER `ins_academic_dept` BEFORE INSERT ON `AcadDept`
FOR EACH ROW
BEGIN
	IF LENGTH(NEW.dept_abv) <> 4 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Four(4) letters required for key \'dept_abv\'';
    END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_academic_dept` BEFORE UPDATE ON `AcadDept`
FOR EACH ROW
BEGIN
	IF LENGTH(NEW.dept_abv) <> 4 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Four(4) letters required for key \'dept_abv\'';
    END IF;
END; $$
DELIMITER ;

/* semesters */
DELIMITER $$
CREATE TRIGGER `ins_enrollment_period` BEFORE INSERT ON `EnrollmentPeriods`
FOR EACH ROW
BEGIN
	IF UPPER(NEW.semester) NOT IN('FALL', 'SPRING', 'SUMMER', 'WINTER') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'semester\'';
	END IF;
    IF NEW.start_date >= NEW.end_date THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Conflicting date(s) for keys \'start_date\' and \'end_date\'';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_enrollment_period` BEFORE UPDATE ON `EnrollmentPeriods`
FOR EACH ROW
BEGIN
	IF UPPER(NEW.semester) NOT IN('FALL', 'SPRING', 'SUMMER', 'WINTER') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'semester\'';
	END IF;
	
    IF (NEW.start_date >= NEW.end_date) OR (NEW.end_date IS NULL AND NEW.start_date >= OLD.end_date)
		OR (NEW.start_date IS NULL AND OLD.start_date > NEW.end_date) THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Conflicting date(s) for keys \'start_date\' and \'end_date\'';
	END IF;
END; $$
DELIMITER ;

/* courses */
DELIMITER $$
CREATE TRIGGER `ins_course` BEFORE INSERT ON `Courses`
FOR EACH ROW
BEGIN
    IF NEW.units < 1.0 OR NEW.units > 5.0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'units\'';
    END IF;
    IF NEW.course_level < 1000 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Minimum 1000 for key \'course_level\'';
    END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_course` BEFORE UPDATE ON `Courses`
FOR EACH ROW
BEGIN
	IF NEW.units < 1.0 OR NEW.units > 5.0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid value for key \'units\'';
    END IF;
    IF NEW.course_level < 1000 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Minimum 1000 for key \'course_level\'';
    END IF;
END; $$
DELIMITER ;

/* course prerequisists */
DELIMITER $$
CREATE TRIGGER `ins_prereq` BEFORE INSERT ON `Prerequisits`
FOR EACH ROW
BEGIN
	/* verify that inserted course (courseno) is not a prerequisit of inserted prerequisit (prereqno) */
	IF (select case when C1.dept_no = C2.dept_no && C2.course_level >= C1.course_level then 0
					when (select 1 from Prerequisits where course_no=NEW.sec_course_no AND sec_course_no=NEW.course_no) = 1 then 0
					else 1 end
        from Courses C1, Courses C2
        where C1.course_no = NEW.course_no AND C2.course_no = NEW.sec_course_no) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ambiguous pre-requisit relationship for key \'course_no\' and \'sec_course_no\'';
	END IF;
END; $$
DELIMITER ;

/* TODO: Improve trigger implementation for updating prereq_no */
DELIMITER $$
CREATE TRIGGER `upd_prereq` BEFORE UPDATE ON `Prerequisits`
FOR EACH ROW
BEGIN
	IF (select case when C1.dept_no = C2.dept_no && C2.course_level >= C1.course_level then 0
					when (select 1 from Prerequisits where course_no=NEW.sec_course_no AND sec_course_no=NEW.course_no) = 1 then 0
					else 1 end
        from Courses C1, Courses C2
        where C1.course_no = NEW.course_no AND C2.course_no = NEW.sec_course_no) = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ambiguous pre-requisit relationship for key \'course_no\' and \'sec_course_no\'';
	END IF;
END; $$
DELIMITER ;

/* class sections */
DELIMITER $$
CREATE TRIGGER `ins_section` BEFORE INSERT ON `Sections`
FOR EACH ROW
BEGIN
	IF NEW.start_time >= NEW.end_time THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Conflicting time: for columns <start_time, end_time>';
	END IF;
    IF NEW.days='' OR REGEXP_LIKE(UPPER(NEW.days), '^(MO|)(TU|)(WE|)(TH|)(FR|)(SA|)(SU|)$') < 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Invalid days: for column days';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_section` BEFORE UPDATE ON `Sections`
FOR EACH ROW
BEGIN
	IF (NEW.start_time >= NEW.end_time) OR (NEW.end_time IS NULL AND NEW.start_time >= OLD.end_time) OR (NEW.start_time IS NULL AND OLD.start_time > NEW.end_time) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Conflicting time: for columns <start_time, end_time>';
	END IF;
    IF NEW.days='' OR REGEXP_LIKE(UPPER(NEW.days), '^(MO|)(TU|)(WE|)(TH|)(FR|)(SA|)(SU|)$') < 1 THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Invalid days: for column days';
	END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `ins_class` BEFORE INSERT ON `Classes`
FOR EACH ROW
BEGIN
	IF NEW.building_no IS NULL OR NEW.room IS NULL THEN 
		SET NEW.room_no = NULL;
        SET NEW.building = NULL;
    END IF;
    IF NEW.class_mode NOT IN('IN-PERSON', 'ONLINE', 'HYBRID') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid class mode: for column class_mode';
	END IF;
    IF NEW.class_type NOT IN('LECTURE', 'LAB') THEN
		SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Invalid class type: for column class_type';
	END IF;
END $$
DELIMITER ;

/* PEOPLE */
/* instructors */
DELIMITER $$
CREATE TRIGGER `ins_instructor` BEFORE INSERT ON `Instructors`
FOR EACH ROW
BEGIN
	IF NEW.gender NOT IN('MALE', 'FEMALE') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid gender: for column gender';
	END IF;
END; $$

CREATE TRIGGER `upd_instructor` BEFORE UPDATE ON `Instructors`
FOR EACH ROW
BEGIN
	IF NEW.gender NOT IN('MALE', 'FEMALE') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid gender: for column gender';
	END IF;
END; $$
DELIMITER ;

/* students */
DELIMITER $$
CREATE TRIGGER `ins_student` BEFORE INSERT ON `Students`
FOR EACH ROW
BEGIN
	IF NEW.admission_date IS NULL THEN
		/* set default admission date to current date */
		SET NEW.admission_date = CURRENT_DATE();
    END IF;
	IF NEW.gender NOT IN('MALE', 'FEMALE') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid gender: for column gender';
	END IF;
    IF NEW.dob >= CURRENT_DATE() OR NEW.admission_date > CURRENT_DATE() THEN
		SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'Ambiguous date: for columns <dob, admission_date>';
    END IF;
    IF NEW.division NOT IN('U', 'G') THEN SIGNAL SQLSTATE '45003' SET MESSAGE_TEXT = 'Invalid division: for column division';
    END IF;
    IF NEW.transf_cr < 50 OR NEW.transf_cr > 60 THEN SIGNAL SQLSTATE '45004' SET MESSAGE_TEXT = 'Transferred credits range is [50-60]: for column transf_cr';
    END IF;
    IF NEW.gpa > 4.00 THEN SIGNAL SQLSTATE '45005' SET MESSAGE_TEXT = 'Maximum GPA is 4.0: for column gpa';
    END IF;
END; $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER `upd_student` BEFORE UPDATE ON `Students`
FOR EACH ROW
BEGIN
	IF NEW.gender NOT IN('MALE', 'FEMALE') THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid gender: for column gender';
	END IF;
    IF NEW.dob >= CURRENT_DATE() OR NEW.admission_date > CURRENT_DATE() THEN
		SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'Ambiguous date: for columns <dob, admission_date>';
    END IF;
    IF NEW.division NOT IN('U', 'G') THEN SIGNAL SQLSTATE '45003' SET MESSAGE_TEXT = 'Invalid division: for column division';
    END IF;
    IF NEW.transf_cr < 50 OR NEW.transf_cr > 60 THEN SIGNAL SQLSTATE '45004' SET MESSAGE_TEXT = 'Transferred credits range is [50-60]: for column transf_cr';
    END IF;
    IF NEW.gpa > 4.00 THEN SIGNAL SQLSTATE '45005' SET MESSAGE_TEXT = 'Maximum GPA is 4.0: for column gpa';
    END IF;
END; $$
DELIMITER ;