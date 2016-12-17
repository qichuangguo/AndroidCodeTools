package com.qfxu.image;

import android.app.Application;

public class MyApplication extends Application {
	public static ImageCache imageCache = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		imageCache = ImageCache.getImageCache();
	}
}
