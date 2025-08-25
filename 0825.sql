create table 고객(
    고객아이디 VARCHAR(20) not null primary key,
    고객이름 VARCHAR(10) not null,
    나이 INT,
    등급 VARCHAR(10) not null,
    직업 VARCHAR(20),
    적립금 INT DEFAULT 0
);

create table 제품(
    제품번호 varchar(5) not null,
    제품명 varchar(20),
    재고량 int,
    단가 int,
    제조업체 varchar(20),
    primary key(제품번호),
    check(재고량>=0 and 재고량<=10000)
);

CREATE TABLE 주문(
    주문번호 VARCHAR(5) not null,
    주문고객 VARCHAR(20),
    주문제품 VARCHAR(5),
    수량 int,
    배송지 varchar(30),
    주문일자 date,
    PRIMARY KEY(주문번호),
    foreign key(주문고객) references 고객(고객아이디),
    foreign key(주문제품) references 제품(제품번호)
);

create table 배송업체(
    업체번호 varchar(5) not null primary key,
    업체명 varchar(20),
    주소 varchar(100),
    전화번호 varchar(20)
);
--테이블 변경
ALTER TABLE 고객 
                add 가입날짜 date;

alter table 고객
                drop column 가입날짜;
                
--테이블 변경: 체크 제약조건 추가      

alter table 고객
                add CONSTRAINT check_age check(나이>=20);
                
--테이블 변경: 체크 제약조건 삭제      

alter table 고객
                drop CONSTRAINT check_age;
                
--테이블 삭제

drop table 배송업체;

-- DML(데이터 조작어)

-- 고객 테이블에 행 삽입(추가)

INSERT INTO 고객 VALUES('apple', '정소화', 20, 'gold', '학생', 1000);
INSERT INTO 고객 VALUES('banana', '김선우', 25, 'vip', '간호사', 2500);
INSERT INTO 고객 VALUES('carrot', '고명석', 28, 'gold', '교사', 3500);
INSERT INTO 고객 VALUES('orange', '김용욱', 22, 'silver', '학생', 0);
INSERT INTO 고객 VALUES('melon', '성원용', 35, 'gold', '회사원', 5000);
INSERT INTO 고객(고객아이디, 고객이름, 등급, 직업, 적립금) VALUES('peach', '오형준', 'silver', '의사', 300);
INSERT INTO 고객 VALUES('pear', '채광주', 31, 'silver', '회사원', 500);

--고객테이블의 전체 조회(검색)

SELECT * FROM 고객;


