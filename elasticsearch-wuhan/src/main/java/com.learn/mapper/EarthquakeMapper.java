package com.learn.mapper;

import com.learn.model.Earthquake;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/29 10:45
 * @Created by dshuyou
 */
@Mapper
public interface EarthquakeMapper {

	@Select("select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake")
	List<Map<String,Object>> findAll();

	@Select("select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake")
	List<Earthquake> findAll1();

	@Select("select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake where id=2")
	Map<String,Object> findOne();

	@Select("select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake where id=2")
	Earthquake findOne1();

}
