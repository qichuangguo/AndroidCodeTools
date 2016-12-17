package com.qfxu.image;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

public class ImageCache {

	private static ImageCache imageCache = null;

	private ImageCache() {

	}

	public static ImageCache getImageCache() {
		if (imageCache == null) {
			imageCache = new ImageCache();
		}

		return imageCache;
	}

	// 它是我们用一个map来集合处理的软引用
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();
	private MyLruCache myLruCache = new MyLruCache((int) (Runtime.getRuntime()
			.maxMemory() / 8));

	public Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
		// 在强引用中寻找
		bitmap = myLruCache.get(url);
		if (bitmap != null) {
			Log.i("强引用找到", "1235");
			return bitmap;
		} else {
			// 在软引用中寻找
			
			SoftReference<Bitmap> softbitmap = map.get(url);
			if (softbitmap != null) {
				bitmap = softbitmap.get();
			}
			if (bitmap != null) {
				Log.i("软引用找到", "1235");
				return bitmap;
			} else {
				String filename = MD5Util.getMD5(url);

				// 在磁盘中寻找(sd)
				String path = SDCardHelper
						.getSDCardPublicDir(Environment.DIRECTORY_DOWNLOADS);
				File file = new File(path + File.separator + filename);
				if (file.exists()) {
//					byte[] data = SDCardHelper.loadFileFromSDCard(file
//							.getAbsolutePath());
//					bitmap = BitmapFactory
//							.decodeByteArray(data, 0, data.length);
					bitmap = BitmapHelper.createImageThumbnail(SDCardHelper.getSDCardPublicDir(Environment.DIRECTORY_DOWNLOADS)+File.separator+MD5Util.getMD5(url), 100, 100);
					myLruCache.put(url, bitmap);
					Log.i("磁盘找到", "1235");
					return bitmap;
				} else {
					// 去网络上找
					return null;
				}

			}

		}

	}

	class MyLruCache extends LruCache<String, Bitmap> {

		public MyLruCache(int maxSize) {
			super(maxSize);

			// TODO Auto-generated constructor stub
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			// TODO Auto-generated method stub
			return value.getByteCount();
		}

		// 这个方法在有东西出去时回调
		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			// TODO Auto-generated method stub
			super.entryRemoved(evicted, key, oldValue, newValue);
			// evicted为true时就是满了
			if (evicted) {
				SoftReference<Bitmap> bitmap = new SoftReference<Bitmap>(
						oldValue);

				map.put(key, bitmap);
			}

		}

	}

}
