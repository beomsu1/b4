# 상품 태이블
create table tbl_product(
	pno int auto_increment primary key,
	pname varchar(500) not null,
	price int default 0,
	status boolean default true,
	regDate timestamp default now(),
	modDate timestamp default now()
)
;

# 초기 값
insert into tbl_product (pname, price) values ('product',1000)
;

# 재귀로 호출
insert into tbl_product (pname, price) (select pname, price from tbl_product)
;

# 상품 이름 변경
update tbl_product  set pname = concat(pname, pno) where pno > 0
;

#
update tbl_product  set price = price * mod(pno,10) where pno > 0
;

select count(*)from tbl_product;

select *from tbl_product order by pno desc ;

################################################## tbl_product

## tbl_product_img 1717 1716 1715 1714 1713
## 첨부파일 테이블
create table tbl_product_img(
	uuid varchar(50) primary key,
	filename varchar(200) not null,
	pno int not null,
	ord int default 0
);

select uuid()
;


## uuid 생성 / filename pno ord insert
insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f1.jpg',1717, 0);
insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f2.jpg',1717, 1);

insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f3.jpg',1716, 0);
insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f4.jpg',1716, 1);

insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f3.jpg',1715, 0);
insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f4.jpg',1716, 1);


insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f5.jpg',1715, 0);
insert into tbl_product_img(uuid, filename, pno, ord) values(uuid(), 'f6.jpg',1715, 1);


## product 테이블과 파일 테이블 join => 일반적인 join 
select p.pno, p.pname, p.price, p.status, concat(pi.uuid,'_',pi.fileName) fileName from tbl_product p 
left outer join tbl_product_img pi on pi.pno = p.pno 
where pi.ord = 0 or pi.ord is null
order by p.pno desc 
limit 0, 10
;


## product 테이블 limit 후 범위를 줄여서 img 테이블과 join
select *
from 
	(select *from tbl_product p where p.pno > 0 order by pno desc limit 0, 10) p2
	left outer join tbl_product_img pi on pi.pno = p2.pno
	where pi.ord = 0 or pi.ord is null
	order by p2.pno desc
;


## 
select *
from tbl_product p


## create index to tbl_product_img
create index idx_product_image1 on tbl_product_img (pno desc, ord asc)
;





select *from tbl_product_img order by pno desc, ord asc
;



