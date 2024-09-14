-- Some vide about main defferences beetween SQL and NOSQL
  https://www.youtube.com/watch?v=IBzTDkYNB7I

NoSQL:
  - улучшения производительности
  - масштабиружмости
  - удобство в работе
  - не имеют преодпределенной структури
  - Бази Данних:

        - key-> value (like a Map): пара (ключ -> значение)
          Redis, memcash, dinamoDB

        - columnstore (колоночние БД)
          нету схеми
          key -> value (только value єто несколько столбцов сразу)
          пример: история просмотров, логи
          cassandra

        - документоориєнтирование БД
          хранят документи, где докумен и могут бить вложеними
          пример: игри, мобильние приложения, xml, json
          mongoDB, amazon dinamo DB

        - графовие БД
          внемание пределяється тому как данние связание друг з другом.
          есть узли которие представля.ют данние и єсть рєбра которие отображают связь межлу данними
          пример: алгоритми рекомендации, социальние сети

        - поисковие БД



Теорема CAP
    - согласованность:            каждая операция чтения возвращает данные с учетом всех обновлений или ошибку;
    - доступность:                каждый запрос к базе данных получает успешный ответ без строгой гарантии актуальности данных;
    - устойчивость к разделению:  система продолжает работать даже при потере связи или задержках сообщений между распределенными узлами.



  ACID и BASE – это МОДЕЛИ ТРАНЗАКЦИЙ ДЛЯ БАЗ ДАНИХ,
которые определяют структуру данных и порядок работы с ними в базе данных.
В контексте баз данных транзакцией называют любую операцию, которую база данных обрабатывает как единую единицу работы.
Чтобы база данных оставалась согласованной, транзакция должна быть полностью завершена. Например, если вы переводите деньги с
одного банковского счета на другой, нужно отразить эти изменения и на вашем счете, и на счете получателя.
Такую транзакцию невозможно считать завершенной без выполнения обоих шагов.

  В базах данных ACID приоритет отдается согласованности в ущерб доступности. Если на любом этапе транзакции возникает ошибка,
вся транзакция полностью отменяется.
  Напротив, базы данных BASE отдают приоритет доступности в ущерб согласованности. При неудачном завершении транзакции
пользователи могут временно получать несогласованные данные. Согласованность данных восстанавливается с некоторой задержкой.

BASE - модель транзакций
BASE означает:
    - базовую доступность (Basically Available)
      База данных должна быть доступна одновременно для всех пользователей в любое время. Пользователю не нужно ждать,
      пока завершатся другие транзакции, прежде чем обновлять запись.
    - мягкое состояние (Soft state)
      Любые данные могут находиться в промежуточных или временных состояниях и в некоторый момент изменяться «сами по себе»,
      без внешних событий или поступления новых данных. Эта концепция отражает неопределенное состояние записи, которую
      обновляют несколько приложений одновременно. Например, если пользователи редактируют сообщение в социальной сети, внесенные ими изменения
      могут быть не сразу видны другим пользователям. Через некоторое время система учтет все внесенные изменения,
      и произойдет обновление сообщения, хотя ни один пользователь его не инициировал.
    - согласованность (Eventually consistent).
      Запись достигнет согласованности не сразу, а после завершения всех одновременных обновлений.


Требования АСИД - набір правил які забезпечують цілісність данних.

Атомарність.
  Гарантує що транзакция буде виконана повність або не буде виконана взагалі. Не допускаються проміжні стани.

Узгодженість.
  Перевод бази даних з одого согласованого стану в інший согласований стан. Приклад: В рамках одної транзакції
є три insert -> у випадку якщо один insert впав з помилкою то відкатуються два інші.

Ізоляція.
  Події, що відбуваються всередині транзакції, повинні бути приховані від інших транзакцій, що одночасно виконуються.
При параллельном выполнении транзакций возможны следующие проблемы:

  - «грязное» чтение (уровень READ UNCOMMITTED) — чтение данных, добавленных или изменённых транзакцией,
    которая впоследствии не подтвердится (откатится).На уровне изоляции READ UNCOMMITTED транзакции могут видеть результаты
    незафиксированных транзакций, что может создать множество проблем.

        Транзакция 1                              Транзакция 2
        SELECT f2 FROM tbl1 WHERE f1=1;
        UPDATE tbl1 SET f2=f2+1 WHERE f1=1;
                                                   SELECT f2 FROM tbl1 WHERE f1=1;
        ROLLBACK WORK;
    В транзакции 1 изменяется значение поля f2, а затем в транзакции 2 выбирается значение этого поля. После этого происходит
    откат транзакции 1. В результате значение, полученное второй транзакцией, будет отличаться от значения, хранимого в базе данных.


