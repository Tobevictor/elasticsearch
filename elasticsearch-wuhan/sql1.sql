/*start transaction;
insert into test (username,password,tel,address) values (10,123456,15038093696,'中国武汉');
commit ;*/

create table Student(SId varchar(10),Sname varchar(10),Sage datetime,Ssex varchar(10),PRIMARY key(SId));
insert into Student values('01' , '赵雷' , '1990-01-01' , '男');
insert into Student values('02' , '钱电' , '1990-12-21' , '男');
insert into Student values('03' , '孙风' , '1990-12-20' , '男');
insert into Student values('04' , '李云' , '1990-12-06' , '男');
insert into Student values('05' , '周梅' , '1991-12-01' , '女');
insert into Student values('06' , '吴兰' , '1992-01-01' , '女');
insert into Student values('07' , '郑竹' , '1989-01-01' , '女');
insert into Student values('09' , '张三' , '2017-12-20' , '女');
insert into Student values('10' , '李四' , '2017-12-25' , '女');
insert into Student values('11' , '李四' , '2012-06-06' , '女');
insert into Student values('12' , '赵六' , '2013-06-13' , '女');
insert into Student values('13' , '孙七' , '2014-06-01' , '女');

create table Teacher(TId varchar(10),Tname varchar(10),PRIMARY key (Tid));
insert into Teacher values('01' , '张三');
insert into Teacher values('02' , '李四');
insert into Teacher values('03' , '王五');

create table Course(CId varchar(10),Cname nvarchar(10),TId varchar(10),PRIMARY KEY(CId));
insert into Course values('01' , '语文' , '02');
insert into Course values('02' , '数学' , '01');
insert into Course values('03' , '英语' , '03');

create table SC(skey int ,SId varchar(10),CId varchar(10),score decimal(18,1),PRIMARY key(skey));
insert into SC values('1','01' , '01' , 80);
insert into SC values('2','01' , '02' , 90);
insert into SC values('3','01' , '03' , 99);
insert into SC values('4','02' , '01' , 70);
insert into SC values('5','02' , '02' , 60);
insert into SC values('6','02' , '03' , 80);
insert into SC values('7','03' , '01' , 80);
insert into SC values('8','03' , '02' , 80);
insert into SC values('9','03' , '03' , 80);
insert into SC values('10','04' , '01' , 50);
insert into SC values('11','04' , '02' , 30);
insert into SC values('12','04' , '03' , 20);
insert into SC values('13','05' , '01' , 76);
insert into SC values('14','05' , '02' , 87);
insert into SC values('15','06' , '01' , 31);
insert into SC values('16','06' , '03' , 34);
insert into SC values('17','07' , '02' , 89);
insert into SC values('18','07' , '03' , 98);

1.求每门课程的学生人数。
select Course.cname'课程名称',count(*)'人数' from SC,Course where SC.CId=Course.CId
GROUP BY SC.CId;

2.查询课程编号为 01 且课程成绩在 80 分及以上的学生的学号和姓名
select a.sid,a.sname from Student a ,SC b where a.sid=b.sid
and b.cid='01' and b.score >=80

3.统计每门课程的学生选修人数（超过 5 人的课程才统计）
SELECT Course.cname'课程名称',count(*)'人数' from SC,Course
where SC.CId=Course.CId
group by SC.CId
having count(*) > 5;

4.检索至少选修两门课程的学生学号
SELECT sid,count(CId) from SC group by SId having count(CId)>2;

5.选修了全部课程的学生信息
SELECT a.SId ,a.Sname ,a.Sage ,a.Ssex from Student as a,SC AS b where a.SId = b.SId GROUP BY b.sid  having count(b.cid) = (select count(*) from Course);

6 .查询存在不及格的课程
SELECT DISTINCT a.CId,a.Cname from Course as a,SC as b where b.CId = a.CId and b.score<60;

7.查询任何一门课程成绩在 70 分以上的学生姓名、课程名称和分数
SELECT student.Sname,course.cname,sc.score from SC,Course,Student WHERE SC.SId = Student.SId and Course.CId = SC.CId and SC.score>70;

8.查询所有学生的课程及分数情况（存在学生没成绩，没选课的情况）
#错误
SELECT Student.Sname,Course.Cname,Sc.score from Student,Course,SC where Student.SId = SC.SId and Course.CId = SC.CId;
#正确 左连接 left join
select a.sname,b.cname,c.score from student a left join sc c
on a.sid=c.sid
left join course b
on b.cid = c.cid;

