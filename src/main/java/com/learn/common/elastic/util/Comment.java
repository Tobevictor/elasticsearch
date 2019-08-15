package com.learn.common.elastic.util;


import java.io.*;
import java.util.*;

/**
 * @Date 2019/8/1 16:13
 * @Created by dshuyou
 */
public class Comment {

	public static List<Map<String,Object>> toJson(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		List<Map<String,Object>> list = new ArrayList<>();
		int i = 0;
		while ((line=reader.readLine()) != null){
			try {
				String[] s = line.split(",");
				Map<String,Object> jsonObject = new HashMap<>();
				jsonObject.put("id",s[0]);
				jsonObject.put("username",s[1]);
				jsonObject.put("address",s[2]);
				jsonObject.put("data",s[3]);
				jsonObject.put("like",s[4]);
				jsonObject.put("content",s[5]);
				list.add(jsonObject);
			}catch (Exception e){
			}
		}

		return list;
	}

	public static void main(String[] args) throws IOException {
		String file = "D:\\test.csv";
		toJson(file);
	}
}
