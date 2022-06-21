/*

  Different SQL Operators:::    = , <, >, >=, <=, <>, !=, BETWEEN, ORDER BY, IN, NOT IN, LIKE, ALIASE, DISTINCT, LIMIT, CASE:
  Comparison Operators: =, <>, != , >, <, >=, <=
  Arithmetic Operators: +, -, *, /, %
  Logical Operators: AND, OR, NOT, IN, BETWEEN, LIKE etc.


  Constrains:
    - CHECK
    - NOT NULL,
    - UNIQUE
    - PRIMARY KEY     => on one or few columns
    - FOREIGN KEY


*/ -- Basic queries

select * from students;              -- Fetch all columns and all records (rows) from table.
select id, first_name from students; -- Fetch only ID and FIRST_NAME columns from students table.


/*


There are two way of writing select queries with a join:
They both are ok to use

1. select t1.column, t2.column
   from table_one as t1
   join table_two as t2 on t1.id = t2.id

2. select t1.column, t2.column
   from table_one as t1, table_two as t2
   where t1.id = t2.id
*/


-- Comparison Operators

select * from subjects where subject_name = 'Mathematics'; -- Fetch all records where subject name is Mathematics.
select * from subjects where subject_name <> 'Mathematics'; -- Fetch all records where subject name is not Mathematics.
select * from subjects where subject_name != 'Mathematics'; -- same as above. Both "<>" and "!=" are NOT EQUAL TO operator in SQL.
select * from staff_salary where salary > 10000; -- All records where salary is greater than 10000.
select * from staff_salary where salary < 10000; -- All records where salary is less than 10000.

select *
from staff_salary
where salary < 10000
order by salary; -- All records where salary is less than 10000 and the output is sorted in ascending order of salary.

select *
from staff_salary
where salary < 10000
order by salary desc; -- All records where salary is less than 10000 and the output is sorted in descending order of salary.

select *
from staff_salary
where salary >= 10000; -- All records where salary is greater than or equal to 10000.

select *
from staff_salary
where salary <= 10000; -- All records where salary is less than or equal to 10000.

-- Logical Operators

select *
from staff_salary
where salary between 5000 and 10000; -- Fetch all records where salary is between 5000 and 10000.

select *
from subjects
where subject_name IN ('Mathematics', 'Science', 'Arts'); -- All records where subjects is either Mathematics, Science or Arts.

select *
from subjects
where subject_name NOT IN ('Mathematics', 'Science', 'Arts'); -- All records where subjects is not Mathematics, Science or Arts.

select *
from subjects
where subject_name LIKE 'Computer%'; -- Fetch records where subject name has Computer as prefixed. % matches all characters.

select *
from subjects
where subject_name NOT LIKE 'Computer%'; -- Fetch records where subject name does not have Computer as prefixed. % matches all characters.

select *
from staff
where age > 50
	and gender = 'F'; -- Fetch records where staff is female and is over 50 years of age. AND operator fetches result only if the condition mentioned both on left side and right side of AND operator holds true. In OR operator, atleast any one of the conditions needs to hold true to fetch result.

select *
from staff
where first_name LIKE 'A%'
	and last_name LIKE 'S%'; -- Fetch record where first name of staff starts with "A" AND last name starts with "S".

select *
from staff
where first_name LIKE 'A%'
	or last_name LIKE 'S%'; -- Fetch record where first name of staff starts with "A" OR last name starts with "S". Meaning either the first name or the last name condition needs to match for query to return data.

select *
from staff
where (first_name LIKE 'A%'
							or last_name LIKE 'S%')
	and age > 50; -- Fetch record where staff is over 50 years of age AND has his first name starting with "A" OR his last name starting with "S".

-- Arithmetic Operators

select (5 + 2) as addition; -- Sum of two numbers. PostgreSQL does not need FROM clause to execute such queries.

select (5-2) as subtract; -- Oracle & MySQL equivalent query would be -->  select (5+2) as Addition FROM DUAL; --> Where dual is a dummy table.

select (5 * 2) as multiply;


select (5 / 2) as divide; -- Divides 2 numbers and returns whole number.

select (5 % 2) as modulus; -- Divides 2 numbers and returns the remainder


select staff_type
from staff ; -- Returns lot of duplicate data.

