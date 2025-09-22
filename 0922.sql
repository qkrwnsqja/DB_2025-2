-- 고명석 고객이 주문한 제품의 고객아이디, 제품명, 단가를 검색하시오.
select 고객아이디, 제품명, 단가 from 고객 c, 주문 o, 제품 p
                            where c.고객이름 = '고명석' and c.고객아이디 = o.주문고객 and p.제품번호 = o.주문제품;

-- 자연조인
-- 나이가 30세 이상인 고객이 주문한 제품의 주문제품과 주문일자를 검색하시오.
select 주문제품, 주문일자 from 고객, 주문 
                        where 나이 >= 30 and 고객아이디=주문고객;

-- 내부조인
select 주문제품, 주문일자 from 고객 inner join 주문 on 고객아이디=주문고객
                        where 나이 >= 30;
        
-- 외부조인
-- 주문하지 않은 고객도 포함해서 고객이름과 주문제품, 주문일자를 검색하시오.
-- 왼쪽 외부조인(Left OuterJoin)
select 고객이름, 주문제품, 주문일자 from 고객 left outer join 주문 on 고객아이디=주문고객;
-- 오른쪽 외부조인(Right OuterJoin)
select 고객이름, 주문제품, 주문일자 from 주문 right outer join 고객 on 고객아이디=주문고객;

-- Sub Query
-- 달콤비스킷을 생산한 제조업체가 만든 제품들의 제품명과 단가를 검색하시오.
select 제조업체 from 제품
                where 제품명='달콤비스킷';

select 제품명, 단가 from 제품 
                    where 제조업체=(
                                select 제조업체 from 제품
                                                where 제품명='달콤비스킷'
                    );
                    
-- 주문테이블에서 쿵떡파이를 주문한 주문고객, 주문제품, 수량을 검색하시오.
select * from 주문;
select * from 제품;
select 제품번호 from 제품
                where 제품명 = '쿵떡파이';
select 주문고객, 주문제품, 수량 from 주문 
                            where 주문제품 = (
                                            select 제품번호 from 제품 
                                                            where 제품명 = '쿵떡파이'
                            );
                            
-- 적립금이 가장 많은 고객의 고객이름과 적립금을 검색하시오.
select max(적립금) from 고객;
select 고객이름, 적립금 from 고객
                        where 적립금=(
                                        select max(적립금) from 고객
                        );
                        
-- 적립금이 가장 적은 고객의 고객이름과 적립금을 검색하시오.      
select * from 고객;
select min(적립금) from 고객;
select 고객이름, 적립금 from 고객 
                        where 적립금=(
                                        select min(적립금) from 고객
                        );
                        
-- 다중행 결과를 나타내는 Sub Query(비교연산자 사용불가능)
-- banana 고객이 주문한 제품의 제품번호, 제품명, 제조업체를 검색하시오.
select * from 주문;
select 주문제품 from 주문
            where 주문고객='banana';
            
select 제품번호, 제품명, 제조업체 from 제품
                                where 제품번호 in( 
                                    select 주문제품 from 주문
                                        where 주문고객='banana'
                                );
-- SubQuery 다중행결과에 사용하는 IN 연산자                             
-- 김씨 성을 가진 고객이 주문한 고객의 고객아이디, 나이, 적립금, 제품명, 단가를 검색하시오.
select 고객이름, 고객아이디, 나이 from 고객 where 고객이름 like '김%';
select 고객아이디, 나이, 적립금, 제품명, 단가 from 고객, 제품, 주문
                                     where 고객아이디=주문고객
                                             and 제품번호=주문제품;

select 고객아이디, 나이, 적립금, 제품명, 단가 from 고객, 제품, 주문
                                     where 고객아이디=주문고객
                                             and 제품번호=주문제품
                                                 and 주문고객 in(
                                                               select 고객아이디 from 고객 where 고객이름 like '김%'
                                                 );
                                                 
-- SubQuery 다중행결과에 사용하는 Not IN 연산자
-- banana고객이 주문하지 않은 제품의 제품명, 제조업체를 검색하시오.
select 주문고객, 주문제품 from 주문; 
select 제품명, 제조업체 from 제품;
select 주문제품 from 주문 where 주문고객='banana';
select 제품번호, 제품명, 제조업체 from 제품
                        where 제품번호 Not In(select 주문제품 from 주문 where 주문고객='banana');

-- 대한식품이 제조한 모든 제품의 단가보다 비싼 제품의 제품명, 단가, 제조업체를 검색하시오.

select 제품명, 단가, 제조업체 from 제품 where 제조업체='대한식품';
select 제품명, 단가, 제조업체 from 제품;

select 제품명, 단가, 제조업체 from 제품 where 단가 > All(select 단가 from 제품 where 제조업체='대한식품');

-- 2022년 3월 15일에 제품을 주문한 고객의 고객이름 검색해보자.
select * from 주문;
select 주문고객 from 주문 where 주문일자 = '2022-03-15';

select 고객이름 from 고객 
                where 고객아이디 = (
                             select 주문고객 from 주문 where 주문일자='2022-03-15'
                );
                
-- 2022년 1월 1일에 제품을 주문한 고객의 고객이름 검색해보자.
select 고객이름 from 고객 
                where Exists(
                             select 주문고객 from 주문 where 주문일자='2022-01-01' and 주문고객 = 고객아이디
                );      
                
-- 2022년 1월 1일에 제품을 주문한 고객이 아닌 고객이름 검색해보자.(여러명인경우)
select 고객이름 from 고객
                where Not Exists(
                             select 주문고객 from 주문 where 주문일자='2022-01-01' and 주문고객=고객아이디
                );             