- неповторяющееся чтение (уровень READ COMMITTED) — при повторном чтении в рамках одной транзакции, ранее прочитанные
  данные оказываются изменёнными или удалёнными. Это уровень изоляции по умолчанию для большинства СУБД. Транзакция увидит только те изменения,
  которые были уже зафиксированы другими транзакциями к моменту ее начала, а произведенные ею изменения останутся
  невидимыми для других транзакций, пока текущая транзакция не будет зафиксирована.
  Предположим, имеются две транзакции, открытые различными приложениями, в которых выполнены следующие SQL-операторы:
          Транзакция 1                                Транзакция 2
          SELECT f2 FROM tbl1 WHERE f1=1;             SELECT f2 FROM tbl1 WHERE f1=1;
          UPDATE tbl1 SET f2=f2+1 WHERE f1=1;
          COMMIT;
                                                      SELECT f2 FROM tbl1 WHERE f1=1;
    В транзакции 2 выбирается значение поля f2, затем в транзакции 1 изменяется значение поля f2. При повторной попытке выбора
  значения из поля f2 в транзакции 2 будет получен другой результат. Эта ситуация особенно неприемлема, когда данные
  считываются с целью их частичного изменения и обратной записи в базу данных.

- фантомное чтение (уровень REPEATABLE READ) — при повторном чтении в рамках одной транзакции прочитаны данные(новые "фантомные" строки),
  которых при предыдущих чтениях не было. Попросту говоря, фантомное чтение может происходить в случае, если в одной транзакции выбирается некоторый диапазон строк,
  затем другая транзакция вставляет новую строку в этот диапазон, после чего в первой транзакции выбирается тот же диапазон снова.
  В результате первая транзакция увидит новую «фантомную» строку.
          Транзакция 1                                  Транзакция 2
                                                        SELECT SUM(f2) FROM tbl1;
          INSERT INTO tbl1 (f1,f2) VALUES (15,20);
          COMMIT;
                                                        SELECT SUM(f2) FROM tbl1;



- упорядочиваемость (Serializable) - Самый высокий уровень изолированности; транзакции полностью изолируются друг от друга.
  На этом уровне результаты параллельного выполнения транзакций для базы данных в большинстве случаев можно считать
  совпадающими с последовательным выполнением тех же транзакций (по очереди в каком-либо порядке). Это самый высокий уровень изоляции,
  и решает проблему фантомного чтения, заставляя транзакции выполняться в таком порядке, чтобы исключить возможность конфликта.
  В двух словах, уровень SERIALIZABLE блокирует каждую строку, которую транзакция читает. На этом уровне может возникать множество
  задержек и конфликтов при блокировках. На практике данный уровень изоляции применяется достаточно редко,
  но потребности вашего приложения могут заставить вас использовать его, согласившись с меньшей степенью совместного
  доступа в пользу стабильности данных.




Довговічність. Після того, як транзакція завершилася та зафіксувала свої результати в базі даних, система має гарантувати,
що ці результати переживуть будь-які подальші збої(виелючили світло тощо...)


Если дубликаты удалять не нужно, используется инструкция UNION ALL:



  Different SQL Operators:::    = , <, >, >=, <=, <>, !=, BETWEEN, ORDER BY, IN, NOT IN, LIKE, ALIASE, DISTINCT, LIMIT, CASE:
  Comparison Operators:        =, <>, != , >, <, >=, <=
  Arithmetic Operators:        +, -, *, /, %
  Logical Operators:           AND, OR, NOT, IN, BETWEEN, LIKE etc.

  Constrains:
    - CHECK
    - NOT NULL,
    - UNIQUE
    - PRIMARY KEY     => on one or few columns
    - FOREIGN KEY

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
  where age > 50 and gender = 'F'; -- Fetch records where staff is female and is over 50 years of age. AND operator fetches result only if the condition mentioned both on left side and right side of AND operator holds true. In OR operator, atleast any one of the conditions needs to hold true to fetch result.

select *
  from staff
  where first_name LIKE 'A%' and last_name LIKE 'S%'; -- Fetch record where first name of staff starts with "A" AND last name starts with "S".

select *
  from staff
  where first_name LIKE 'A%' or last_name LIKE 'S%'; -- Fetch record where first name of staff starts with "A" OR last name starts with "S". Meaning either the first name or the last name condition needs to match for query to return data.

select *
  from staff
  where (first_name LIKE 'A%' or last_name LIKE 'S%') and age > 50; -- Fetch record where staff is over 50 years of age AND has his first name starting with "A" OR his last name starting with "S".




-- Arithmetic Operators
select (5 + 2) as addition; -- Sum of two numbers. PostgreSQL does not need FROM clause to execute such queries.
select (5-2) as subtract; -- Oracle & MySQL equivalent query would be -->  select (5+2) as Addition FROM DUAL; --> Where dual is a dummy table.
select (5 * 2) as multiply;
select (5 / 2) as divide; -- Divides 2 numbers and returns whole number.
select (5 % 2) as modulus; -- Divides 2 numbers and returns the remainder

