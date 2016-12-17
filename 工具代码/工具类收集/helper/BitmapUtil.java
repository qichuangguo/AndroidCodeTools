package com.qfsheldon.oom;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

	/**
	 * 
	 */
	public static Bitmap createThumbnail(String filePath, int newWidth, int newHeight) {
		//options它是一个图片采样的参数
		BitmapFactory.Options options = new BitmapFactory.Options();
		//inJustDecodeBounds把这个属性设置成true，代表此次采样不分配内存
		options.inJustDecodeBounds = true;
		//进行第一次不分配内存的采样，此次采样结束之后，options中就有了这张图片的属性
		BitmapFactory.decodeFile(filePath, options);
		//取出长宽值
		int originalWidth = options.outWidth;
		int originalHeight = options.outHeight;
		//计算比例值
		int ratioWidth = originalWidth / newWidth;
		int ratioHeight = originalHeight / newHeight;

		//取一个合适的比例值
		options.inSampleSize = ratioHeight > ratioWidth ? ratioHeight
				: ratioWidth;
		//为第二次采样做准备，设置inJustDecodeBounds为false，代表此次采样分配内存
		options.inJustDecodeBounds = false;
		//把第二次采样的结果返回
		return BitmapFactory.decodeFile(filePath, options);
	}
	public static Bitmap createThumbnail(Resources res,int id, int newWidth, int newHeight) {
		//options它是一个图片采样的参数
		BitmapFactory.Options options = new BitmapFactory.Options();
		//inJustDecodeBounds把这个属性设置成true，代表此次采样不分配内存
		options.inJustDecodeBounds = true;
		//进行第一次不分配内存的采样，此次采样结束之后，options中就有了这张图片的属性
		BitmapFactory.decodeResource(res, id, options);
		//取出长宽值
		int originalWidth = options.outWidth;
		int originalHeight = options.outHeight;
		//计算比例值
		int ratioWidth = originalWidth / newWidth;
		int ratioHeight = originalHeight / newHeight;

		//取一个合适的比例值
		options.inSampleSize = ratioHeight > ratioWidth ? ratioHeight
				: ratioWidth;
		//为第二次采样做准备，设置inJustDecodeBounds为false，代表此次采样分配内存
		options.inJustDecodeBounds = false;
		//把第二次采样的结果返回
		return BitmapFactory.decodeResource(res, id, options);
	}
}
