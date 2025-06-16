create table member(
mno number(20) not null,
id nvarchar2(30) primary key,
pw nvarchar2(10) not null,
mname nvarchar2(20) not null,
birthday number(20) not null,
phonenumber nvarchar2(50) not null,
email nvarchar2(50) not null,
address nvarchar2(50) not null,
regidate date default sysdate not null
)
create sequence board_seq increment by 1 start with 1 nocycle nocache

drop table member -- member 테이블 삭제

insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'kkw','1234', '김기원', '000101', '010-1111-1111', 'aaaaa@gmail.com', '수원시 영통구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'yjs', '1234', '윤제석','010202', '010-2222-2222', 'bbbbb@gmail.com', '수원시 장안구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'lej', '1234', '이은진', '030303', '010-3333-3333', 'ccccc@gmail.com', '용인시 기흥구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'cjo', '1234', '최준오', '050505', '010-5555-5555', 'ddddd@gmail.com', '서울시 동작구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'ljs', '1234', '이재상', '060606', '010-6666-6666', 'eeeee@gmail.com', '서울시 강남구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'kch', '1234', '김채하', '070707', '010-7777-7777', 'fffff@gmail.com', '수원시 권선구')
insert into member (mno, id, pw, mname, birthday, phonenumber, email, address) 
values (board_seq.nextval, 'jea', '1234', '조은아', '080808', '010-8888-8888', 'ggggg@gmail.com', '안양시 동안구')


select * from member


------------------member의 자식 board 외래키 생성 필수-------------------------
drop table board -- 기존에 board 테이블 삭제

create table board(
bno number(5) primary key,
btitle nvarchar2(100) not null,
bcontent nvarchar2(1000) not null,
bwriter nvarchar2(10) null,
bdate date not null
)

alter table board add constraint board_member_fk foreign key (bwriter) references member(id) on delete set null

delete from board

insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '새로운 시작입니다.', '오들은 정말 덥네요', 'kkw', sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '드디어 금요일입니다.', '한 주간도 고생 많았어요', 'kch', sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '최선을 다합시다.', '공부를 통해 새로운 시작이 될거예요', 'cjo', sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '괜찮아요.', '모두 잘 될거예요', 'jo', sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '기분전환이 필요해요~', '한 곳에만 있지 마세요. 기분 전화도 필요해요', 'jea', sysdate)


select * from board;


