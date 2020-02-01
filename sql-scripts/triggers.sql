delimiter $$
create trigger `ins_instructor` before insert on `instructors`
for each row
begin
SET NEW.id = UUID_SHORT();
end; $$
