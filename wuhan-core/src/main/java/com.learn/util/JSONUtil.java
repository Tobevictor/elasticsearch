package com.learn.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author dshuyou
 * @date 2019/9/9 9:20
 */
public class JSONUtil {
	private static Logger logger = Logger.getLogger(JSONUtil.class);
	public static String readLocalTextFile(String path) {
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		}  catch (IOException e) {
			logger.error("readLocalTextFile error");
			return null;
		}
		return sb.toString();
	}
}
