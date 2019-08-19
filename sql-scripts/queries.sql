/* Fecth all subjects */
SELECT * FROM Subjects;

/* Fetch all semesters */
SELECT * FROM Semesters;
/* Fetch opened enrollment periods */
SELECT * FROM Semesters WHERE start_date >= CURDATE();

/* Fetch all courses */
CALL FIND_COURSE_BY_ID(15);
CALL FIND_ALL_COURSES(NULL);
CALL FIND_ALL_COURSES(1);

/* Find all prerequisites for given course */
CALL FIND_ALL_COURSE_PREREQ(12);
/* Find all conflicting course with given course */
CALL FIND_ALL_CONFLICTING_COURSES(14);

/* Find all classes per semester */
CALL FIND_ALL_CLASSES(7, 'cisc', null, null, FALSE);
/* Find class */
CALL FIND_CLASS_BY_ID(2);
/* Find course from class */
CALL FIND_COURSE_FROM_CLASS(5);
