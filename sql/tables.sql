create table `Semesters`(
	id int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    semester varchar(7) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);

ALTER TABLE `Semesters` ADD CONSTRAINT `unq_semester` UNIQUE(start_date, end_date);
ALTER TABLE `Semesters` ADD CONSTRAINT `ck_semester` CHECK(UPPER(semester) IN ('FALL','SPRING','SUMMER','WINTER'));
ALTER TABLE `Semesters` ADD CONSTRAINT `ck_semester_period` CHECK(end_date > start_date);

create table `Subjects`(
	id int(2) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject_name varchar(100) UNIQUE NOT NULL,
    subject_short varchar(4) UNIQUE NOT NULL
);

ALTER TABLE `Subjects` ADD CONSTRAINT `ck_subject_short_length` CHECK(LENGTH(subject_short) = 4);

create table `Courses`(
	id int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject_id int(2) UNSIGNED NOT NULL,
    course_level int(4) NOT NULL,
    course_name varchar(100),
    units float(3,1) UNSIGNED NOT NULL,
    course_desc varchar(600)
);

ALTER TABLE `Courses` ADD FOREIGN KEY(subject_id) REFERENCES `Subjects`(id) ON DELETE CASCADE;
ALTER TABLE `Courses` ADD CONSTRAINT `unq_course` UNIQUE(subject_id, course_level);

create table `Prerequisites`(
	id int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_id int(5) UNSIGNED NOT NULL,
    prereq_id int(5) UNSIGNED NOT NULL,
    `group` tinyint(1) UNSIGNED NOT NULL
);

ALTER TABLE `Prerequisites` ADD FOREIGN KEY(course_id) REFERENCES `Courses`(id) ON DELETE CASCADE;
ALTER TABLE `Prerequisites` ADD FOREIGN KEY(prereq_id) REFERENCES `Courses`(id) ON DELETE CASCADE;
ALTER TABLE `Prerequisites` ADD CONSTRAINT `unq_prereq` UNIQUE(course_id, prereq_id);

create table `ConflictingCourses`(
	id int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_id int(5) UNSIGNED NOT NULL,
    conflicting_course_id int(5) UNSIGNED NOT NULL
);

ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(id);
ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(conflicting_course_id) REFERENCES `Courses`(id);
ALTER TABLE `ConflictingCourses` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, conflicting_course_id);

create table `Instructors`(
	id int(8) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dept_id int(2) UNSIGNED NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL
);

ALTER TABLE `Instructors` ADD FOREIGN KEY(dept_id) REFERENCES `Subjects`(id);

create table `Classes`(
	id int(8) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_id int(5) UNSIGNED NOT NULL,
    instructor_id int(8) UNSIGNED,
    /* class details */
    semester_id int(3) UNSIGNED NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,
    class_mode varchar(10) NOT NULL,
    room varchar(25),
    capacity int(3) UNSIGNED NOT NULL,
    num_enrolled int(3) UNSIGNED NOT NULL,
    isopened boolean NOT NULL DEFAULT TRUE
);

ALTER TABLE `Classes` ADD FOREIGN KEY(course_id) REFERENCES `Courses`(id);
ALTER TABLE `Classes` ADD FOREIGN KEY(semester_id) REFERENCES `Semesters`(id);
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_time` CHECK(start_time < end_time);
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_mode` CHECK(UPPER(class_mode) IN('IN-PERSON','ONLINE'));
ALTER TABLE `Classes` ADD CONSTRAINT `ck_room_format` CHECK(REGEXP_LIKE(room, '^([a-zA-Z]+) ([0-9]+)$'));
ALTER TABLE `Classes` ADD CONSTRAINT `ck_room_capacity` CHECK(num_enrolled <= capacity);

/** TODO: improve meeting days table */
create table `MeetingDays`(
	md_no int(5) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_no int(5) UNSIGNED NOT NULL,
    m_day int(1) UNSIGNED NOT NULL
);
ALTER TABLE `MeetingDays` ADD CONSTRAINT `unq_meeting_day` UNIQUE(m_day, class_no);

create table `Students`(
	student_no int(8) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email_address varchar(255) UNIQUE,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    gender varchar(6),
    dob date,
    degree varchar(7),
    major varchar(100),
    division char(1) NOT NULL,
    gpa double(3,2) UNSIGNED
);

ALTER TABLE `Students` ADD CONSTRAINT `ck_email` CHECK(REGEXP_LIKE(email_address, '^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$'));
ALTER TABLE `Students` ADD CONSTRAINT `ck_gender` CHECK(UPPER(gender) IN('MALE','FEMALE'));
ALTER TABLE `Students` ADD CONSTRAINT `ck_major` CHECK( (degree IS NOT NULL AND major IS NOT NULL) OR (degree IS NULL AND major IS NULL));
ALTER TABLE `Students` ADD CONSTRAINT `ck_division` CHECK(UPPER(division) IN('U','G'));
ALTER TABLE `Students` ADD CONSTRAINT `ck_gpa` CHECK(gpa <= 4.00);