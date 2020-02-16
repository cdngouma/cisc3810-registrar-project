delimiter $$
create trigger `ins_instructor` before insert on `instructors`
for each row
begin
SET NEW.id = UUID_SHORT();
end; $$

delimiter $$
create trigger `ins_student` before insert on `students`
for each row
begin
SET NEW.id = UUID_SHORT();
SET NEW.email = CONCAT(UPPER(NEW.first_name), ".", UPPER(NEW.last_name), MOD(NEW.id, 10000),"@COLLEGE.EDU");
end; $$

/*delimiter $$
create trigger `ins_student_classes` before insert on `student_classes`
for each row
begin
end; $$*/
