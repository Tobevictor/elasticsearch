package com.learn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dshuyou
 * @date 2019/11/29 15:00
 */
public class DateUtil {

    ////15-7月 -04 转 yyyy-MM-dd
    public static String convertFormat(String time){
        String[] times = time.split("月");
        String year = "20".concat(times[1].split("-")[1]);
        String[] md = times[0].split("-");
        String month = md[1].length() == 1 ? "0".concat(md[1]) : md[1];
        String day = md[0];
        return year.concat("-").concat(month).concat("-").concat(day);
    }

    public static String convert(String time) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1;
        if(time.matches(".*月.*")){
            return convertFormat(time);
        }else if(time.matches(".*-.*-.*")){
            Date date = dateFormat.parse(time);
            String s = dateFormat.format(date);
            return String.valueOf(s);
        }else if(time.matches(".*/.*/.*")){
            dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
            Date date = dateFormat1.parse(time);
            String s = dateFormat.format(date);
            return String.valueOf(s);
        }else if(time.matches(".*/.*/.*:.*:.*")){
            dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = dateFormat1.parse(time);
            String s = dateFormat.format(date);
            return String.valueOf(s);
        }else {
            return null;
        }
    }
}
