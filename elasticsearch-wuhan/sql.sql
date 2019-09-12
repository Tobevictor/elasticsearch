select time,latitude,longitude,depth,mag,ST_AsGeoJSON(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKB(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsText(geopoint) as point from earthquake;
insert into earthquake(time, latitude, longitude, depth, mag, geopoint) values ('1995',2,2,2,2,ST_GeomFromText('POINT(2 2)'));
select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake;


show index from earthquake;

explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where depth = 5.2;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake use index(earthquake_id_uindex) where depth = 5.2;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake ignore index (earthquake_id_uindex) where id = 1000;

explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where id = 1000;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where mag = 2.1 limit 3;

select * from comment where ids = 1000;
explain select * from comment where id < 100000000 limit 30;

show profiles ;
show variables;
show variables like '%profiling%';
set profiling=1;

select * from comment where ids = 1000;
show profiles ;
select * from comment where id > 10000000 && id < 100000000 order by id desc ;
show profiles ;





