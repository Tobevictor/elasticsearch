package com.learn.mbg.mapper4;

import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author dshuyou
 * @date 2019/11/26 15:03
 */
public class CreateSql {

    public String selectWithParamSql(Map<String, Object> params) {
        String table = String.valueOf(params.get("table"));
        if(Pattern.matches(".*;.*--.*",table)){
            return null;
        }
        return new SQL() {
            {
                SELECT("*");
                FROM(String.valueOf(params.get("table")));
                if (params.get("pk")!=null) {
                    String pk = String.valueOf(params.get("pk"));
                    WHERE(pk.concat("= #{id}"));
                }
            }

        }.toString();
    }

    public static void main(String[] args){
        Map<String,Object> map = new HashMap<>();
        map.put("table","SA.VIEW_SEARCH_KC");
        map.put("pk","bjno");
        map.put("BJNO","123");

        CreateSql createSql = new CreateSql();
        String res = createSql.selectWithParamSql(map);
        System.out.println(res);
    }
}