select distinct staff_type
from staff ; -- Returns unique values only.

select staff_type
from staff
limit 5; -- Fetches only the first 5 records from the result.

-- CASE statement:  (IF 1 then print True ; IF 0 then print FALSE ; ELSE print -1)

select staff_id,
	salary,
	case
					when salary >= 10000 then 'High Salary'
					when salary between 5000 and 10000 then 'Average Salary'
					when salary < 5000 then 'Too Low'
	end as range
from staff_salary
order by 2 desc;

-- TO_CHAR / TO_DATE:

select *
from students
where to_char(dob,

							'YYYY') = '2014';


select *
from students
where dob = to_date('13-JAN-2014',

													'DD-MON-YYYY');

-- JOINS (Two ways to write SQL queries):
-- #1. Using JOIN keyword between tables in FROM clause.

select t1.column1 as c1,
	t1.column2 c2,
	t2.column3 as c3 -- C1, C2, C3 are aliase to the column

from table1 t1
join table2 as t2 on t1.c1 = t2.c1
and t1.c2 = t2.c2; -- T1, T2 are aliases for table names.

-- #2. Using comma "," between tables in FROM clause.

select t1.column1 as c1,
	t1.column2 as c2,
	t2.column3 c3
from table1 as t1,
	table2 as t2
where t1.c1 = t2.c1
	and t1.c2 = t2.c2;

-- Fetch all the class name where Music is thought as a subject.

select class_name
from subjects sub
join classes cls on sub.subject_id = cls.subject_id
where subject_name = 'Music';

-- Fetch the full name of all staff who teach Mathematics.

select distinct (stf.first_name || ' ' || stf.last_name) as full_name --, CLS.CLASS_NAME
from subjects sub
join classes cls on cls.subject_id = sub.subject_id
join staff stf on cls.teacher_id = stf.staff_id
where sub.subject_name = 'Mathematics';

-- Fetch all staff who teach grade 8, 9, 10 and also fetch all the non-teaching staff
-- UNION can be used to merge two differnt queries. UNION returns always unique records so any duplicate data while merging these queries will be eliminated.
-- UNION ALL displays all records including the duplicate records.
-- When using both UNION, UNION ALL operators, rememeber that noo of columns and their data type must match among the different queries.

select stf.staff_type,
	(stf.first_name || ' ' || stf.last_name) as full_name,
	stf.age,
	(case
						when stf.gender = 'M' then 'Male'
						when stf.gender = 'F' then 'Female'
		end) as gender,
	stf.join_date
from staff stf
join classes cls on stf.staff_id = cls.teacher_id
where stf.staff_type = 'Teaching'
	and cls.class_name IN ('Grade 8',
																									'Grade 9',
																									'Grade 10')
union all
select staff_type,
	(first_name || ' ' || last_name) as full_name,
	age,
	(case
						when gender = 'M' then 'Male'
						when gender = 'F' then 'Female'
		end) as gender,
	join_date
from staff
where staff_type = 'Non-Teaching';

-- Count no of students in each class

select sc.class_id,
	count(1) as "no_of_students"
from student_classes sc
group by sc.class_id
order by sc.class_id;

-- Return only the records where there are more than 100 students in each class

select sc.class_id,
	count(1) as "no_of_students"
from student_classes sc
group by sc.class_id
having count(1) > 100
order by sc.class_id;

-- Parents with more than 1 kid in school.

select parent_id,
	count(1) as "no_of_kids"
from student_parent sp
group by parent_id
having count(1) > 1;

--SUBQUERY: Query written inside a query is called subquery.
-- Fetch the details of parents having more than 1 kids going to this school. Also display student details.

select (p.first_name || ' ' || p.last_name) as parent_name,
	(s.first_name || ' ' || s.last_name) as student_name,
	s.age as student_age,
	s.gender as student_gender,
	(adr.street || ', ' || adr.city || ', ' || adr.state || ', ' || adr.country) as address
from parents p
join student_parent sp on p.id = sp.parent_id
join students s on s.id = sp.student_id
join address adr on p.address_id = adr.address_id
where p.id IN
		(select parent_id
			from student_parent sp
			group by parent_id
			having count(1) > 1)
order by 1;

-- Staff details who’s salary is less than 5000

select staff_type,
	first_name,
	last_name
