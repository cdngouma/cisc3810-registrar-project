select * from Subjects;

SELECT * FROM Subjects WHERE subj_no=(SELECT MAX(subj_no) FROM Subjects);

update subjects set subj_name=coalesce('ENGINEERING', subj_name), subj_abv=coalesce('ENGR', subj_abv) WHERE subj_no=7;

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

select 	k.class_no, concat(s.subj_abv,' ',c.course_level) as course_shr, c.course_name,
        k.instr_name, z.sem_name, k.room, k.capacity, k.num_enrolled, k.`mode`, k.opened
from classes k
join courses c on c.course_no=k.course_no
join subjects s on s.subj_no=c.subj_no
join semesters z on z.sem_no=k.sem_no
where k.sem_no=7;
