package com.qfxu.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

public class BitmapHelper {
	public static Bitmap createImageThumbnail(String filePath, int newHeight,
			int newWidth) {

		// bitmapfactory它在decode的时候会有一个options来控制采样的参数
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 仅仅采样边界
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int oldHeight = options.outHeight;
		int oldWidth = options.outWidth;
		// Log.i(TAG, "高度是：" + oldHeight + "，宽度是：" + oldWidth);
		int ratioHeight = oldHeight / newHeight;
		int ratioWidth = oldWidth / newWidth;

		options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth
				: ratioHeight;

		options.inPreferredConfig = Config.RGB_565;

		options.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		// Log.i(TAG, "高度是：" + options.outHeight + "，宽度是：" + options.outWidth);
		return bm;
	}

	public static Bitmap createImageThumbnail(int id, Resources resources,
			int newHeight, int newWidth) {

		// bitmapfactory它在decode的时候会有一个options来控制采样的参数
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 仅仅采样边界
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeResource(resources, id, options);
		int oldHeight = options.outHeight;
		int oldWidth = options.outWidth;
		// Log.i(TAG, "高度是：" + oldHeight + "，宽度是：" + oldWidth);
		int ratioHeight = oldHeight / newHeight;
		int ratioWidth = oldWidth / newWidth;

		options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth
				: ratioHeight;

		options.inPreferredConfig = Config.RGB_565;

		options.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeResource(resources, id, options);
		// Log.i(TAG, "高度是：" + options.outHeight + "，宽度是：" + options.outWidth);
		return bm;
	}
}
