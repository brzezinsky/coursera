create view keyword as select * from Frequency 
union
	select 'q' as docid, 'washington' as term, 1 as count
union 
	select 'q' as docid, 'tax' as term, 1 as count
union 
	select 'q' as docid, 'treasury' as term, 1 as count;


select max(similarity) from (select a.docid, b.docid, sum(a.count * b.count) as similarity from keyword a, keyword b 
	where a.term = b.term and b.docid = 'q' group by a.docid, b.docid;

drop view keyword;