9.查询课程名称为「数学」，且分数低于 60 的学生姓名和分数
SELECT Course.Cname,Student.Sname,SC.score FROM Student,Course,SC WHERE Course.Cname = '数学'
and Student.SId = SC.SId
and SC.CId = Course.CId
and SC.score<60;

10.查询平均成绩大于等于 85 的所有学生的学号、姓名和平均成绩
#正确
SELECT Student.SId,Student.Sname,AVG(SC.score)'平均成绩' from Student,SC
where Student.SId = SC.SId
GROUP BY Student.sid
having avg(SC.score)>=85;
#错误（没有group by ，计算的是全部学生得平均成绩而非某个学生得平均）
SELECT Student.SId,Student.Sname,AVG(SC.score)'平均成绩' from Student,SC
where Student.SId = SC.SId
having avg(SC.score)>=85;

11.查询每门课程的平均成绩，结果按平均成绩降序排列，平均成绩相同时，按课程编号升序排列
SELECT b.cid,b.Cname, avg(c.score) as a from Course as b ,sc as c where b.CId = c.CId group by c.CId order by a desc ,c.CId asc;

12.查询各科成绩最高分、最低分和平均分(以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率

及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90

要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列)
SELECT a.CId,a.Cname,max(b.score)`最高分`,min(b.score)`最低分`,avg(b.score)`平均分`,
sum(case when score <60 then 1 else 0 end)/count(b.cid) `不合格率`,
sum(case when score <80 and score >=60 then 1 else 0 end)/count(b.cid) `合格率`,
sum(case when score <90 and score >=80 then 1 else 0 end)/count(b.cid) `优良率`,
sum(case when score >= 90  then 1 else 0 end)/count(b.cid) `优秀率`
from Course as a ,SC as b
where b.cid =  a.cid
GROUP BY b.cid;

13.查询男生、女生人数
SELECT Ssex,count(Student.Ssex) from Student group by Ssex;

14.检索" 01 "课程分数小于 60，按分数降序排列的学生信息
SELECT Student.* from SC,Student
where SC.SId = Student.SId
and SC.CId = '01'
and SC.score<60
order by SC.score desc ;

15.按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩
###right join ###

SELECT a.SId,a.score,b.`平均成绩` from sc a right join(select sid,avg(score) '平均成绩'from sc group by sid) b
on a.sid = b.SId
order by b.`平均成绩` desc ;

16.查询没学过"张三"老师讲授的任一门课程的学生姓名
#错误
select distinct a.sname from Student a ,Teacher b ,Course c ,sc d
where a.sid=d.sid
and d.cid = c.cid
and b.tid=c.tid
and b.Tname!='张三';
#正确
select distinct student.sname from Student where Student.sid not in
(select sc.sid from teacher,course ,sc
where sc.cid=Course.cid and
teacher.tid=course.tid  and
Teacher.tname='张三');

17.成绩不重复，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩
select a.* ,b.score from student a ,sc b ,teacher c,Course d
where a.sid=b.sid
and c.tid = d.tid
and d.cid = b.cid
and c.tname = '张三'
order by b.score desc
limit 1;

UPDATE sc SET score=90
where skey=17;
18.成绩有重复的情况下，查询选修「张三」老师所授课程的学生中，成绩最高的学生信息及其成绩

select a.* ,b.score from student a ,sc b ,teacher c,Course d
where a.sid=b.sid
and c.tid = d.tid
and d.cid = b.cid
and c.tname = '张三'
and b.score = (select max(b.score) from student a ,sc b ,teacher c,Course d
where a.sid=b.sid
and c.tid = d.tid
and d.cid = b.cid
and c.tname = '张三');

19.查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩
####自联结 inner join####

select any_value(a.cid), any_value(a.sid), any_value(a.score) from sc as a
inner join
sc as b
on a.sid = b.sid
and a.cid != b.cid
and a.score = b.score
GROUP BY a.cid, b.sid;

20.查询每门功成绩最好的前两名
#左连接 left join
select any_value(a.sid),any_value(a.cid),any_value(a.score) from sc a left join sc as b
on a.cid = b.cid
and a.score < b.score
group by a.cid,a.sid
having count(b.score)<2
order by a.cid;

