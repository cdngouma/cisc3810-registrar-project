create table `Users`(
	id int(7) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username varchar(250) UNIQUE NOT NULL,
    user_password varchar(100) NOT NULL,
    occupation varchar(10) NOT NULL /* student or instructor */
);

create table `User_Devices`(
	device_id varchar(100) PRIMARY KEY,
	userno int(7) UNSIGNED NOT NULL,
    ip_address varchar(10) UNIQUE NOT NULL,
    logged_in boolean NOT NULL,
    last_logged timestamp
);

ALTER TABLE `User_Devices` ADD FOREIGN KEY(userno) REFERENCES `Users`(userno) ON DELETE CASCADE;

create table `Instructors`(
	id int(7) UNSIGNED UNIQUE NOT NULL,
    subject_no int(3) UNSIGNED NOT NULL,
    email varchar(250) UNIQUE NOT NULL,
    first_name varchar(80) NOT NULL,
    last_name varchar(80) NOT NULL,
    gender varchar(7)
);

ALTER TABLE `Instructors` ADD FOREIGN KEY(id) REFERENCES Users(user_no);
ALTER TABLE `Instructors` ADD FOREIGN KEY(dept_no) REFERENCES AcadDept(dept_no);

create table `Students`(
	id int(7) UNSIGNED UNIQUE NOT NULL,
    /* personal info */
    email varchar(200) UNIQUE NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    gender varchar(7),
    dob date NOT NULL,
    /* academics */
    degree varchar(7) NOT NULL, /* abreviation of degree */
    major varchar(100),
    division char NOT NULL,
    transf_cr double(3,1) UNSIGNED,
    attempt_cr double(3,1) UNSIGNED,
    earned_cr double(4,1) UNSIGNED,
    gpa double(3,2) UNSIGNED,
    admission_date date
);

ALTER TABLE `Students` ADD FOREIGN KEY(id) REFERENCES Users(userno);

/* Create views for Student table */
create view `StudentInfo` AS
select id, email, first_name, last_name, gender, dob
from `Students`;

create view `StudentAcademics` AS
select id, degree, major, division, transf_cr, attempt_cr, earned_cr, gpa, admission_date
from `Students`;