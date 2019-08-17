create table `Semesters`(
	sem_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    sem_name varchar(7) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);

ALTER TABLE `Semesters` ADD CONSTRAINT `unq_semester` UNIQUE(start_date, end_date);
ALTER TABLE `Semesters` ADD CONSTRAINT `ck_semester_val` CHECK(UPPER(sem_name) IN ('FALL','SPRING','SUMMER','WINTER'));
ALTER TABLE `Semesters` ADD CONSTRAINT `ck_semester_period` CHECK(end_date > start_date);

create table `Subjects`(
	subj_no int(2) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subj_name varchar(100) UNIQUE NOT NULL,
    subj_abv varchar(4) UNIQUE NOT NULL
);

ALTER TABLE `Subjects` ADD CONSTRAINT `ck_subj_abv_len` CHECK(LENGTH(subj_abv) = 4);

create table `Courses`(
	course_no int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subj_no int(2) UNSIGNED NOT NULL,
    course_level int(4) NOT NULL,
    course_name varchar(100),
    units double(3,1) UNSIGNED NOT NULL,
    course_desc varchar(600)
);

ALTER TABLE `Courses` ADD FOREIGN KEY(subj_no) REFERENCES `Subjects`(subj_no) ON DELETE CASCADE;
ALTER TABLE `Courses` ADD CONSTRAINT `unq_course` UNIQUE(subj_no, course_level);

create table `Prerequisits`(
	prereq_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_no int(5) UNSIGNED NOT NULL,
    sec_course_no int(5) UNSIGNED NOT NULL,
    prereq_group int(1) UNSIGNED
);

ALTER TABLE `Prerequisits` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisits` ADD FOREIGN KEY(sec_course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisits` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, sec_course_no);

create table `Conflicting_Courses`(
	conf_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_no int(5) UNSIGNED NOT NULL,
    sec_course_no int(5) UNSIGNED NOT NULL
);

ALTER TABLE `Conflicting_Courses` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no);
ALTER TABLE `Conflicting_Courses` ADD FOREIGN KEY(sec_course_no) REFERENCES `Courses`(course_no);
ALTER TABLE `Conflicting_Courses` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, sec_course_no);

create table `Classes`(
	class_no int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_no int(5) UNSIGNED NOT NULL,
    instr_name varchar(255),
    /* class details */
    sem_no int(3) UNSIGNED NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,
    `mode` varchar(10) NOT NULL,
    room varchar(7),
    capacity int(3) UNSIGNED NOT NULL,
    num_enrolled int(3) UNSIGNED NOT NULL,
    opened boolean NOT NULL DEFAULT TRUE
);

/* SU19 */
insert into Classes values(NULL,2,'Kelly Fox',6,'8:00','09:10','IN-PERSON','BH 409',22,22,FALSE);
insert into Classes values(NULL,2,'Lydia Simpson',6,'11:50','13:00','IN-PERSON','BH 345',22,13,TRUE);
/* FA19 */
insert into Classes values(NULL,8,'Ronald Callahan',7,'18:30','20:35','IN-PERSON','IA 214',30,23,TRUE);
insert into MeetingDays values(NULL,3,1); insert into MeetingDays values(NULL,3,3);
insert into Classes values(NULL,14,'Dar Stein',7,'18:00','22:00','IN-PERSON','IA 214',40,37,TRUE);
insert into MeetingDays values(NULL,4,2);
insert into Classes values(NULL,14,'Jorah Dukes',7,'09:05','10:55','IN-PERSON','IN 414',40,39,TRUE);
insert into MeetingDays values(NULL,5,0); insert into MeetingDays values(NULL,5,2);
insert into Classes values(NULL,14,'Jorah Dukes',7,'09:05','10:55','IN-PERSON','IN 414',40,39,TRUE);
insert into MeetingDays values(NULL,5,0); insert into MeetingDays values(NULL,5,2);

select 	k.class_no, 
		concat(s.subj_abv,' ',c.course_level) as course_shr,
		c.course_name,
        k.instr_name,
        z.start_date,
        z.end_date,
        k.room,
        k.capacity,
        k.num_enrolled,
        k.`mode`,
        k.opened
from classes k
join courses c on c.course_no=k.course_no
join subjects s on s.subj_no=c.subj_no
join semesters z on z.sem_no=k.sem_no;


ALTER TABLE `Classes` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Classes` ADD FOREIGN KEY(sem_no) REFERENCES `Semesters`(sem_no) ON DELETE CASCADE;
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_time` CHECK(start_time < end_time);
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_mode` CHECK(UPPER(`mode`) IN('IN-PERSON','ONLINE'));
ALTER TABLE `Classes` ADD CONSTRAINT `ck_room_fmt` CHECK(REGEXP_LIKE(UPPER(room), '^([A-Z]{2}) ([0-9]{3,4})$'));
ALTER TABLE `Classes` ADD CONSTRAINT `ck_room_capacity` CHECK(num_enrolled <= capacity);

create table `MeetingDays`(
	md_no int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_no int(5) UNSIGNED NOT NULL,
    m_day int(1) UNSIGNED NOT NULL
);

ALTER TABLE `MeetingDays` ADD CONSTRAINT `unq_meeting_day` UNIQUE(m_day, class_no);

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