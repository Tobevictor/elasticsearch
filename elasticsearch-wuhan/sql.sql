select time,latitude,longitude,depth,mag,ST_AsGeoJSON(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKB(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsText(geopoint) as point from earthquake;
insert into earthquake(time, latitude, longitude, depth, mag, geopoint) values ('1995',2,2,2,2,ST_GeomFromText('POINT(2 2)'));
select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake;
