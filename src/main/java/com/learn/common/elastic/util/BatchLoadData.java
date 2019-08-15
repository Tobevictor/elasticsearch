package com.learn.common.elastic.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		}return connection;
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
		String prefix = "INSERT INTO comment(id, address, content, username, like, date) VALUES ";

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
				suffix.append("("+Integer.parseInt(lines[0])+",'"+lines[2]+"','"+lines[5]+"','"
						+lines[1]+"',"+Integer.parseInt(lines[4])+",'"+lines[3]+"')");
				sql = prefix + suffix.toString().substring(0, suffix.length());
			}catch (Exception e){
				System.out.println("第"+count+"条数据错误");
			}
			pst.addBatch(sql);
			suffix.setLength(0);
			count++;
			if(count%10000==0) {
				pst.executeBatch();
				connection.commit();
				pst.clearBatch();
				System.out.println("插入" + count + "条");
			}
		}
		pst.executeBatch();
		connection.commit();
		pst.clearBatch();
		close(connection);
		System.out.println("共计插入" + count + "条");
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime+"ms");
	}

	public static void main(String[] args) throws IOException, SQLException {
		BatchLoadData batch = new BatchLoadData();
		getConnection();
		batch.load("D:\\test.csv");
	}
}