21.查询每门课程被选修的学生数
SELECT b.CId,count(*) from SC b group by b.CId;

22.查询出只选修两门课程的学生学号和姓名
SELECT a.SId,a.Sname from Student a,SC b
where a.SId = b.SId
group by a.SId
having count(*)=2;

23.查询同名学生名单，并统计同名人数
select a.sname ,count(*) from Student a group by Sname
having count(*)>1;

24.查询 1990 年出生的学生名单
SELECT * from Student where year(Sage)=1990;

25.查询各学生的年龄
#函数 TIMESTAMPDIFF
select SId,Sname,TIMESTAMPDIFF(year,Sage,CURDATE()) from student;

26.查询本周过生日的学生
UPDATE Student SET Sage='1990-10-16'
where SId= '02';

SELECT * from Student where week(current_date)=week(Sage);

27.查询本月过生日的学生
SELECT * from Student where month(current_date) = month(Sage);

28.查询「李」姓老师的数量
SELECT count(*) from Teacher where Tname like '李%';

29.查有成绩的学生信息
SELECT * from Student where Student.SId in (SELECT sc.SId from SC);

30.查询所有同学的学生编号、学生姓名、选课总数、所有课程的成绩总和
#left join
SELECT a.SId,a.Sname,count(b.CId)`选课总数`,sum(b.score) from Student a left join SC b on a.SId = b.SId
group by a.SId;

31.查询在 SC 表存在成绩的学生信息
SELECT distinct a.* from Student a,SC b where a.SId = b.SId;

select * from student where sid in (select sc.sid from sc);

32.查询平均成绩大于等于 60 分的同学的学生编号和学生姓名和平均成绩
SELECT a.SId,a.Sname,avg(b.score)`平均成绩` from Student a,SC b
where a.SId = b.SId
group by b.SId
having avg(b.score)>60
order by avg(b.score) desc;

select a.sid,a.Sname,AVG(b.score) from Student a ,SC b
where a.SId = b.sid
GROUP BY a.sid
having AVG(b.score)>60;

33.查询不存在" 01 "课程但存在" 02 "课程的情况
SELECT * from sc where sc.CId = '02'
and sc.CId not in(SELECT cid from sc where cid = '01');

select * from sc where cid = '02' and sid not in (select  sid from sc  where cid='01');

34.查询存在" 01 "课程但可能不存在" 02 "课程的情况
SELECT * from sc where sc.CId = '01';

35.按各科成绩进行排序，并显示排名， Score 重复时保留名次空缺
select a.cid ,a.sid ,any_value(a.score),count(b.sid)+1 from sc a left join sc b
on a.cid=b.cid
and a.score<b.score
GROUP BY  a.cid,a.sid
order by a.cid ,count(b.sid)+1;

36.查询" 01 "课程比" 02 "课程成绩高的学生的信息及课程分数
#错误
SELECT a.*,b.score from Student a ,SC b where a.SId = b.SId
and b.SId in (SELECT b.SId from SC b inner join sc c on b.SId = c.SId and b.CId = '01'and c.CId = '02'and b.score>c.score);
#正确
select c.* ,a.score,b.score from student c ,
(select score,sid from sc where cid = '01')a,
(select score,sid from sc where cid = '02')b
where a.sid = b.sid
and c.sid=b.sid
and a.score >b.score;

37.查询学过「张三」老师授课的同学的信息
SELECT a.* from Student a,SC b,Teacher c,Course d
where a.SId = b.SId
and c.TId = d.TId
and d.CId = b.CId
and c.Tname = '张三';

38.查询没有学全所有课程的同学的信息
select a.* from Student a left join sc b
on a.sid = b.sid
group by a.SId
having count(b.cid)<(select count(cname) from Course);

39.查询至少有一门课与学号为" 01 "的同学所学相同的同学的信息
select a.* from student a ,sc b where a.sid = b.sid and b.cid in
(select cid from sc where sid ='01')
group by a.sid;

40.查询和" 01 "号的同学学习的课程完全相同的其他同学的信息
select * from Student where sid in
(select  sid from sc where sid not in
(select  sid from sc where  cid not in
(select cid from sc where sid ='01'))
GROUP BY sid
having count(*)=(select count(cid) from sc where sid ='01')and sid<>'01');

select  sid from sc where sid not in
(select  sid from sc where  cid not in
(select cid from sc where sid ='01')



































