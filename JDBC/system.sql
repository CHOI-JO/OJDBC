-- 실행은 블럭설정하녹, alt+c
create user jdbctest identified by jdbctest -- 사용자 계정 생성
grant resource, connect to jdbctest -- 접속권한과 일반 사용자 권한을 줌

alter user jdbctest default tablespace users -- 테이블 스페이스(db)는 users용을 배정
alter user jdbctest temporary tablespace temp -- 임시용 db는 temp 용으로 배정

