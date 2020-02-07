create table `users`(
    username varchar(80) primary key,
    user_password varchar(255) not null,
    user_occupation enum('STUDENT','INSTRUCTOR','STAFF') not null
);

create table `academic_periods` (
	id int(2) unsigned auto_increment primary key,
    start_date date unique not null,
    end_date date unique not null,
    semester enum('FALL','SPRING','SUMMER','WINTER') not null
);

alter table academic_periods add constraint `chk_period` check (start_date < end_date);
-- alter table academic_periods add constraint `unq_semester` unique (year(start_date), semester);
-- alter table academic_periods add constraint `unq_period` unique (start_date, end_date);

create table `subjects` (
	id int(3) unsigned primary key,
    subject_name varchar(80) unique not null,
    subject_code char(4) unique not null
);

alter table subjects add constraint `chk_subj_code` check(LENGTH(subject_code) = 4);

create table `instructors` (
	id bigint(17) unsigned primary key,
    subject_id int(3) unsigned not null,
    first_name varchar(80) not null,
    last_name varchar(80) not null
);

alter table instructors add foreign key(subject_id) references subjects(id);

create table `courses` (
	id int(5) unsigned auto_increment primary key,
    course_code varchar(9) unique not null,
    subject_id int(3) unsigned not null,
    course_name varchar(80) not null,
    course_level int(4) unsigned not null,
    course_units float(1) unsigned not null,
    prerequisites varchar(255),
    conflicting_courses varchar(255),
    course_desc varchar(600)
);

alter table courses add foreign key (subject_id) references subjects(id);
alter table courses add constraint `unq_course` unique (subject_id, course_level);
alter table courses auto_increment = 10000;

-- Classes
create table `classes` (
	id int(8) unsigned auto_increment primary key,
    period_id int(2) unsigned not null,
    course_id int(5) unsigned not null,
    instructor_id bigint(17) unsigned,
    start_time time not null,
    end_time time not null,
    meeting_days varchar(14) not null,
    room varchar(7),
    room_capacity int(2) unsigned not null,
    num_enrolled int(2) unsigned not null default 0
);

alter table classes auto_increment = 10000000;
alter table classes add foreign key (period_id) references academic_periods (id);
alter table classes add foreign key (course_id) references courses (id);
alter table classes add foreign key (instructor_id) references instructors (id);
alter table classes add constraint `chk_class_time` check (start_time < end_time);
alter table classes add constraint `chk_num_enrolled` check (num_enrolled <= room_capacity);
alter table classes add constraint `chk_room_format` check (REGEXP_LIKE(room, "^[A-Z]{2} [0-9]{3,4}$") > 0);
alter table classes add constraint `chk_class_days` check (REGEXP_LIKE(meeting_days, "^(Mo)?(Tu)?(We)?(Th)?(Fr)?(Sa)?(Su)?$") > 0 and length(meeting_days) >= 2);
-- alter table classes add constraint `unq_class` unique (course_id, period_id, start_time, end_time, meeting_days);

select REGEXP_LIKE("TuTh", "^(Mo)?(Tu)?(We)?(Th)?(Fr)?(Sa)?(Su)?$") > 0 and length("TuTh") >= 2;

-- Academic
create table `majors` (
	id int(3) unsigned auto_increment primary key,
    degree enum('ASSOCIATE','BACHELOR','MASTER') not null,
    major_name varchar(80) not null
);

alter table majors auto_increment = 100;
alter table majors add constraint `unq_major` unique (degree, major_name);

-- Students
create table `students`(
	id bigint(17) unsigned primary key,
    -- username varchar(50) unique not null,
    -- personal info
    email varchar(100) unique not null,
    first_name varchar(80) not null,
    last_name varchar(80) not null,
    date_of_birth date not null,
    gender enum('MALE','FEMALE','OTHER') not null,
    -- academic info
    major_id int(3) unsigned not null,
    gpa double(4,2) unsigned
);

-- alter table students add foreign key (unsername) references `users` (username);
-- alter table students add constraint `chk_email` check (regexp_like(email, "") <> 0);
alter table students add foreign key (major_id) references majors (id);
alter table students add constraint `chk_gpa` check (gpa <= 4);

create table `student_classes` (
	id int(8) unsigned not null,
    student_id bigint(17) unsigned not null,
    class_id int(8) unsigned not null,
    grade double(5,2) unsigned,
    completion_status enum("COMPLETED", "ENROLLED", "FORGIVEN") not null default "ENROLLED"
);

alter table student_classes add foreign key (student_id) references students (id);
alter table student_classes add foreign key (class_id) references classes (id);
alter table student_classes add constraint `chk_grade` check (grade <= 100);
alter table student_classes add constraint `chk_completion_status` check (grade IS NOT NULL XOR completion_status = "ENROLLED");
