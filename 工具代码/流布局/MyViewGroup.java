package com.qfxu.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

//�Զ���viewgroup
public class MyViewGroup extends ViewGroup {

	public MyViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyViewGroup(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	// �����е���view��������
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int h = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			// layout�Ǿ����ÿһ����View�������еķ���
			// ����view�Ĳ���
			view.measure(0, 0);

			view.layout(0, h, view.getMeasuredWidth(),
					h + view.getMeasuredHeight());
			// view.layout(0, 0, 100, 20);
			h += view.getHeight();
		}
	}

	// �����Լ����
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int h = 0;
		int w = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			// layout�Ǿ����ÿһ����View�������еķ���
			// ����view�Ĳ���
			view.measure(0, 0);

			// view.layout(0, 0, 100, 20);
			h += view.getMeasuredHeight();
			if (w < view.getMeasuredWidth()) {
				w = view.getMeasuredWidth();
			}
		}
		setMeasuredDimension(w, h);

	}
}
