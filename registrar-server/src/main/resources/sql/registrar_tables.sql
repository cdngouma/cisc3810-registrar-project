/* Tables "owned" to the registrar office */
/* departments */
create table `CourseSubjects`(
	subject_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject_name varchar(100) UNIQUE NOT NULL,
    subject_abv varchar(4) UNIQUE NOT NULL
);

/* semesters */
create table `EnrollmentPeriods`(
	period_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    semester varchar(6) NOT NULL,
    start_date date UNIQUE NOT NULL,
    end_date date UNIQUE NOT NULL
);

ALTER TABLE `EnrollmentPeriods` ADD CONSTRAINT `unq_period` UNIQUE(start_date, end_date);

/* courses */
create table `Courses`(
	course_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject_no int(3) UNSIGNED NOT NULL,
    course_name varchar(80),
    course_level int(4) UNSIGNED NOT NULL,
    units double(3,2) UNSIGNED NOT NULL,
    course_desc varchar(600)
);

ALTER TABLE `Courses` ADD FOREIGN KEY(subject_no) REFERENCES `AcadDept`(subject_no) ON DELETE CASCADE;
ALTER TABLE `Courses` ADD CONSTRAINT `unq_course` UNIQUE(subject_no, course_level);

/* prerequisits */
create table `Prerequisits`(
	prereq_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	course_no int(3) UNSIGNED NOT NULL,
    sec_course_no int(3) UNSIGNED NOT NULL,
    prereq_group int(1) UNSIGNED NOT NULL
);

ALTER TABLE `Prerequisits` ADD FOREIGN KEY(course_no) REFERENCES Courses(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisits` ADD FOREIGN KEY(sec_course_no) REFERENCES Courses(course_no) ON DELETE CASCADE;
ALTER TABLE `Prerequisits` ADD CONSTRAINT `unq_prereq` UNIQUE(course_no, sec_course_no);

/* conflicting courses */
create table `ConflictingCourses`(
	conf_no int(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	course_no int(3) UNSIGNED NOT NULL,
    sec_course_no int(3) UNSIGNED NOT NULL
);

ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(course_no) REFERENCES Courses(course_no);
ALTER TABLE `ConflictingCourses` ADD FOREIGN KEY(sec_course_no) REFERENCES Courses(course_no);
ALTER TABLE `ConflictingCourses` ADD CONSTRAINT `unq_conf_course` UNIQUE(course_no, sec_course_no);

/* sections */
create table `Sections`(
	section_no int(2) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    section_name varchar(4) UNIQUE NOT NULL,
    meeting_days varchar(14) NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL
);

ALTER TABLE `Sections` ADD CONSTRAINT `unq_section_hours` UNIQUE(start_time, end_time, meeting_days);