from staff
where staff_id IN
		(select staff_id
			from staff_salary
			where salary < 5000);

--Aggregate Functions (AVG, MIN, MAX, SUM, COUNT): Aggregate functions are used to perform calculations on a set of values.
-- AVG: Calculates the average of the given values.

select avg(ss.salary)::numeric(10,

																								2) as avg_salary
from staff_salary ss
join staff stf on stf.staff_id = ss.staff_id
where stf.staff_type = 'Teaching';


select stf.staff_type,
	avg(ss.salary)::numeric(10,

																		2) as avg_salary
from staff_salary ss
join staff stf on stf.staff_id = ss.staff_id
group by stf.staff_type;

/* Note:
“::NUMERIC” is a cast operator which is used to convert values from one data type to another.
In the above query we use it display numeric value more cleanly by restricting the decimal point to only 2.
Here 10 is precision which is the total no of digits allowed.
2 is the scale which is the digits after decimal point.
*/ -- SUM: Calculates the total sum of all values in the given column.

select stf.staff_type,
	sum(ss.salary)::numeric(10,

																		2) as avg_salary
from staff_salary ss
join staff stf on stf.staff_id = ss.staff_id
group by stf.staff_type;

-- MIN: Returns the record with minimun value in the given column.

select stf.staff_type,
	min(ss.salary)::numeric(10,

																		2) as avg_salary
from staff_salary ss
join staff stf on stf.staff_id = ss.staff_id
group by stf.staff_type;

-- MAX: Returns the record with maximum value in the given column.

select stf.staff_type,
	max(ss.salary)::numeric(10,

																		2) as avg_salary
from staff_salary ss
join staff stf on stf.staff_id = ss.staff_id
group by stf.staff_type;

/*
SQL Joins: There are several types of JOIN but we look at the most commonly used:
1) Inner Join
    - Inner joins fetches records when there are matching values in both tables.
2) Outer Join
    - Left Outer Join
        - Left join fetches all records from left table and the matching records from right table.
        - The count of the query will be the count of the Left table.
        - Columns which are fetched from right table and do not have a match will be passed as NULL.
    - Right Outer Join
        - Right join fetches all records from right table and the matching records from left table.
        - The count of the query will be the count of the right table.
        - Columns which are fetched from left table and do not have a match will be passed as NULL.
    - Full Outer Join
        - Full join always return the matching and non-matching records from both left and right table.
*/ -- Inner Join: 21 records returned – Inner join always fetches only the matching records present in both right and left table.
-- Inner Join can be represented as eithe "JOIN" or as "INNER JOIN". Both are correct and mean the same.

select count(1)
from staff stf
join staff_salary ss on ss.staff_id = stf.staff_id
order by 1;


select distinct (stf.first_name || ' ' || stf.last_name) as full_name,
	ss.salary
from staff stf
join staff_salary ss on ss.staff_id = stf.staff_id
order by 2;

-- 23 records – 23 records present in left table.
-- All records from LEFT table with be fetched irrespective of whether there is matching record in the RIGHT table.

select count(1)
from staff stf
left join staff_salary ss on ss.staff_id = stf.staff_id
order by 1;


select distinct (stf.first_name || ' ' || stf.last_name) as full_name,
	ss.salary
from staff stf
left join staff_salary ss on ss.staff_id = stf.staff_id
order by 2;

-- 24 records – 24 records in right table.
-- All records from RIGHT table with be fetched irrespective of whether there is matching record in the LEFT table.

select count(1)
from staff stf
right join staff_salary ss on ss.staff_id = stf.staff_id
order by 1;


select distinct (stf.first_name || ' ' || stf.last_name) as full_name,
	ss.salary
from staff stf
right join staff_salary ss on ss.staff_id = stf.staff_id
order by 1;

-- 26 records – all records from both tables. 21 matching records + 2 records from left + 3 from right table.
-- All records from both LEFT and RIGHT table with be fetched irrespective of whether there is matching record in both these tables.

select count(1)
from staff stf
full outer join staff_salary ss on ss.staff_id = stf.staff_id
order by 1;


select distinct (stf.first_name || ' ' || stf.last_name) as full_name,
	ss.salary
from staff stf
full outer join staff_salary ss on ss.staff_id = stf.staff_id
order by 1,2;