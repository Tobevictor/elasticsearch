package com.learn.common.elastic.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dshuyou
 * @create 2019/6/13
 *
 */
public class jsonUtils {

	private long id;
	private double lat;
	private double lon;
	private String time;

	public jsonUtils(long id, double lat, double lon, String time) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	@Override
	public String toString() {
		return "jsonUtils{" +
				"id=" + id +
				", lat=" + lat +
				", lon=" + lon +
				", time='" + time + '\'' +
				'}';
	}

	public static List<String> getFile(String dir){
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(dir);
		File[] tempList = file.listFiles();

		for (File aTempList : Objects.requireNonNull(tempList)) {
			if (aTempList.isFile()) {
				files.add(aTempList.toString());
			}
			if (aTempList.isDirectory()) {
				getFile(String.valueOf(aTempList));
			}
		}
		return files;
	}

	public static void mergeFile(String dir,String output) throws IOException {
		BufferedWriter bw=new BufferedWriter(new FileWriter(output));
		File file = new File(dir);
		File[] list = file.listFiles();
		int fileCount = 0;
		int folderConut= 0;
		for(File f : Objects.requireNonNull(list)) {
			if(f.isFile()) {
				fileCount++;
				System.out.println("fileCount: "+fileCount+",file:"+f);
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				while((line=br.readLine())!=null) {
					bw.write(line+"\r\n");
				}
				br.close();
			}else {
				folderConut++;
			}
		}
		bw.newLine();
		bw.close();
		System.out.println("输入目录下文件个数为"+fileCount);
		System.out.println("输入目录下文件夹个数为"+folderConut);
	}

	public static JSONObject csvToJson(String input,String output) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter bw=new BufferedWriter(new FileWriter(output));
		String line = null;
		JSONObject featureCollection = null;
		while ((line=reader.readLine()) != null){
			try {
				String[] s = line.split("\t");
				jsonUtils json = new jsonUtils(Long.parseLong(s[0]),Double.parseDouble(s[53]),Double.parseDouble(s[54]),s[56]);
				featureCollection = new JSONObject();
				JSONArray featureList = new JSONArray();
				featureList.add(json.toGEOJSON());
				JSONArray featureArray = new JSONArray();
				JSONObject object = new JSONObject();
				object.put("Actor1Name",s[6]);
				object.put("Actor1Geo_Fullname",s[36]);
				featureArray.add(object);
				featureCollection.put("attribute",featureArray);
				featureCollection.put("date",s[56]);
				featureCollection.put("features",featureList);
				featureCollection.put("type", "featureCollection");
				bw.write(featureCollection.toJSONString()+"\r\n");
			}catch (Exception ignored){

			}
		}return featureCollection;
	}


	public static int addIndex(String input,String output) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter bw=new BufferedWriter(new FileWriter(output));
		String line = null;

		String index = "{ \"index\" : { \"_index\" : \"feature\", \"_type\" : \"type1\", \"_id\" : \"1\" } }";
		bw.write(index+"\r\n");
		int count = 2;
		while ((line=reader.readLine()) != null){
			bw.write(line+"\r\n");
			bw.write("{ \"index\" : { \"_index\" : \"feature\", \"_type\" : \"type1\", \"_id\" :" +count+ "} }"+"\r\n");
			count++;
		}
		bw.write("\r\n");
		bw.close();
		System.out.println(count);
		return count;
	}

	public static int addIndex1(String input,String output,int count) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter bw=new BufferedWriter(new FileWriter(output));
		String line = null;

		String index = "{ \"index\" : { \"_index\" : \"feature\", \"_type\" : \"type1\", \"_id\" : "+count+" } }";
		count++;
		bw.write(index+"\r\n");
		while ((line=reader.readLine()) != null){
			bw.write(line+"\r\n");
			bw.write("{ \"index\" : { \"_index\" : \"feature\", \"_type\" : \"type1\", \"_id\" :" +count+ "} }"+"\r\n");
			count++;
		}
		bw.write("\r\n\r\n");
		System.out.println(count);
		return count;
	}

	private JSONObject toGEOJSON(){


		JSONObject point = new JSONObject();
		try{
			point.put("lat",String.valueOf(lat));
			point.put("lng",String.valueOf(lon));

		}catch (JSONException e) {
			e.printStackTrace();
		}
		return point;
	}

	public static void main(String[] args) throws IOException {
	/*	List<String> list = getFile("F:\\download\\download");
		int count = 0;
		int tmp1 = 0;
		for (int i = 0;i<list.size();i++) {
			String path1 = "F:\\Gdelt data\\201812\\201812"+count+".export.json";
			String path2 = "F:\\Gdelt data\\201812\\201812"+count+".index.json";
			csvToJson(list.get(i),path1);
			int tmp = addIndex1(path1,path2,tmp1);
			count++;
			tmp1 = tmp;
		}*/

		mergeFile("F:\\Gdelt data\\201812","F:\\Gdelt data\\index.json");
	}
}
