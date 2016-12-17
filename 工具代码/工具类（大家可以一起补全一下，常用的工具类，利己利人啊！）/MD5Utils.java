package com.itheima.mobilesafe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5加密
	 * @param password 明文
	 * @return 密文
	 */
	public static String encoder(String password) {

		try {
			// md5加密的算法
			// 信息摘要器
			MessageDigest digest = MessageDigest.getInstance("md5");
			// String password = "14e1b600b1fd579f47433b88e8d85291";
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			for (byte b : result) {
				// 每一个byte 和8个二进制位做与运算 oxff
				int number = b & 0xff;// 加盐
				String strNumber = Integer.toHexString(number);
				// 补全
				if (strNumber.length() == 1) {
					buffer.append("0");
				}
				buffer.append(strNumber);

			}

			// 标准的md5加密的结果
			// System.out.println(buffer.toString());
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 获取文件的md5值
	 * @param path 文件的完整路径
	 * @return
	 */
	public static String getFileMd5(String path){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			//遍历文件的每个byte
			while((len = fis.read(buffer))!=-1){
				digest.update(buffer, 0, len);
			}
			//得到数字签名结果
			byte[] result = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				// 每一个byte 和8个二进制位做与运算 oxff
				int number = b & 0xff;// 加盐
				String strNumber = Integer.toHexString(number);
				// 补全
				if (strNumber.length() == 1) {
					sb.append("0");
				}
				sb.append(strNumber);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();//can't reach
			return "";
		}
	}
}
