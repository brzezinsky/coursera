select count(*) from (select docid from Frequency group by docid having sum(count) > 300;
