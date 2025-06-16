-- 일반 사용자 계정용 sql 쿼리 메모장

-- 블럭설정하고 alt + c로 실행

create table emp(
	num number(5) primary key, -- num 필드 숫자 5개 타입, 기본키
	name varchar2(16) not null, -- name 필드 문자 16개 타입, nn
	dept varchar2(16), -- dept필드 문자 16게 타입
	score number(7,2) -- socre 숫자 7개중 소수점 2개
) -- emp 테이블 생성

drop table emp -- 테이블 삭제용

create sequence emp_seq increment by 1 start with 1 nocache nocycle --자동번호생성
drop table sequence emp_seq

insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름1', '부서1', 75.3)
insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름2', '부서2', 85.3)
insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름3', '부서3', 95.3)
insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름4', '부서4', 65.3)
insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름5', '부서5', 55.3)
insert into emp(num, name, dept, score) values(emp_seq.nextval, '이름6', '부서6', 78.3)
-- 더미데이터 6개 입력

select * from emp; -- 입력결과 보기
