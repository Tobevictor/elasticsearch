select id,title,type,pid,sindex,to_date(createdate) as ceate,isdelete,to_date(deletetime) as deletet,code from resourcedirectory;
select id,title,type,pid,sindex,createdate,isdelete,deletetime,code from resourcedirectory;

select id,count(*) from resourcedirectory group by id having count(*)=1;