select staff_type from staff ; -- Returns lot of duplicate data.
select distinct staff_type from staff ; -- Returns unique values only.
select staff_type from staff limit 5; -- Fetches only the first 5 records from the result.

select * from staff_view;


-- JOINS

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
*/

-- Inner Join: 21 records returned – Inner join always fetches only the matching records present in both right and left table.
-- Inner Join can be represented as either "JOIN" or as "INNER JOIN". Both are correct and mean the same.
select count(1)
  from staff stf
  join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1;

select distinct (stf.first_name || ' ' || stf.last_name) as full_name, ss.salary
  from staff stf
  join staff_salary ss on ss.staff_id = stf.staff_id
  order by 2;

-- 23 records – 23 records present in left table.
-- All records from LEFT table with be fetched irrespective of whether there is matching record in the RIGHT table.
select count(1)
  from staff stf
  left join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1;

select distinct (stf.first_name || ' ' || stf.last_name) as full_name,ss.salary
  from staff stf
  left join staff_salary ss on ss.staff_id = stf.staff_id
  order by 2;

-- 24 records – 24 records in right table.
-- All records from RIGHT table with be fetched irrespective of whether there is matching record in the LEFT table.
select count(1)
  from staff stf
  right join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1;

select distinct (stf.first_name || ' ' || stf.last_name) as full_name, ss.salary
  from staff stf
  right join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1;

-- 26 records – all records from both tables. 21 matching records + 2 records from left + 3 from right table.
-- All records from both LEFT and RIGHT table with be fetched irrespective of whether there is matching record in both these tables.
select count(1)
  from staff stf
  full outer join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1;

select distinct (stf.first_name || ' ' || stf.last_name) as full_name, ss.salary
  from staff stf
  full outer join staff_salary ss on ss.staff_id = stf.staff_id
  order by 1,2;


--(Two ways to write SQL queries):
-- #1 Using JOIN keyword between tables in FROM clause.

select t1.column1 as c1, t1.column2 c2, t2.column3 as c3
  from table1 t1
  join table2 as t2 on t1.c1 = t2.c1 and t1.c2 = t2.c2;

-- #2. Using comma "," between tables in FROM clause.

select t1.c1 as c1, t1.c2 as c2, t2.c3 c3
  from table1 as t1, table2 as t2
  where t1.c1 = t2.c1 and t1.c2 = t2.c2;


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





-- CASE statement
(IF 1 then print True ; IF 0 then print FALSE ; ELSE print -1)

-- multiply case is allowed in select statement but each case for each column
select age, gender, staff_id,
  case
    when age > 50 then 'age is more than 50'
  end as range,
  case
    when gender = 'F' then 'M'
  end as gender_edit
from staff;


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
  where to_char(dob, 'YYYY') = '2014';

select to_char(dob, 'DD'), to_char(dob, 'MM'), to_char(dob, 'YYYY')
  from students
  where to_char(dob, 'MM') = '02';

select *
  from students
  where dob = to_date('13-JAN-2014', 'DD-MON-YYYY');




--  Aggregate Functions (AVG, MIN, MAX, SUM, COUNT):
-- AVG: Calculates the average of the given values.
select avg(ss.salary) as avg_salary
  from staff_salary ss
  join staff stf on stf.staff_id = ss.staff_id
  where stf.staff_type = 'Teaching';


select stf.staff_type, avg(ss.salary) as avg_salary
  from staff_salary as ss
  join staff as stf on stf.staff_id = ss.staff_id
  group by stf.staff_type;

-- SUM: Calculates the total sum of all values in the given column.
select stf.staff_type, sum(ss.salary)::numeric(10, 2) as avg_salary
  from staff_salary ss
  join staff stf on stf.staff_id = ss.staff_id
  group by stf.staff_type;

-- MIN: Returns the record with minimun value in the given column.
select stf.staff_type, min(ss.salary)::numeric(10,2) as avg_salary
  from staff_salary ss
  join staff stf on stf.staff_id = ss.staff_id
  group by stf.staff_type;

-- MAX: Returns the record with maximum value in the given column.
select stf.staff_type, max(ss.salary)::numeric(10,2) as avg_salary
  from staff_salary ss
  join staff stf on stf.staff_id = ss.staff_id
  group by stf.staff_type;

/* Note:
  '::NUMERIC' is a cast operator which is used to convert values from one data type to another.
  In the above query we use it display numeric value more cleanly by restricting the decimal point to only 2.
  Here 10 is precision which is the total no of digits allowed.
  2 is the scale which is the digits after decimal point.
*/


