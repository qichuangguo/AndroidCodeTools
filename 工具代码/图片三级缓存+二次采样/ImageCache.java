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

	// ����������һ��map�����ϴ����������
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();
	private MyLruCache myLruCache = new MyLruCache((int) (Runtime.getRuntime()
			.maxMemory() / 8));

	public Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
		// ��ǿ������Ѱ��
		bitmap = myLruCache.get(url);
		if (bitmap != null) {
			Log.i("ǿ�����ҵ�", "1235");
			return bitmap;
		} else {
			// ����������Ѱ��
			
			SoftReference<Bitmap> softbitmap = map.get(url);
			if (softbitmap != null) {
				bitmap = softbitmap.get();
			}
			if (bitmap != null) {
				Log.i("�������ҵ�", "1235");
				return bitmap;
			} else {
				String filename = MD5Util.getMD5(url);

				// �ڴ�����Ѱ��(sd)
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
					Log.i("�����ҵ�", "1235");
					return bitmap;
				} else {
					// ȥ��������
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

		// ����������ж�����ȥʱ�ص�
		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			// TODO Auto-generated method stub
			super.entryRemoved(evicted, key, oldValue, newValue);
			// evictedΪtrueʱ��������
			if (evicted) {
				SoftReference<Bitmap> bitmap = new SoftReference<Bitmap>(
						oldValue);

				map.put(key, bitmap);
			}

		}

	}

}
