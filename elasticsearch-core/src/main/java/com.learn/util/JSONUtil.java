package com.learn.util;

import java.io.*;

/**
 * @author dshuyou
 * @Date 2019/9/9 9:20
 */
public class JSONUtil {

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
			e.printStackTrace();
		}
		return sb.toString();
	}
}
