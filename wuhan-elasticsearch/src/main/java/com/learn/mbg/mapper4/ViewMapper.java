package com.learn.mbg.mapper4;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/20 13:15
 */
@Mapper
public interface ViewMapper {
    //select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE ,'业务编号：'||caseno||',发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||',立搞人：'||ligaoman as content from sa.view_fwxx;
    @Select("select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE as title ,'业务编号：'||caseno||',发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||',立搞人：'||ligaoman as content from ${table}")
    List<Map<String,Object>> findAll(@Param("table") String table);

    @Select("select 'GW' type, caseno as  id ,'420000' qy, fwsj as time ,TITLE as title ,'业务编号：'||caseno||',发文名称：'||title||',发文时间：'||fwsj ||',立搞单位：'|| ligaounit ||',立搞人：'||ligaoman as content from ${table} where time > #{updatetime}")
    List<Map<String,Object>> find(@Param("table") String table, @Param("updatetime") String updatetime);
}
