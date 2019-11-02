create table `semesters` (
	id int unsigned auto_increment primary key,
    sem_name enum('FALL','SPRING','SUMMER','WINTER') not null,
    start_date date not null,
    end_date date not null
);

alter table `semesters` add constraint `check_semester_period` check(end_date > start_date);

create table `subjects` (
	id int(2) unsigned primary key,
    subject_name varchar(50) not null,
    subject_name_short char(4) unique not null
);

alter table `subjects` add constraint `check_subject_name_short` check(LENGTH(subject_name_short) = 4);

create table `instructors` (
	id int(8) unsigned unique primary key,
    subject_id int(2) unsigned not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null
);

alter table `instructors` add foreign key (subject_id) references `subjects` (id);

create table `courses`(
	id int unsigned auto_increment primary key,
    subject_id int(2) unsigned not null,
    course_level int(4) not null,
    course_name varchar(100),
    units float(2,1) unsigned not null,
    course_desc varchar(600)
);

alter table `courses` add foreign key(subject_id) references `Subjects`(id) on delete cascade;
alter table `courses` add constraint `unique_course` unique(subject_id, course_level);

create table `prerequisites`(
	id int unsigned auto_increment primary key,
    course_id int unsigned not null,
    prereq_id int unsigned not null,
    prereq_group tinyint(1) unsigned not null
);

alter table `prerequisites` add foreign key(course_id) references `courses`(id) on delete cascade;
alter table `prerequisites` add foreign key(prereq_id) references `courses`(id) on delete cascade;
alter table `prerequisites` add constraint `unq_prereq` unique(course_id, prereq_id);

create table `conflicting_courses`(
	id int unsigned auto_increment primary key,
    course_id int unsigned not null,
    conf_course_id int unsigned not null
);

alter table `conflicting_courses` add foreign key(course_no) references `courses`(id);
alter table `conflicting_courses` add foreign key(conf_course_id) references `courses`(id);
alter table `conflicting_courses` add constraint `unique_prerequisite` unique(course_no, conf_course_id);

create table `classes`(
	id int unsigned auto_increment primary key,
    course_id int unsigned not null,
    instructor_id int(8) unsigned not null,
    /* class details */
    sem_id int unsigned not null,
    start_time time not null,
    end_time time not null,
    class_mode enum('IN-PERSON','ONLINE') default 'IN-PERSON',
    building varchar(20),
    room int(5) unsigned,
    capacity int(2) unsigned not null,
    num_enrolled int(2) unsigned not null,
    class_status enum('OPENED','CLOSED','WAITLIST') default 'OPENED'
);

alter table `classes` add foreign key (course_id) references `courses`(id);
alter table `classes` add foreign key (sem_id) references `semesters`(id);
alter table `classes` add foreign key (instructor_id) references `instructors`(id);
alter table `classes` add constraint `check_class_time` check (start_time < end_time);
alter table `classes` add constraint `check_room_availability` check (num_enrolled <= capacity);
alter table `classes` add constraint `check_room_building` check (building is null xor room is not null);

create table `meeting_days` (
	id int unsigned auto_increment primary key,
    class_id int unsigned not null,
    class_day tinyint(1) unsigned not null check (class_day < 7)
);

alter table `meeting_days` add foreign key (class_id) references `classes` (id);
alter table `meeting_days` add constraint `unique_class_day` unique (class_id, class_day);

/*
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
*/