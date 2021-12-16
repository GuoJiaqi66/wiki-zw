/*
一个分类表  两级
*/
drop table if exists `category`;
create table `category` (
    `id` bigint not null comment 'id',
    `parent` bigint not null default 0 comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key(`id`)
) engine=innodb default charset=utf8mb4 comment='分类';


insert into `category`(id,parent,name,sort) values(100,000,'前端开发',100);
insert into `category`(id,parent,name,sort) values(101,100,'vue',101);
insert into `category`(id,parent,name,sort) values(102,100,'Html & Css',102);

insert into `category`(id,parent,name,sort) values(200,000,'Java',200);
insert into `category`(id,parent,name,sort) values(201,200,'基础应用',201);
insert into `category`(id,parent,name,sort) values(202,200,'框架应用',202);

insert into `category`(id,parent,name,sort) values(300,000,'Python',300);
insert into `category`(id,parent,name,sort) values(301,300,'基础应用',301);
insert into `category`(id,parent,name,sort) values(302,300,'进阶方向应用',302);

insert into `category`(id,parent,name,sort) values(400,000,'数据库',400);
insert into `category`(id,parent,name,sort) values(401,400,'MySQL',401);
insert into `category`(id,parent,name,sort) values(402,400,'其他',402);

insert into `category`(id,parent,name,sort) values(500,000,'服务器',500);
insert into `category`(id,parent,name,sort) values(501,500,'开发工具',501);
insert into `category`(id,parent,name,sort) values(502,500,'热门服务端语言',502);

