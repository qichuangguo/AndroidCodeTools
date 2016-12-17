package com.itheima.mobilesafe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5����
	 * @param password ����
	 * @return ����
	 */
	public static String encoder(String password) {

		try {
			// md5���ܵ��㷨
			// ��ϢժҪ��
			MessageDigest digest = MessageDigest.getInstance("md5");
			// String password = "14e1b600b1fd579f47433b88e8d85291";
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			for (byte b : result) {
				// ÿһ��byte ��8��������λ�������� oxff
				int number = b & 0xff;// ����
				String strNumber = Integer.toHexString(number);
				// ��ȫ
				if (strNumber.length() == 1) {
					buffer.append("0");
				}
				buffer.append(strNumber);

			}

			// ��׼��md5���ܵĽ��
			// System.out.println(buffer.toString());
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * ��ȡ�ļ���md5ֵ
	 * @param path �ļ�������·��
	 * @return
	 */
	public static String getFileMd5(String path){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			//�����ļ���ÿ��byte
			while((len = fis.read(buffer))!=-1){
				digest.update(buffer, 0, len);
			}
			//�õ�����ǩ�����
			byte[] result = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				// ÿһ��byte ��8��������λ�������� oxff
				int number = b & 0xff;// ����
				String strNumber = Integer.toHexString(number);
				// ��ȫ
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
