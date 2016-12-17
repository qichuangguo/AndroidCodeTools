package com.itheima30.multithreaddownload_android.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.itheima30.multithreaddownload_android.MainActivity;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MultiThreadDownloadUtils {
	
	private int threadCount;		// 线程的总数
	private int runCompleteThreadCount; 	// 运行完成线程的计数器
	private LinearLayout llGroup;
	private Handler handler;

	/**
	 * 开始多线程下载
	 * @param url 下载连接
	 * @param threadCount 线程的数量
	 * @param directory 存储的路径
	 * @param handler 
	 */
	public void startMultiThreadDownload(String url, int threadCount, 
			String directory, LinearLayout llGroup, Handler handler) throws Exception {
		this.handler = handler;
		this.threadCount = threadCount; 
		this.llGroup = llGroup;
		runCompleteThreadCount = 0;
		
		//1. 得到服务器文件的长度.
		int remoteFileLength = getFileLengthFromNet(url);
		
		if(remoteFileLength == -1) {
			throw new IllegalArgumentException("获取远程文件的长度失败.");
		}
		
		System.out.println("远程文件的长度: " + remoteFileLength);
		
		//2. 根据文件的长度创建一个本地的文件.
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		File file = new File(directory, fileName);		// /mnt/sdcard/FeiQ.exe
		RandomAccessFile raf = new RandomAccessFile(file, "rwd");
		// 设置为远程服务器文件的成都
		raf.setLength(remoteFileLength);
		raf.close();
		
		//3. 计算出每一个线程需要下载的大小.
		int partSize = remoteFileLength / threadCount;		// 每一块的大小
		
		// 发送一个消息到主线程(MainActivity)中, 弹出开始下载的吐司
		handler.sendEmptyMessage(MainActivity.START_DONWLOAD);
		
		for (int i = 0; i < threadCount; i++) {
			// 开始的位置: 线程id * 块的大小;
			int start = i * partSize;
			// 结束的位置: ((线程id + 1) * 块的大小) -1;
			int end = ((i + 1) * partSize) -1;
			
			// 如果是最后一个线程, 需要下载到最后. 文件长度 -1;
			if(i == threadCount -1) {
				end = remoteFileLength -1;
			}

			//4. 开启线程下载.
			// 开启子线程时, 需要告诉子线程的数据是: 线程id, 开始的位置, 结束的位置, 下载的链接地址, 文件存储的路径(包含文件名).
			new DownloadThread(i, start, end, url, file.getPath()).start();
		}
	}

	/**
	 * 根据url连接获取将要下载文件的长度
	 * @param url
	 * @return -1 获取失败
	 * @throws Exception 
	 */
	private int getFileLengthFromNet(String url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		
		int responseCode = conn.getResponseCode();
		if(responseCode == 200) {
			// 获取文件的长度, 返回.
			int length = conn.getContentLength();// 获取远程文件的长度
			conn.disconnect();
			return length;
		}
		conn.disconnect();
		return -1;
	}
	
	/**
	 * @author andong
	 * 多线程下载中的子线程业务类
	 */
	class DownloadThread extends Thread {
		
		private int threadID;
		private int start;
		private int end;
		private String url;
		private String path;

		/**
		 * @param i 线程id
		 * @param start 开始的位置
		 * @param end 结束的位置
		 * @param url 下载链接
		 * @param path 文件的路径名(包含文件名)
		 */
		public DownloadThread(int i, int start, int end, String url, String path) {
			this.threadID = i;
			this.start = start;
			this.end = end;
			this.url = url;
			this.path = path;
		}

		@Override
		public void run() {
			// 设置当前进度最大值
			ProgressBar pb = (ProgressBar) llGroup.getChildAt(threadID);	// 获取当前线程对应的进度条
			pb.setMax(end - start);
			
			HttpURLConnection conn = null;
			RandomAccessFile cacheFileRAF;

			// 定义缓存的目录: /mnt/sdcard/FeiQ.exe		/mnt/sdcard/线程1.txt
			String cacheDirectory = path.substring(0, path.lastIndexOf("/"));
			try {
				File cacheFile = new File(cacheDirectory, "线程" + threadID + ".txt");
				if(cacheFile.exists()) {		// 当前文件存在, 说明上次是断点下载
					// 把本地文件的进度取出来
					cacheFileRAF = new RandomAccessFile(cacheFile, "rwd");
					// 上次下载的进度
					int previousProgress = Integer.valueOf(cacheFileRAF.readLine());
					cacheFileRAF.close();
					
					// 把当前的进度值, 设置为上一次下载的进度
					pb.setProgress(previousProgress);
					
					start += previousProgress;
				}

				// 4. 开始下载
				System.out.println("线程" + threadID + ", 开始下载: " + start + "-" + end);
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(10000);
				// 4.1. 在获取状态码之前, 设置请求头Range, 请求服务器中文件的部分内容.
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);	// 请求服务器中3-5位置的数据.
				
				// 4.2. 因为请求的是部分内容, 这时候服务器返回的状态码: 206
				int responseCode = conn.getResponseCode();
				if(responseCode == 206) {	// 当前服务器支持请求部分内容, 并且已经把部分内容返回过来了.
					// 4.3. 在写入本地文件时, 需要把本地文件开始写入的索引, 移动到当前线程开始的位置.
					RandomAccessFile raf = new RandomAccessFile(path, "rwd");
					raf.seek(start);		// 把当前文件的索引移动到开始的位置上
					
					// 4.4. 取出部分内容. 循环开始读取, 并写入到本地.
					InputStream is = conn.getInputStream(); // 部分内容
					byte[] buffer = new byte[102400];
					int len = -1;
					int downloadTotalLength = 0;		// 当前线程下载了总长度
					
					while((len = is.read(buffer)) != -1) {		// 开始下载, 正在下载中
						raf.write(buffer, 0, len);
						
						// 突然, 手机断电了.  把当前正在下载的进度, 存储到本地
						downloadTotalLength += len;
						cacheFileRAF = new RandomAccessFile(cacheFile, "rwd");
						cacheFileRAF.write(String.valueOf(downloadTotalLength).getBytes());
						cacheFileRAF.close();
						
						// 每次读取一段数据时, 更新一下进度值.
						pb.incrementProgressBy(len);
					}
					raf.close();
					is.close();
					
					System.out.println("线程" + threadID + ", 下载完成.");
					runCompleteThreadCount++;		// 每个线程完成一次, 计数器+1
				}
			} catch (Exception e) {
				handler.sendEmptyMessage(MainActivity.FAILED);
				e.printStackTrace();
			} finally {
				if(conn != null) {
					conn.disconnect();
				}
				
				if(runCompleteThreadCount == threadCount) {
					System.out.println("所有线程都下载完成.");
					handler.sendEmptyMessage(MainActivity.SUCCESS);
					// 删除所有的配置文件
					for (int i = 0; i < threadCount; i++) {
						File file = new File(cacheDirectory, "线程" + i + ".txt");
						if(file.exists()) {		// 如果存在删除文件.
							System.out.println(file.delete());
						}
					}
				}
			}
		}
	}
}
