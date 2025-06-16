create user membertest identified by membertest
grant resource, connect to membertest
alter user membertest default tablespace users
alter user membertest temporary tablespace temp

