package com.qfxu.lineview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RectView extends View {

	// ����ͳ��ͼ������
	private List<Integer> list = null;

	public void setList(List<Integer> list) {
		this.list = list;
		invalidate();
	}

	public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public RectView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RectView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	private Paint paint = null;

	private TextPaint textPaint = null;

	private Random random = null;

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		textPaint = new TextPaint();
		textPaint.setAntiAlias(true);
		list = new ArrayList<Integer>();
		random = new Random();
		for (int i = 0; i < x_Num - 1; i++) {
			int n = random.nextInt(y_Max);
			list.add(n);
		}

	}

	// �����ͼ����
	private int left = 0;
	private int right = 0;
	private int top = 0;
	private int bottom = 0;

	// ������̶�Ԥ��λ��
	private int leftText = 100;
	private int bottomText = 0;
	// ��titleԤ��λ��
	private int titleText = 50;

	// ����ֽ��������

	// ����ʾ��X��ƽ�е����ж�����
	private int x_Num = 7;

	// ����ʾ��Y��ƽ�е����ж�����
	private int y_Num = 5;

	// Y�����ֵ
	private int y_Max = 100;

	// X�����ֵ
	private int x_Max = 100;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		left = getPaddingLeft();
		right = getWidth() - getPaddingRight();
		top = getPaddingTop();
		bottom = getHeight() - getPaddingBottom();
		Log.i("123456", left + ":" + right + ":" + top + ":" + bottom);

		for (int i = 0; i < y_Num + 1; i++) {
			// ����Y�������ƽ�е���
			canvas.drawLine(left + leftText + 1.0f * (right - left - leftText)
					/ y_Num * i, bottom - bottomText, left + leftText + 1.0f
					* (right - left - leftText) / y_Num * i, top + titleText,
					paint);
			if (i != 0) {
				// ����X��Ŀ̶�
				canvas.drawText(
						x_Max / y_Num * i + "",
						left + leftText + 1.0f * (right - left - leftText)
								/ y_Num * i
								- textPaint.measureText(x_Max / y_Num * i + "")
								* 0.5f,
						bottom - bottomText + textPaint.getFontMetrics().bottom
								* 4, textPaint);
			}
		}

		for (int i = 0; i < x_Num + 1; i++) {
			// ����X�������ƽ�е���
			canvas.drawLine(left + leftText, bottom
					- (bottom - top - titleText) / x_Num * i, right, bottom
					- (bottom - top - titleText) / x_Num * i, paint);
			if (i != 0) {
				// ����Y��Ŀ̶�
				canvas.drawText(y_Max / x_Num * i + "", left + leftText
						- textPaint.measureText(y_Max / x_Num * i + "0"),
						bottom - (bottom - top - titleText) / x_Num * i
								+ textPaint.getFontMetrics().bottom * 1.5f,
						textPaint);
			}
		}

		canvas.drawText("0", left + leftText - textPaint.measureText("00"),
				bottom - bottomText + textPaint.getFontMetrics().bottom * 4,
				textPaint);

		// ����
		for (int i = 1; i < list.size(); i++) {
			// ����Y�������ƽ�е���
			// canvas.drawLine(left + leftText + 1.0f * (right - left -
			// leftText)
			// / y_Num * i, bottom - bottomText, left + leftText + 1.0f
			// * (right - left - leftText) / y_Num * i, top + titleText,
			// paint);
			// 0~100
			// bottom-bottomText-top-titleText

			// canvas.drawCircle(left + leftText + 1.0f
			// * (right - left - leftText) / y_Num * i,
			// bottom - bottomText - 1.0f
			// * (bottom - bottomText - top - titleText) / 100
			// * list.get(i), 5, paint);

			paint.setStrokeWidth(10);
			canvas.drawLine(left + leftText + 1.0f * (right - left - leftText)
					/ y_Num * i, bottom - bottomText, left + leftText + 1.0f
					* (right - left - leftText) / y_Num * i,
					bottom - bottomText - 1.0f
							* (bottom - bottomText - top - titleText) / 100
							* list.get(i), paint);
			paint.setStrokeWidth(1);

		}
		// // ����
		// for (int i = 1; i < list.size() - 1; i++) {
		// canvas.drawLine(left + leftText + 1.0f * (right - left - leftText)
		// / y_Num * i,
		// bottom - bottomText - 1.0f
		// * (bottom - bottomText - top - titleText) / 100
		// * list.get(i), left + leftText + 1.0f
		// * (right - left - leftText) / y_Num * (i + 1),
		// bottom - bottomText - 1.0f
		// * (bottom - bottomText - top - titleText) / 100
		// * list.get(i + 1), paint);
		// }

	}
}
