select * from Subjects;

insert into Subjects values(1, "MATHEMATICS", "MATH");

select * from Semesters where start_date >= CURDATE();

select * from Courses;

select C.course_no, C.subj_no, S.subj_abv, C.course_level, C.course_name, C.units, C.course_desc
from Courses C
join Subjects S on C.subj_no=S.subj_no
where S.subj_no=3;

select P.prereq_no, P.course_no, P.sec_course_no, C.subj_no, S.subj_abv, C.course_level, P.prereq_group
from Prerequisits P
join Courses C on P.sec_course_no = C.course_no
join Subjects S on C.subj_no = S.subj_no
where P.course_no=00012;

select X.conf_no, X.course_no, X.sec_course_no, C.subj_no, S.subj_abv, C.course_level
from Conflicting_Courses X
join Courses C on X.sec_course_no = C.course_no
join Subjects S on C.subj_no = S.subj_no
where X.course_no=00014;