-- Select count of students on each class
select sc.class_id, count(*) as "amount_of_students"
  from student_classes sc
  group by sc.class_id
  order by sc.class_id;

-- Select count of students on each class where amount of them is greater than 100
select sc.class_id, count(*) as "amount_of_students"
  from student_classes sc
  group by sc.class_id
  having count(*) > 100
  order by sc.class_id;

-- Parents with more than 1 kid in school.
select parent_id, count(*) as "count_of_kids"
  from student_parent sp
  group by parent_id
  having count(1) > 1;





-- SUBQUERY:
--   1. Scalar subquery
--   2. Multiply row
--   3. Correlated

--   Subquery can be used in:
--     -   SELECT - which is not recommended
--     -   FROM
--     -   WHERE
--     -   HAVING

-- subquery in  Having example:
-- Question: Find the store who sold more units than the average units sold by all stores
select store_name, sum(quantity)
  from sales
  group by store_name
  having sum(quantity) > (select avg(quantity) from sales);
-- Query written inside a query is called subquery


-- SCALARY
--  subquery query which return always 1 row and 1 column
--  Find the employees who's salary is more that the average salary earned by all employees
--  1. find the avg salary
--  2. filter the employees based on the above result

    select *          -- main query
      from employee
      where salary > (select avg(salary) from employee); -- subquery / inner query



-- MULTIPLE row subquery
-- Find the employees who earn the highest salary in each department
    select *
      from employee
      where (dept_name, salary) in (select dept_name, max(salary)
                                  from employee
                                  group by dept_name);

-- Find the department who don't have any employee
    select *
      from department
      where dept_name not in (select distinct dept_name from employee)


-- CORRELATED subquery
-- Subquery where inner query depends on main query
-- Find the employee who earn more than the average salary in that departmant
select *
  from employee e1
  where salary > (select avg(salary)
                    from employee e2
                    where e1.dept_name = e2.dept_name )




-- VIEWS

--  cases:
--    1. create view
--    2. alter table by adding new column
--    3. view does not contain this new column. View should be recreated only then new column will appear.
--
--  - View can be updated in case it was created based on only one table.

create view some_view
  as
  select * from staff;


select * from staff_salary;
select avg(salary) from staff_salary;



-- WITH CLOUSE
     -- name -----     -- query itself ------
with students_with as (select * from students)
select * from students_with -- using that query here


with sales as
  (select store_name, sum(price) as total_sales
    from sales
     group by store_name)
select *
from sales
join (select avg(total_sales) as sales from sales x) avg_sales
on sales.total.price > avg_sales.sales






--  INSERT
-- Question: Insert employees into the history table and make sure there are no duplicates
insert into employee_history
  select *
    from eployee
    join department on employee.dep_name=deparrtment.dep_name
  where not exists (select 1 from employee_history where employee.id=employee_history.id)



-- UPDATE
-- Question: Give 10% increment to all employee in Bangalor location based on the maximum salary earned by
-- an employee in each departament. Only consider  employees in employee_history table

update employeee e
set salary = (select max(salary) + (max(salary * 0.1))
              from employee_history eh
              where eh.dep_name = e.dep_name)
where e.dep_name in (select dep_name from department where location = 'Bangalor') and
      e.id in (select id from employee_history);



-- WINDOW FUNCTION

select *, max(salary) over(partition by dept_name) as max_salary
  from employee e;

-- ROW_NUMBER()
select *, row_number() over(partition by dept_name) as rm
  from employee

-- Find the first 2 employee who has joined the company for each department
select *
  from (
    select *, row_number() over(partition by dept_name order by e.emp_id) as rm
    from employee e
    ) x
  where x.rm < 3;

-- RANK()
-- Find the top 3 employees in each department earning the max salary
select *
  from (
    select *,  rank() over(partition by dept_name order by salary desc) as asd
    from employee e) x
  where x.asd < 4 and ;

-- DENSE_RANK()
-- The deference between rank() and dense_rank() is that in case of similar values heppest `dense_rank` does not skip
-- the number. Just run to see what it means

select *
from (
    select *,
      rank()       over(partition by dept_name order by salary desc) as rank,
      dense_rank() over(partition by dept_name order by salary desc) as dense_rank,
      row_number() over(partition by dept_name order by salary desc) as row_number
    from employee e) x
where x.row_number < 4;


-- lag()   - looks at the previous record
--  lead()  - looks at the next record
--  Question: If salary of an employee is higher or lower or equal to the previous employee

select *,
  lag(salary, 1 /*row number to look at*/, 0 /*default number in case of first or last record*/) over(partition by dept_name order by salary desc) as foo
  lag(salary, 1 /*row number to look at*/, 0 /*default number in case of first or last record*/) over(partition by dept_name order by salary desc) as foo
  from employee;
