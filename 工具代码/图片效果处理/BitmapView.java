package com.qfxu.bitmapview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BitmapView extends View {

	public BitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public BitmapView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public BitmapView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private Bitmap bitmap_old = null;
	private Bitmap bitmap_new = null;
	private Paint paint = null;

	private int[] pixels;

	private int[] pixels_new;

	private void init() {
		paint = new Paint();
		bitmap_old = BitmapFactory.decodeResource(getResources(),
				R.drawable.zuozhu);
		bitmap_new = Bitmap.createBitmap(bitmap_old.getWidth(),
				bitmap_old.getHeight(), Config.ARGB_8888);
		pixels = new int[bitmap_old.getWidth() * bitmap_old.getHeight()];
		pixels_new = new int[bitmap_old.getWidth() * bitmap_old.getHeight()];
		// 255-x
		bitmap_old.getPixels(pixels, 0, bitmap_old.getWidth(), 0, 0,
				bitmap_old.getWidth(), bitmap_old.getHeight());
		for (int i = 0; i < pixels.length - 1; i++) {
			int a = Color.alpha(pixels[i]);
			int r = Color.red(pixels[i]);
			int g = Color.green(pixels[i]);
			int b = Color.blue(pixels[i]);

			r = r - Color.red(pixels[i + 1]) + 127;
			g = g - Color.green(pixels[i + 1]) + 127;
			b = b - Color.blue(pixels[i + 1]) + 127;

			// r = 255 - r;
			// g = 255 - g;
			// b = 255 - b;
			pixels_new[i] = Color.argb(a, r, g, b);
		}
		bitmap_new.setPixels(pixels_new, 0, bitmap_old.getWidth(), 0, 0,
				bitmap_old.getWidth(), bitmap_old.getHeight());

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		canvas.drawBitmap(bitmap_new, 0, 0, paint);
	}

}
