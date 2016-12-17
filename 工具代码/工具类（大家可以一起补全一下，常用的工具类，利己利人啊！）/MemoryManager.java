package com.ithm.lotteryhm30.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class MemoryManager {
	private static final String TAG = "MemoryManager";
	private static final int MAXMEMORY=16*1024*1024;//程序运行的最大内存 模拟器(0-16m)
	/**
	 * 判断系统是否在低内存下运行
	 * @param context
	 * @return
	 */
	public static boolean hasAcailMemory(Context context) {
		// 获取手机内部空间大小
		long memory = getAvailRAM(context);
		Log.i(TAG, memory+"");
		if (memory < MAXMEMORY) {
			//应用将处于低内存状态下运行
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取手机内部可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();// 获取 Android 数据目录
		StatFs stat = new StatFs(path.getPath());// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
		long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
		long availableBlocks = stat.getAvailableBlocks();// 返回 Int ，获取当前可用的存储空间
		return availableBlocks * blockSize;
	}

	/**
	 * 获取手机内部空间大小
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
		return totalBlocks * blockSize;
	}

	/**
	 * 获取手机外部可用空间大小
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 * 获取手机外部空间大小
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();// 获取外部存储目录即 SDCard
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			throw new RuntimeException("Don't have sdcard.");
		}
	}

	/**
	 * 外部存储是否可用
	 * 
	 * @return
	 */
	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**返回正在运行的进程数量
	 * @param context
	 * @return
	 */
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		return runningAppProcesses.size();
	}
	
	/**获得可用ram
	 * @param context
	 * @return
	 */
	public static long getAvailRAM(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new MemoryInfo();
		am.getMemoryInfo(outInfo);
		return outInfo.availMem;
	}
	/**
	 * 获取全部的手机内存
	 * @param context 上下文
	 * @return
	 */
	public static long getTotalRAM(Context context){
		try {
			File file = new File("/proc/meminfo");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			//MemTotal:         513000 kB  字符 串
			String line = br.readLine();
			char[] chars = line.toCharArray();
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<chars.length;i++){
				if(chars[i]>='0'&&chars[i]<='9'){
					sb.append(chars[i]);
				}
			}
			long memsize = Integer.parseInt(sb.toString());
			return memsize*1024;//byte 单位 
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
