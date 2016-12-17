package com.qfxu.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyView extends ImageView {

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private Paint paint = null;
	private TextPaint textPaint = null;

	private void init() {
		paint = new Paint();
		// 设置反锯齿
		paint.setAntiAlias(true);
		textPaint = new TextPaint();
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 开始补充绘制
		// 表示颜色
		paint.setColor(0xffff0000);
		canvas.drawCircle(getWidth() - textPaint.measureText("23") * 0.5f
				- getPaddingRight(), textPaint.getFontMetrics().bottom * 1.5f
				+ getPaddingTop(), textPaint.getTextSize(), paint);
		canvas.drawText("23", getWidth() - textPaint.measureText("23")
				- getPaddingRight(), textPaint.getFontMetrics().bottom * 3
				+ getPaddingTop(), textPaint);

		// textPaint.setTextSize(50);
		// ;
	}

}
