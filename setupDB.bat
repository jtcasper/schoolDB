echo off
sqlplus / as sysdba @scripts/setupuser.sql
set /p delExit=Press the ENTER key to exit...:
