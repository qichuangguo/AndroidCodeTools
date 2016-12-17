package com.qfxu.image;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imageView = null;

	public ImageAsyncTask(ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = params[0];
		Bitmap bitmap = MyApplication.imageCache.getBitmap(url);
		if (bitmap == null) {
			Log.i("网络上请求", "1235");
			byte[] data = HttpURLConnHelper.loadByteFromURL(url);
			//在sd卡上存一份
			SDCardHelper.saveFileToSDCardPublicDir(data, Environment.DIRECTORY_DOWNLOADS, MD5Util.getMD5(url));
			
			bitmap = BitmapHelper.createImageThumbnail(SDCardHelper.getSDCardPublicDir(Environment.DIRECTORY_DOWNLOADS)+File.separator+MD5Util.getMD5(url), 100, 100);
			
		}

		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		imageView.setImageBitmap(result);
	}

}
