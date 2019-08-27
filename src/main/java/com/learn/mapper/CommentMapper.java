package com.learn.mapper;

import com.learn.model.EarthquakeTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/14 17:51
 * @Created by dshuyou
 */
@Mapper
public interface CommentMapper {

	@Select("select * from comment")
	List<Map<String,Object>> getAll();

	/*@Select("select * from comment")
	List<Comment> getAll();*/

	@Select("select * from comment where ids >  (SELECT max(id) from commentassist)")
	List<Map<String,Object>> getAsc();

	@Select("select id,time,latitude,longitude,depth,mag from earthquake")
	List<Map<String,Object>> findAll();

	@Select("select id,time,latitude,longitude,depth,mag from earthquake where id = #{id}")
	EarthquakeTemplate findOne(int id);

	@Select("select id,time,latitude,longitude,depth,mag,ST_AsGeoJSON(geopoint) as geopoint from earthquake where time = 19950522")
	Map<String,Object>findbytime();
}
