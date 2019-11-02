-- Instructor
delimiter $$
create trigger `ins_instructor` before insert on `instructors`
for each row
begin
	set NEW.id = 50 * 1000000 + NEW.subject_id * 10000 + COALESCE((select COUNT(id) from instructors), 0) + 1;
end; $$
delimiter ;

-- Prerequisites
delimiter $$
create trigger `ins_prerequisite` before insert on `prerequisites`
for each row
begin
	IF
		/*	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
			and check that inserted course is not a prerequisit of inserted prerequisit
			return 0 if constraint is violated. return 1 otherwise
		*/
		(select 1 from Courses C1, Courses C2
		where C1.course_id = NEW.course_id and C2.course_id = NEW.prereq_id
		and C1.subject_id = C2.subject_id && C2.course_level > C1.course_level) = 1 or
		(select 1 from prerequisites where course_id = NEW.prereq_id and prereq_id = NEW.course_id) = 1 THEN
		SIGNAL SQLSTATE '45001' set MESSAGE_TEXT = 'Ambiguous prerequisite relationship: for columns \'course_id\' and \'prereq_id\'';
	end IF;
end; $$
delimiter ;

delimiter $$
create trigger `upd_prerequisite` before update on `Prerequisites`
for each row
begin
	/* prevent user to update column 'course_id' */
	IF NEW.course_id IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'Column \'course_id\' cannot be updated';
	ELSEIF
		/*	check that inserted course level is larger than inserted prerequisit [course] level if both have the same subject
			and check that inserted course is not a prerequisit of inserted prerequisit
			return 0 if constraint is violated. return 1 otherwise
		*/
		(select 1 from Courses C1, Courses C2
		where C1.course_id = OLD.course_id and C2.course_id = NEW.prereq_id
		and C1.subject_id = C2.subject_id && C2.course_level > C1.course_level) = 1 or
		(select 1 from Prerequisites where course_id = NEW.prereq_id and prereq_id = OLD.course_id) = 1 THEN
		SIGNAL SQLSTATE '45001' set MESSAGE_TEXT = 'Ambiguous prerequisite relationship: for columns \'course_id\' and \'prereq_id\'';
	end IF;
end; $$
delimiter ;

-- Classes
delimiter $$
create trigger `ins_class` before insert on `classes`
for each row
begin
	-- Look for overlaping classes by instructor or course (id):
    -- first find two classes with overlaping time slot and same course id or instructor
    IF (select 1 from classes C1, classes C2 where C1.id = NEW.id 
    and C1.sem_id = C2.sem_id and (C1.course_id = C2.course_id or C1.instructor_id = C2.instructor_id) 
    and C1.start_time <= C2.end_time and C1.end_time >= C2.start_time
    -- find the intersecton between the meeting days of both classes
    and (select 1 from meeting_days M1, meeting_days M2 where M1.class_id = C1.id 
    and M1.class_id = M2.class_id and M1.class_day = M2.class_day) is not null) is not null THEN
		SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'Overlaping classes time slot';
	end IF;
end; $$
delimiter ;

-- Students
delimiter $$
create trigger `ins_instructor` before insert on `instructors`
for each row
begin
	-- SET new id
	set NEW.id = 50 * 1000000 + NEW.subject_id * 10000 + COALESCE((select COUNT(id) from instructors), 0) + 1;
end; $$
delimiter ;