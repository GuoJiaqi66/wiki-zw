/*
点赞数，观看数更新sql
*/
update doc set view_count = view_count + 1 where id = #{id}
update doc set vote_count = vote_count + 1 where id = #{id}


/*
统计一个电子书数据sql
*/
select count(1), sum(view_count), sum(vote_count) from doc where ebook_id = 1;
/*
统计全部电子书数据：分组查询
*/
select ebook_id, count(1), sum(view_count), sum(vote_count) from doc group by ebook_id;
/*
将统计到的数据全部保存到：ebook表中
更新ebook数据的话，只需要执行这个sql语句
*/
update ebook t1, (select ebook_id, count(1) doc_count, sum(view_count) view_count, sum(vote_count) vote_count from doc group by ebook_id) t2
set t1.doc_count = t2.doc_count, t1.view_count = t2.view_count, t1.vote_count = t2.vote_count
where t1.id = t2.ebook_id;