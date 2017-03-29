--------------------------------------------------------
--  File created - Wednesday-March-29-2017   
--------------------------------------------------------
DROP TABLE "ADMIN" cascade constraints;
DROP TABLE "COURSE" cascade constraints;
DROP TABLE "CREDITCOSTLIMITS" cascade constraints;
DROP TABLE "GRADING" cascade constraints;
DROP TABLE "OFFERS" cascade constraints;
DROP TABLE "PENDING" cascade constraints;
DROP TABLE "PRECONDITION" cascade constraints;
DROP TABLE "REQUIRES" cascade constraints;
DROP TABLE "SEMESTER" cascade constraints;
DROP TABLE "STUDENT" cascade constraints;
DROP TABLE "TAKES" cascade constraints;
DROP TABLE "WAITLIST" cascade constraints;
DROP TABLE "WAITLISTING" cascade constraints;
--------------------------------------------------------
--  DDL for Table ADMIN
--------------------------------------------------------

  CREATE TABLE "ADMIN" 
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
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "COURSE" 
   (	"CID" VARCHAR2(20 BYTE), 
	"TITLE" VARCHAR2(50 BYTE), 
	"CREDITS" VARCHAR2(20 BYTE), 
	"CLEVEL" VARCHAR2(20 BYTE), 
	"DID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;

   COMMENT ON COLUMN "COURSE"."CLEVEL" IS 'Course Level
';
--------------------------------------------------------
--  DDL for Table CREDITCOSTLIMITS
--------------------------------------------------------

  CREATE TABLE "CREDITCOSTLIMITS" 
   (	"CLEVEL" VARCHAR2(20 BYTE), 
	"RESIDENCY" VARCHAR2(20 BYTE), 
	"COST_PER_CREDIT" NUMBER, 
	"MINCREDITS" NUMBER, 
	"MAXCREDITS" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table GRADING
--------------------------------------------------------

  CREATE TABLE "GRADING" 
   (	"GRADE" VARCHAR2(20 BYTE), 
	"GRADE_POINT" FLOAT(126)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table OFFERS
--------------------------------------------------------

  CREATE TABLE "OFFERS" 
   (	"OFFERID" VARCHAR2(20 BYTE), 
	"SCHEDULE" VARCHAR2(20 BYTE), 
	"LOCATION" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE), 
	"SEMID" VARCHAR2(20 BYTE), 
	"FID" VARCHAR2(20 BYTE), 
	"CLASSSIZE" NUMBER DEFAULT 0, 
	"WAITSIZE" NUMBER DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;

   COMMENT ON COLUMN "OFFERS"."FID" IS 'Faculty Id';
--------------------------------------------------------
--  DDL for Table PENDING
--------------------------------------------------------

  CREATE TABLE "PENDING" 
   (	"PSID" VARCHAR2(20 BYTE), 
	"PCID" VARCHAR2(20 BYTE), 
	"STATUS" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table PRECONDITION
--------------------------------------------------------

  CREATE TABLE "PRECONDITION" 
   (	"PREREQID" VARCHAR2(20 BYTE), 
	"PRE_CID" VARCHAR2(20 BYTE), 
	"GPA" VARCHAR2(20 BYTE), 
	"SPPERM" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;

   COMMENT ON COLUMN "PRECONDITION"."SPPERM" IS 'Special Permissions';
--------------------------------------------------------
--  DDL for Table REQUIRES
--------------------------------------------------------

  CREATE TABLE "REQUIRES" 
   (	"CID" VARCHAR2(20 BYTE), 
	"REQUIRES" VARCHAR2(20 BYTE), 
	"PREREQID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table SEMESTER
--------------------------------------------------------

  CREATE TABLE "SEMESTER" 
   (	"SEMID" VARCHAR2(20 BYTE), 
	"YEAR" NUMBER, 
	"DEADLINE" VARCHAR2(20 BYTE), 
	"SEMESTER" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "STUDENT" 
   (	"SID" VARCHAR2(20 BYTE), 
	"LNAME" VARCHAR2(20 BYTE), 
	"FNAME" VARCHAR2(20 BYTE), 
	"EMAIL" VARCHAR2(20 BYTE), 
	"PWD" VARCHAR2(20 BYTE) DEFAULT NULL, 
	"GPA" VARCHAR2(20 BYTE), 
	"SLEVEL" VARCHAR2(20 BYTE), 
	"BILL" VARCHAR2(20 BYTE) DEFAULT 0, 
	"DID" VARCHAR2(20 BYTE), 
	"RESIDENCY" VARCHAR2(20 BYTE), 
	"UNAME" VARCHAR2(20 BYTE), 
	"DOB" DATE, 
	"CREDITS" NUMBER DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;

   COMMENT ON COLUMN "STUDENT"."CREDITS" IS 'Credits already taken';
--------------------------------------------------------
--  DDL for Table TAKES
--------------------------------------------------------

  CREATE TABLE "TAKES" 
   (	"SID" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE), 
	"GRADE" VARCHAR2(2 BYTE), 
	"CREDITS" NUMBER(1,0) DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;

   COMMENT ON COLUMN "TAKES"."CREDITS" IS 'The credits of the course which counted';
--------------------------------------------------------
--  DDL for Table WAITLIST
--------------------------------------------------------

  CREATE TABLE "WAITLIST" 
   (	"WSIZE" VARCHAR2(20 BYTE), 
	"PCID" VARCHAR2(20 BYTE), 
	"CID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Table WAITLISTING
--------------------------------------------------------

  CREATE TABLE "WAITLISTING" 
   (	"PSID" VARCHAR2(20 BYTE), 
	"SID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index ADMIN_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ADMIN_PK" ON "ADMIN" ("EID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index COURSE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "COURSE_PK" ON "COURSE" ("CID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index CREDITCOSTLIMITS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "CREDITCOSTLIMITS_UK1" ON "CREDITCOSTLIMITS" ("CLEVEL", "RESIDENCY") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index GRADING_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "GRADING_PK" ON "GRADING" ("GRADE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index OFFERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "OFFERS_PK" ON "OFFERS" ("OFFERID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index OFFERS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "OFFERS_UK1" ON "OFFERS" ("SCHEDULE", "LOCATION", "CID", "SEMID", "FID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index PRECONDITION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PRECONDITION_PK" ON "PRECONDITION" ("PREREQID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index SEMESTER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SEMESTER_PK" ON "SEMESTER" ("SEMID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  DDL for Index STUDENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "STUDENT_PK" ON "STUDENT" ("SID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB" ;
--------------------------------------------------------
--  Constraints for Table ADMIN
--------------------------------------------------------

  ALTER TABLE "ADMIN" ADD CONSTRAINT "ADMIN_PK" PRIMARY KEY ("EID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "ADMIN" MODIFY ("EID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "COURSE" ADD CONSTRAINT "COURSE_PK" PRIMARY KEY ("CID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "COURSE" MODIFY ("CID" NOT NULL ENABLE);
  ALTER TABLE "COURSE" MODIFY ("DID" NOT NULL ENABLE);
  ALTER TABLE "COURSE" MODIFY ("TITLE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CREDITCOSTLIMITS
--------------------------------------------------------

  ALTER TABLE "CREDITCOSTLIMITS" ADD CONSTRAINT "CREDITCOSTLIMITS_UK1" UNIQUE ("CLEVEL", "RESIDENCY")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "CREDITCOSTLIMITS" MODIFY ("COST_PER_CREDIT" NOT NULL ENABLE);
  ALTER TABLE "CREDITCOSTLIMITS" MODIFY ("RESIDENCY" NOT NULL ENABLE);
  ALTER TABLE "CREDITCOSTLIMITS" MODIFY ("CLEVEL" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table GRADING
--------------------------------------------------------

  ALTER TABLE "GRADING" ADD CONSTRAINT "GRADING_PK" PRIMARY KEY ("GRADE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "GRADING" MODIFY ("GRADE_POINT" NOT NULL ENABLE);
  ALTER TABLE "GRADING" MODIFY ("GRADE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table OFFERS
--------------------------------------------------------

  ALTER TABLE "OFFERS" ADD CONSTRAINT "OFFERS_PK" PRIMARY KEY ("OFFERID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "OFFERS" MODIFY ("OFFERID" NOT NULL ENABLE);
  ALTER TABLE "OFFERS" ADD CONSTRAINT "OFFERS_UK1" UNIQUE ("SCHEDULE", "LOCATION", "CID", "SEMID", "FID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
--------------------------------------------------------
--  Constraints for Table PRECONDITION
--------------------------------------------------------

  ALTER TABLE "PRECONDITION" ADD CONSTRAINT "PRECONDITION_PK" PRIMARY KEY ("PREREQID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "PRECONDITION" MODIFY ("PREREQID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SEMESTER
--------------------------------------------------------

  ALTER TABLE "SEMESTER" ADD CONSTRAINT "SEMESTER_PK" PRIMARY KEY ("SEMID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "SEMESTER" MODIFY ("SEMID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "STUDENT" ADD CONSTRAINT "STUDENT_PK" PRIMARY KEY ("SID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SCHOOLDB"  ENABLE;
  ALTER TABLE "STUDENT" MODIFY ("SID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TAKES
--------------------------------------------------------

  ALTER TABLE "TAKES" MODIFY ("SID" NOT NULL ENABLE);
  ALTER TABLE "TAKES" MODIFY ("CID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table OFFERS
--------------------------------------------------------

  ALTER TABLE "OFFERS" ADD CONSTRAINT "OFFERS_FK1" FOREIGN KEY ("CID")
	  REFERENCES "COURSE" ("CID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "OFFERS" ADD CONSTRAINT "OFFERS_FK2" FOREIGN KEY ("SEMID")
	  REFERENCES "SEMESTER" ("SEMID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PRECONDITION
--------------------------------------------------------

  ALTER TABLE "PRECONDITION" ADD CONSTRAINT "PRECONDITION_FK1" FOREIGN KEY ("PRE_CID")
	  REFERENCES "COURSE" ("CID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table REQUIRES
--------------------------------------------------------

  ALTER TABLE "REQUIRES" ADD CONSTRAINT "REQUIRES_FK1" FOREIGN KEY ("CID")
	  REFERENCES "COURSE" ("CID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "REQUIRES" ADD CONSTRAINT "REQUIRES_FK2" FOREIGN KEY ("PREREQID")
	  REFERENCES "PRECONDITION" ("PREREQID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TAKES
--------------------------------------------------------

  ALTER TABLE "TAKES" ADD CONSTRAINT "TAKES_FK1" FOREIGN KEY ("CID")
	  REFERENCES "COURSE" ("CID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "TAKES" ADD CONSTRAINT "TAKES_FK2" FOREIGN KEY ("SID")
	  REFERENCES "STUDENT" ("SID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "TAKES" ADD CONSTRAINT "TAKES_FK3" FOREIGN KEY ("GRADE")
	  REFERENCES "GRADING" ("GRADE") ON DELETE CASCADE ENABLE;
