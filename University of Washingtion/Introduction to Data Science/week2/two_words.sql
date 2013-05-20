select count(*) from (select docid from Frequency where term='transactions' intersect select docid from Frequency where term='world');

