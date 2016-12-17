package com.qfxu.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NumView extends ImageView {

	private int num = 0;
	private int showNumMode = 2;

	public void setNum(int num) {
		this.num = num;
		invalidate();
	}

	public int getNum() {
		return num;
	}

	public void setShowNumMode(int showNumMode) {
		this.showNumMode = showNumMode;
		// �˷��������̵߳��ã�ʹ��ondrawִ��
		invalidate();
		// postInvalidate()
	}

	public int getShowNumMode() {
		return showNumMode;
	}

	// SHOW_NUM_MODE_1��ʾ����һ����ͨ��imageview
	// SHOW_NUM_MODE_2��ʾ����ʾ����
	// SHOW_NUM_MODE_3��ʾ��ֻ��ʾһ��С��㣬����ʾ����
	public static final int SHOW_NUM_MODE_1 = 0;
	public static final int SHOW_NUM_MODE_2 = 1;
	public static final int SHOW_NUM_MODE_3 = 2;

	public NumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public NumView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public NumView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private Paint paint = null;
	private TextPaint textPaint = null;

	private void init() {
		paint = new Paint();
		// ���÷����
		paint.setAntiAlias(true);
		textPaint = new TextPaint();
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		switch (showNumMode) {
		case 0:

			break;
		case 1:
			if (num > 0) {
				if (num <= 99) {
					// ��ʼ�������
					// ��ʾ��ɫ
					paint.setColor(0xffff0000);
					canvas.drawCircle(
							getWidth() - textPaint.measureText(num + "") * 0.5f
									- getPaddingRight(),
							textPaint.getFontMetrics().bottom * 1.5f
									+ getPaddingTop(), textPaint.getTextSize(),
							paint);
					canvas.drawText(num + "",
							getWidth() - textPaint.measureText(num + "")
									- getPaddingRight(),
							textPaint.getFontMetrics().bottom * 3
									+ getPaddingTop(), textPaint);

					// textPaint.setTextSize(50);
					// ;
				} else {
					// ��ʼ�������
					// ��ʾ��ɫ
					paint.setColor(0xffff0000);
					canvas.drawCircle(
							getWidth() - textPaint.measureText(99 + "") * 0.5f
									- getPaddingRight(),
							textPaint.getFontMetrics().bottom * 1.5f
									+ getPaddingTop(), textPaint.getTextSize(),
							paint);
					canvas.drawText(99 + "",
							getWidth() - textPaint.measureText(99 + "")
									- getPaddingRight(),
							textPaint.getFontMetrics().bottom * 3
									+ getPaddingTop(), textPaint);

					// textPaint.setTextSize(50);
					// ;
				}
			}
			break;
		case 2:
			if (num > 0) {
				paint.setColor(0xffff0000);
				canvas.drawCircle(
						getWidth() - getPaddingRight()
								- textPaint.getTextSize() / 4, getPaddingTop()
								+ textPaint.getTextSize() / 4,
						textPaint.getTextSize() / 2, paint);
			}
			break;
		default:
			break;
		}

	}

}
