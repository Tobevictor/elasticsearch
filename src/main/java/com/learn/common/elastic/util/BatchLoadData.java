package com.learn.common.elastic.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * @author dshuyou
 * @create 2019/7/1
 * @description
 */
public class BatchLoadData {

	private static String URL = "jdbc:mysql://localhost:3306/dshuyou?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
	private static String USERNAME = "dshuyou";
	private static String PASSWORD = "123456";

	private static Connection connection = null;

	//连接数据库
	public static Connection getConnection(){

		try {
			connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
			System.out.println("成功连接mysql数据库"+connection);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Class not found " + e);
		}
		return connection;
	}

	// 关闭连接
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
				System.out.println("已经关闭连接！");
			} catch (SQLException e) {
				System.out.println("关闭连接失败！");
				e.printStackTrace();
			}
		}
	}

	public void load(String file) throws IOException, SQLException {
		long startTime = System.currentTimeMillis();
		String prefix = "INSERT INTO earthquake(time, latitude, longitude, depth, mag, geopoint) VALUES ";

		BufferedReader input = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		PreparedStatement pst = connection.prepareStatement(prefix);
		connection.setAutoCommit(false);
		StringBuilder suffix = new StringBuilder();
		while ((line = input.readLine())!=null ){
			String[] lines = line.split(",");
			String sql = null;
			try{
				suffix.append("('"+lines[0]+"',"+Double.parseDouble(lines[1])+","+
						Double.parseDouble(lines[2])+"," +Double.parseDouble(lines[3])+","+
						Double.parseDouble(lines[4])+",ST_GeomFromText('POINT("+Double.parseDouble(lines[2])
						+" "+Double.parseDouble(lines[1])+")')"+")");
				sql = prefix + suffix.toString().substring(0, suffix.length());
			}catch (Exception e){
				continue;
			}
			pst.addBatch(sql);
			suffix.setLength(0);
			count++;
			if(count%100000==0) {
				pst.executeBatch();
				connection.commit();
				pst.clearBatch();
				System.out.println("插入" + count + "条");
			}
		}
		pst.executeBatch();
		connection.commit();
		pst.clearBatch();
		System.out.println("共计插入" + count + "条");
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime+"ms");
	}

	public static void main(String[] args) throws IOException, SQLException {
		BatchLoadData batch = new BatchLoadData();
		getConnection();
		long start = System.currentTimeMillis();
		batch.load("D:\\download\\Earthquake.csv");

		long end = System.currentTimeMillis();
		System.out.println("导入共需："+(end-start)/1000+"s");
		close(connection);
	}
}
