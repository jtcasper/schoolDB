CREATE USER schooldb IDENTIFIED by "root";
CREATE TABLESPACE schooldb DATAFILE 'C:\oraclexe\app\oracle\oradata\xe\schooldb.dbf'
SIZE 50M EXTENT MANAGEMENT LOCAL AUTOALLOCATE;
ALTER USER schooldb
default tablespace schooldb
temporary tablespace temp;
ALTER USER schooldb QUOTA 100M on schooldb;
GRANT CREATE SESSION TO schooldb WITH ADMIN OPTION;
GRANT CREATE TABLE TO schooldb;
exit