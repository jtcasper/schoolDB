--------------------------------------------------------
--  File created - Monday-April-03-2017   
--------------------------------------------------------
DROP TABLE "BKMUKHEJ"."ADMIN" cascade constraints;
DROP TABLE "BKMUKHEJ"."COURSE" cascade constraints;
DROP TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" cascade constraints;
DROP TABLE "BKMUKHEJ"."GRADING" cascade constraints;
DROP TABLE "BKMUKHEJ"."OFFERS" cascade constraints;
DROP TABLE "BKMUKHEJ"."PRECONDITION" cascade constraints;
DROP TABLE "BKMUKHEJ"."SEMESTER" cascade constraints;
DROP TABLE "BKMUKHEJ"."STUDENT" cascade constraints;
DROP TABLE "BKMUKHEJ"."TAKES" cascade constraints;
DROP TABLE "BKMUKHEJ"."WAITLIST" cascade constraints;
DROP PROCEDURE "BKMUKHEJ"."ADD_COURSE";
DROP PROCEDURE "BKMUKHEJ"."ADD_COURSE_OFFERING";
DROP PROCEDURE "BKMUKHEJ"."CALCULATE_GPA";
DROP PROCEDURE "BKMUKHEJ"."DROP_COURSE";
DROP PROCEDURE "BKMUKHEJ"."ENFORCE_DEADLINE";
DROP PROCEDURE "BKMUKHEJ"."ENROLL_COURSE";
DROP PROCEDURE "BKMUKHEJ"."ENROLL_STUDENT";
DROP PROCEDURE "BKMUKHEJ"."SAMPLE_PROC";
DROP PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_1";
DROP PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_2";
DROP PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_3";
DROP PROCEDURE "BKMUKHEJ"."VIEW_PROFILE_ADMIN";
DROP SYNONYM "PUBLIC"."DBMS_OUTPUT";
--------------------------------------------------------
--  DDL for Table ADMIN
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."ADMIN" 
   (	"EID" VARCHAR2(20 BYTE), 
	"USERNAME" VARCHAR2(20 BYTE), 
	"FNAME" VARCHAR2(20 BYTE), 
	"LNAME" VARCHAR2(30 BYTE), 
	"PASSWORD" VARCHAR2(20 BYTE) DEFAULT 123456, 
	"DOB" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."COURSE" 
   (	"CID" VARCHAR2(20 BYTE), 
	"TITLE" VARCHAR2(50 BYTE), 
	"CREDITS" VARCHAR2(20 BYTE), 
	"CLEVEL" VARCHAR2(20 BYTE), 
	"DID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."COURSE"."CLEVEL" IS 'Course Level
';
--------------------------------------------------------
--  DDL for Table CREDITCOSTLIMITS
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" 
   (	"CLEVEL" VARCHAR2(20 BYTE), 
	"RESIDENCY" VARCHAR2(20 BYTE), 
	"COST_PER_CREDIT" NUMBER, 
	"MINCREDITS" NUMBER, 
	"MAXCREDITS" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table GRADING
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."GRADING" 
   (	"GRADE" VARCHAR2(20 BYTE), 
	"GRADE_POINT" FLOAT(126)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table OFFERS
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."OFFERS" 
   (	"SCHEDULE" VARCHAR2(50 BYTE), 
	"LOCATION" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE), 
	"SEMID" VARCHAR2(20 BYTE), 
	"FID" VARCHAR2(20 BYTE), 
	"CLASSSIZE" NUMBER DEFAULT 0, 
	"WAITSIZE" NUMBER DEFAULT 0, 
	"SESSIONID" VARCHAR2(3 BYTE) DEFAULT 001
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."OFFERS"."FID" IS 'Faculty Id';
 
   COMMENT ON COLUMN "BKMUKHEJ"."OFFERS"."SESSIONID" IS 'Session Id of a course in case of multiple sessions in a semester';
--------------------------------------------------------
--  DDL for Table PRECONDITION
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."PRECONDITION" 
   (	"CID" VARCHAR2(20 BYTE), 
	"PRE_CID" VARCHAR2(20 BYTE), 
	"GPA" VARCHAR2(20 BYTE), 
	"SPPERM" VARCHAR2(20 BYTE) DEFAULT 'N', 
	"PREREQ_GRADE" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."PRECONDITION"."SPPERM" IS '''Y''=Required, N=''Not Required';
--------------------------------------------------------
--  DDL for Table SEMESTER
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."SEMESTER" 
   (	"SEMID" VARCHAR2(20 BYTE), 
	"YEAR" NUMBER, 
	"DEADLINE" DATE, 
	"SEMESTER" VARCHAR2(20 BYTE), 
	"IS_DEADLINE_ENFORCED" NUMBER DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."SEMESTER"."SEMESTER" IS 'F = Fall, S = Spring, Su = Summer';
 
   COMMENT ON COLUMN "BKMUKHEJ"."SEMESTER"."IS_DEADLINE_ENFORCED" IS '0 = No, 1 = Yes';
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."STUDENT" 
   (	"SID" VARCHAR2(20 BYTE), 
	"LNAME" VARCHAR2(20 BYTE), 
	"FNAME" VARCHAR2(20 BYTE), 
	"EMAIL" VARCHAR2(20 BYTE), 
	"PWD" VARCHAR2(20 BYTE) DEFAULT NULL, 
	"SLEVEL" VARCHAR2(20 BYTE), 
	"BILL" VARCHAR2(20 BYTE) DEFAULT 0, 
	"DID" VARCHAR2(20 BYTE), 
	"RESIDENCY" VARCHAR2(20 BYTE), 
	"UNAME" VARCHAR2(20 BYTE), 
	"DOB" DATE, 
	"CREDITS" NUMBER DEFAULT 0, 
	"GPA" FLOAT(126) DEFAULT 0.0, 
	"ADDRESS" VARCHAR2(50 BYTE) DEFAULT NULL, 
	"PHONE_NUMBER" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."STUDENT"."CREDITS" IS 'Credits already taken';
--------------------------------------------------------
--  DDL for Table TAKES
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."TAKES" 
   (	"SID" VARCHAR2(20 BYTE), 
	"GRADE" VARCHAR2(4 BYTE) DEFAULT 'NULL', 
	"CREDITS" NUMBER(1,0) DEFAULT 3, 
	"STATUS" VARCHAR2(20 BYTE), 
	"SEMID" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE), 
	"SESSIONID" VARCHAR2(3 BYTE) DEFAULT 001
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "BKMUKHEJ"."TAKES"."CREDITS" IS 'The credits of the course which counted';
 
   COMMENT ON COLUMN "BKMUKHEJ"."TAKES"."STATUS" IS '''Confirmed'' or ''Pending'' or ''Waitlist''';
--------------------------------------------------------
--  DDL for Table WAITLIST
--------------------------------------------------------

  CREATE TABLE "BKMUKHEJ"."WAITLIST" 
   (	"WNUM" VARCHAR2(20 BYTE), 
	"SEMID" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE), 
	"SESSIONID" VARCHAR2(20 BYTE), 
	"SID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index ADMIN_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."ADMIN_PK" ON "BKMUKHEJ"."ADMIN" ("EID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index COURSE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."COURSE_PK" ON "BKMUKHEJ"."COURSE" ("CID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index CREDITCOSTLIMITS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."CREDITCOSTLIMITS_UK1" ON "BKMUKHEJ"."CREDITCOSTLIMITS" ("CLEVEL", "RESIDENCY") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index GRADING_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."GRADING_PK" ON "BKMUKHEJ"."GRADING" ("GRADE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index OFFERS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."OFFERS_UK1" ON "BKMUKHEJ"."OFFERS" ("SCHEDULE", "LOCATION", "CID", "SEMID", "FID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index OFFERS_UK2
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."OFFERS_UK2" ON "BKMUKHEJ"."OFFERS" ("CID", "SESSIONID", "SEMID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index PRECONDITION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."PRECONDITION_PK" ON "BKMUKHEJ"."PRECONDITION" ("CID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SEMESTER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."SEMESTER_PK" ON "BKMUKHEJ"."SEMESTER" ("SEMID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index STUDENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."STUDENT_PK" ON "BKMUKHEJ"."STUDENT" ("SID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TAKES_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "BKMUKHEJ"."TAKES_UK1" ON "BKMUKHEJ"."TAKES" ("SID", "SEMID", "CID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Trigger TRIGGER1
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BKMUKHEJ"."TRIGGER1" 
AFTER INSERT OR UPDATE OR DELETE ON TAKES 
BEGIN
  CALCULATE_GPA;
END;
/
ALTER TRIGGER "BKMUKHEJ"."TRIGGER1" ENABLE;
--------------------------------------------------------
--  DDL for Procedure ADD_COURSE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."ADD_COURSE" 
(
  CID_INPUT IN VARCHAR2 
, TITLE_INPUT IN VARCHAR2 
, CREDITS_INPUT IN VARCHAR2 
, CLEVEL_INPUT IN VARCHAR2 
, DID_INPUT IN VARCHAR2 
, STATUS OUT VARCHAR2 
) AS 
cnt number;
BEGIN
  select count(*) into cnt from course where cid = CID_INPUT;
  if (cnt <>0) THEN
    STATUS := 'Not Done';
  else
    Insert into course (cid, title, credits, clevel, did) values (cid_input, title_input, credits_input, clevel_input, did_input);
    STATUS := 'Done';
  end if;
END ADD_COURSE;

/
--------------------------------------------------------
--  DDL for Procedure ADD_COURSE_OFFERING
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."ADD_COURSE_OFFERING" 
(
  STATUS OUT VARCHAR2 
, CID_INPUT IN VARCHAR2 
, SEMID_INPUT IN VARCHAR2 
, SCHEDULE_INPUT IN VARCHAR2 
, LOCATION_INPUT IN VARCHAR2 
, CLASSSIZE_INPUT IN VARCHAR2 
, WAITSIZE_INPUT IN VARCHAR2 
, FID_INPUT IN VARCHAR2 
) AS 
offering_cnt number;
sem_cnt number;
BEGIN
  select count(*) into offering_cnt from offers where cid = CID_INPUT and semid = semid_input and fid = fid_input and schedule = schedule_input;
  if (offering_cnt <>0) THEN
    STATUS := 'Course Offering already exists.';
  else
    select count(*) into sem_cnt from semester where semid = semid_input;
    if (sem_cnt = 0) then
      STATUS := 'Semester Does Not Exist.';
    else
      Insert into offers (cid, semid, schedule, location, fid, classsize, waitsize) values (cid_input, semid_input, schedule_input, location_input, fid_input, classsize_input, waitsize_input);
      STATUS := 'Done';
    end if;
  end if;
END ADD_COURSE_OFFERING;

/
--------------------------------------------------------
--  DDL for Procedure CALCULATE_GPA
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."CALCULATE_GPA" 
AS 
cgpa NUMBER;
BEGIN
  FOR s IN (SELECT * from student) 
  loop
    select ROUND((sum(grading.GRADE_POINT*takes.CREDITS)/sum(takes.credits)),2) into cgpa from takes inner join grading on grading.GRADE = takes.GRADE and takes.SID = s.SID and takes.Status = 'Graded';
    if (cgpa > 4) then
      cgpa := 4;
    end if;
    update student set gpa = cgpa where sid = s.SID;
    --dbms_output.put_line(s.sid);
    --dbms_output.put_line(cgpa);
    cgpa := 0;
  end loop;
END CALCULATE_GPA;

/
--------------------------------------------------------
--  DDL for Procedure DROP_COURSE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."DROP_COURSE" 
(
  CID_INPUT IN VARCHAR2 
, SID_INPUT IN VARCHAR2 
, SEMID_INPUT IN VARCHAR2 
) AS
credits_billed number;
credit_cost number;
BEGIN
  select credits into credits_billed from takes where CID = cid_input and sid = SID_INPUT and semid = SEMID_INPUT and STATUS <> 'Graded';
  delete from takes where CID = cid_input and sid = SID_INPUT and semid = SEMID_INPUT and status <> 'Graded';
  select cost_per_credit into credit_cost from student inner join creditcostlimits ccl on student.slevel = ccl.clevel and student.residency = ccl.residency and student.sid = sid_input; 
  update student set bill = bill - (credits_billed*credit_cost) where sid = sid_input;
  update takes set STATUS = 'Confirmed' where cid = cid_input and semid = semid_input and status = 'Waitlist' and rownum = 1;
END DROP_COURSE;

/
--------------------------------------------------------
--  DDL for Procedure ENFORCE_DEADLINE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."ENFORCE_DEADLINE" 
(
SEMID_INPUT IN VARCHAR2
, status out varchar2
) AS
is_enforced number;
BEGIN
  select IS_DEADLINE_ENFORCED into is_enforced from semester where semid = semid_input;
  if (is_enforced = 0) then
    FOR s in (select sid from student where bill > 0) loop
      for c in (select cid from takes where semid = semid_input and sid = s.sid)
      loop
        begin
          drop_course(c.cid, s.sid, SEMID_INPUT);
        end;
      end loop;
    end loop;
    Delete from takes where semid = semid_input and STATUS = 'Waitlist';
    update semester set IS_DEADLINE_ENFORCED = 1 where semid = semid_input;
    status := 'Done';
  else
    status := 'Already Enforced Once in this semester';
  end if;
END ENFORCE_DEADLINE;

/
--------------------------------------------------------
--  DDL for Procedure ENROLL_COURSE
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."ENROLL_COURSE" 
(
  SID_INPUT IN VARCHAR2 
, CID_INPUT IN VARCHAR2 
, SEMID_INPUT IN VARCHAR2
, STATUS OUT VARCHAR2
, CREDITS_INPUT IN NUMBER
, SESSIONID_INPUT IN varchar2
) AS 
people_count number;
class_size number;
waitlist_size number;
record_gpa float;
record_pre_cid varchar2(5);
record_sp_perm varchar2(1);
record_prereq_grade varchar2(2);
record_prereq_grade_point float;
is_sp_perm varchar2(1) := 'N';
should_enroll varchar2(1) := 'Y';
course_grade_point number;
student_gpa float;

BEGIN
status := 'Y';
begin
--Check Prerequisites for student;
select pre_cid,gpa,spperm, prereq_grade into record_pre_cid,record_gpa,record_sp_perm,record_prereq_grade from precondition where cid = cid_input;
exception when NO_DATA_FOUND then
  record_pre_cid:= NULL;
  record_gpa:= NULL;
  record_sp_perm:=NULL;
  record_prereq_grade:=NULL;
end;
select gpa into student_gpa from student where sid = sid_input;
if record_gpa is not null and record_gpa < student_gpa then
  should_enroll := 'N';
end if;
if record_pre_cid is not null then
  select 'N' into should_enroll from takes where not exists (select cid from takes where sid = sid_input and cid = record_pre_cid) and ROWNUM = 1;
end if;
if record_sp_perm is not null and record_sp_perm = 'Y' then
  is_sp_perm := 'Y';
end if;
if record_prereq_grade is not null then
  begin
    select grade_point into course_grade_point from takes inner join grading on takes.GRADE = grading.GRADE where takes.sid = sid_input and takes.cid = record_pre_cid;
    exception
    when NO_DATA_FOUND then
      should_enroll := 'N';
  end;
  select grade_point into record_prereq_grade_point from grading where grade = record_prereq_grade;
  if course_grade_point <> 'NULL' and course_grade_point < (record_prereq_grade_point) then
    should_enroll := 'N';
  end if;
end if;
if should_enroll = 'Y' then
  --Check waitlist conditions
  select classsize , waitlist_size into class_size, waitlist_size from offers where cid = cid_input and semid = semid_input and sessionid = SESSIONID_INPUT;
  dbms_output.put_line(people_count);
  select count(*) into people_count from takes where cid = cid_input and semid = semid_input and sessionid = sessionid_input and status in ('Confirmed','Pending','Waitlist');
  if people_count >= class_size then
    if people_count >= class_size + waitlist_size then
      status := 'Waitlist full. Student Cannot be enrolled.';
    else
      if is_sp_perm = 'Y' then
        insert into takes(cid,sid,semid,credits,status, sessionid) VALUES (CID_INPUT, SID_INPUT, SEMID_INPUT, CREDITS_INPUT, 'Pending',SESSIONID_INPUT);
        status := concat('Enrollment pending Admin approval. Waitlist number ',to_char(waitlist_size+1));
      else
        insert into takes(cid,sid,semid,credits,status,sessionid) VALUES (CID_INPUT, SID_INPUT, SEMID_INPUT, CREDITS_INPUT, 'Waitlist', SESSIONID_INPUT);
        status := concat('Enrolled into Waitlist. Waitlist number ',to_char(waitlist_size+1));
      end if;
    end if;
  else 
    if is_sp_perm = 'Y' then
        insert into takes(cid,sid,semid,credits,status, SESSIONID) VALUES (CID_INPUT, SID_INPUT, SEMID_INPUT, CREDITS_INPUT, 'Pending', sessionid_input);
        status := 'Enrolled in course pending Admin approval.';
      else
        insert into takes(cid,sid,semid,credits,status, sessionid) VALUES (CID_INPUT, SID_INPUT, SEMID_INPUT, CREDITS_INPUT, 'Confirmed', sessionid_input);
        STATUS := 'Enrollment Confirmed';
    end if;
  end if;
else 
  status := 'Pre-Requisite Condition not met.';
end if;
dbms_output.put_line(status);
END ENROLL_COURSE;

/
--------------------------------------------------------
--  DDL for Procedure ENROLL_STUDENT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."ENROLL_STUDENT" 
(
  SID_INPUT IN VARCHAR2 
, LNAME_INPUT IN VARCHAR2
, FNAME_INPUT IN VARCHAR2
, EMAIL_INPUT IN VARCHAR2
--, PWD_INPUT IN VARCHAR2
, SLEVEL_INPUT IN VARCHAR2
, RESIDENCY_INPUT IN VARCHAR2
, UNAME_INPUT IN VARCHAR2
, STATUS OUT VARCHAR2
) AS 
cnt number;
BEGIN
select count(*) into cnt from student where sid = SID_INPUT;
  if (cnt <> 0) THEN
    STATUS := 'Not Done';
  else
    Insert into student (sid, lname, fname, email, pwd, slevel, residency, uname) values (sid_input, lname_input, fname_input, email_input, 'password', slevel_input, residency_input, uname_input);
    STATUS := 'Done';
  end if;
END ENROLL_STUDENT;

/
--------------------------------------------------------
--  DDL for Procedure SAMPLE_PROC
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."SAMPLE_PROC" 
is
v_no number(4);
begin
select eid into v_no from admin where eid = '1111';
dbms_output.put_line(v_no);
end;

/
--------------------------------------------------------
--  DDL for Procedure SAMPLE_QUERY_1
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_1" 
(
nme out sys_refcursor
) as
reqd_gpa number;
reqd_cid varchar2(242);
BEGIN
  select gpa , pre_cid into reqd_gpa, reqd_cid from PRECONDITION where cid = 'CS521';
  open nme for select concat(concat(fname, ' '),lname) as Name from student 
  inner join takes on student.sid = takes.sid 
  where student.gpa >= reqd_gpa 
  and takes.cid = reqd_cid
  and takes.status in ('Confirmed','Graded');
END SAMPLE_QUERY_1;

/
--------------------------------------------------------
--  DDL for Procedure SAMPLE_QUERY_2
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_2" (
names out sys_refcursor
)AS 
BEGIN
open names for 
select sid, (concat(concat(fname,' '),lname)) as name 
from student 
inner join 
(select takes.sid as id, ROUND((sum(grading.GRADE_POINT*takes.CREDITS)/sum(takes.credits)),2) as fall_grade
  from takes 
  inner join grading on grading.GRADE = takes.GRADE 
  where takes.semid = 'F2016' group by takes.sid) on sid = id where fall_grade > gpa
  ;
END SAMPLE_QUERY_2;

/
--------------------------------------------------------
--  DDL for Procedure SAMPLE_QUERY_3
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."SAMPLE_QUERY_3" 
(
ot out sys_refcursor
)
AS 
BEGIN
  open ot for SELECT TITLE, SEMID as SEMESTER, COUNT(*) As "Number of Students" FROM COURSE, TAKES WHERE COURSE.CID = TAKES.CID group by TITLE, SEMID ORDER BY TITLE, SEMID;
END SAMPLE_QUERY_3;

/
--------------------------------------------------------
--  DDL for Procedure VIEW_PROFILE_ADMIN
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "BKMUKHEJ"."VIEW_PROFILE_ADMIN" 
(
  EID_INPUT IN VARCHAR2
, EID_OUTPUT OUT VARCHAR2
, USERNAME_OUTPUT OUT VARCHAR2 
, FNAME_OUTPUT OUT VARCHAR2 
, LNAME_OUTPUT OUT VARCHAR2 
, DOB_OUTPUT OUT VARCHAR2 
) AS 
BEGIN
  SELECT A.EID, A.USERNAME, A.FNAME, A.LNAME, A.DOB INTO EID_OUTPUT, USERNAME_OUTPUT, FNAME_OUTPUT, LNAME_OUTPUT, DOB_OUTPUT FROM ADMIN A WHERE A.EID = EID_INPUT;
END VIEW_PROFILE_ADMIN;

/
--------------------------------------------------------
--  DDL for Synonymn DBMS_OUTPUT
--------------------------------------------------------

  CREATE OR REPLACE PUBLIC SYNONYM "DBMS_OUTPUT" FOR "SYS"."DBMS_OUTPUT";
--------------------------------------------------------
--  Constraints for Table ADMIN
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."ADMIN" ADD CONSTRAINT "ADMIN_PK" PRIMARY KEY ("EID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."ADMIN" MODIFY ("EID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."COURSE" ADD CONSTRAINT "COURSE_PK" PRIMARY KEY ("CID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."COURSE" MODIFY ("CID" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."COURSE" MODIFY ("DID" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."COURSE" MODIFY ("TITLE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CREDITCOSTLIMITS
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" ADD CONSTRAINT "CREDITCOSTLIMITS_UK1" UNIQUE ("CLEVEL", "RESIDENCY")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" MODIFY ("COST_PER_CREDIT" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" MODIFY ("RESIDENCY" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."CREDITCOSTLIMITS" MODIFY ("CLEVEL" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GRADING
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."GRADING" ADD CONSTRAINT "GRADING_PK" PRIMARY KEY ("GRADE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."GRADING" MODIFY ("GRADE_POINT" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."GRADING" MODIFY ("GRADE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OFFERS
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."OFFERS" ADD CONSTRAINT "OFFERS_PK" PRIMARY KEY ("SESSIONID", "CID", "SEMID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."OFFERS" ADD CONSTRAINT "OFFERS_UK1" UNIQUE ("CID", "SESSIONID", "SEMID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."OFFERS" MODIFY ("CID" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."OFFERS" MODIFY ("SEMID" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."OFFERS" MODIFY ("SESSIONID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRECONDITION
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."PRECONDITION" ADD CONSTRAINT "PRECONDITION_UK1" UNIQUE ("CID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."PRECONDITION" MODIFY ("CID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SEMESTER
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."SEMESTER" ADD CONSTRAINT "SEMESTER_PK" PRIMARY KEY ("SEMID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."SEMESTER" MODIFY ("IS_DEADLINE_ENFORCED" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."SEMESTER" MODIFY ("SEMID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."STUDENT" ADD CONSTRAINT "STUDENT_PK" PRIMARY KEY ("SID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."STUDENT" MODIFY ("SID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TAKES
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."TAKES" MODIFY ("SID" NOT NULL ENABLE);
 
  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_UK1" UNIQUE ("SID", "CID", "SEMID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table OFFERS
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."OFFERS" ADD CONSTRAINT "OFFERS_FK1" FOREIGN KEY ("SEMID")
	  REFERENCES "BKMUKHEJ"."SEMESTER" ("SEMID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRECONDITION
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."PRECONDITION" ADD CONSTRAINT "PRECONDITION_FK1" FOREIGN KEY ("PRE_CID")
	  REFERENCES "BKMUKHEJ"."COURSE" ("CID") ON DELETE CASCADE ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."PRECONDITION" ADD CONSTRAINT "PRECONDITION_FK2" FOREIGN KEY ("CID")
	  REFERENCES "BKMUKHEJ"."COURSE" ("CID") ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."PRECONDITION" ADD CONSTRAINT "PRECONDITION_FK3" FOREIGN KEY ("PREREQ_GRADE")
	  REFERENCES "BKMUKHEJ"."GRADING" ("GRADE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."STUDENT" ADD CONSTRAINT "STUDENT_FK1" FOREIGN KEY ("SLEVEL", "RESIDENCY")
	  REFERENCES "BKMUKHEJ"."CREDITCOSTLIMITS" ("CLEVEL", "RESIDENCY") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TAKES
--------------------------------------------------------

  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_FK1" FOREIGN KEY ("CID", "SESSIONID", "SEMID")
	  REFERENCES "BKMUKHEJ"."OFFERS" ("CID", "SESSIONID", "SEMID") ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_FK2" FOREIGN KEY ("SID")
	  REFERENCES "BKMUKHEJ"."STUDENT" ("SID") ON DELETE CASCADE ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_FK3" FOREIGN KEY ("GRADE")
	  REFERENCES "BKMUKHEJ"."GRADING" ("GRADE") ON DELETE CASCADE ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_FK4" FOREIGN KEY ("SEMID")
	  REFERENCES "BKMUKHEJ"."SEMESTER" ("SEMID") ENABLE;
 
  ALTER TABLE "BKMUKHEJ"."TAKES" ADD CONSTRAINT "TAKES_FK5" FOREIGN KEY ("CID")
	  REFERENCES "BKMUKHEJ"."COURSE" ("CID") ENABLE;
