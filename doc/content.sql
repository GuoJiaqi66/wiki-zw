/*
文档内容  mediumtext媒体文本即html
id doc的id
content 大字段，可重新建张表

纵向分表， 将一张表分为两部分
横向分表，例如 一月份，二月份 数据库数据增加，可使用这种
*/
drop table if exists `content`;
create table `content` (
    `id` bigint not null comment '文档id',
    `content` mediumtext not null comment '内容',
    primary key (`id`)
) engine = innodb default charset = utf8mb4 comment='文档内容';