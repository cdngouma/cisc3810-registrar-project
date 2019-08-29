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
    units float(3,1) UNSIGNED NOT NULL,
    course_desc varchar(600)
);

ALTER TABLE `Courses` ADD FOREIGN KEY(subj_no) REFERENCES `Subjects`(subj_no) ON DELETE CASCADE;
ALTER TABLE `Courses` ADD CONSTRAINT `unq_course` UNIQUE(subj_no, course_level);

create table `Prerequisites`(
	prereq_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_no int(5) UNSIGNED NOT NULL,
    sec_course_no int(5) UNSIGNED NOT NULL,
    prereq_group int(1) UNSIGNED
);

ALTER TABLE `Prerequisites` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisites` ADD FOREIGN KEY(sec_course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisites` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, sec_course_no);

create table `ConflictingCourses`(
	conf_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    course_no int(5) UNSIGNED NOT NULL,
    sec_course_no int(5) UNSIGNED NOT NULL
);

ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no);
ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(sec_course_no) REFERENCES `Courses`(course_no);
ALTER TABLE `ConflictingCourses` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, sec_course_no);

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

ALTER TABLE `Classes` ADD FOREIGN KEY(course_no) REFERENCES `Courses`(course_no) ON DELETE CASCADE;
ALTER TABLE `Classes` ADD FOREIGN KEY(sem_no) REFERENCES `Semesters`(sem_no) ON DELETE CASCADE;
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_time` CHECK(start_time < end_time);
ALTER TABLE `Classes` ADD CONSTRAINT `ck_class_mode` CHECK(UPPER(`mode`) IN('IN-PERSON','ONLINE'));
ALTER TABLE `Classes` ADD CONSTRAINT `ck_room_fmt` CHECK(REGEXP_LIKE(UPPER(room), '^([A-Z]{2}) ([0-9]{3,4})$'));
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