package com.qfxu.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup{

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}
	public FlowLayout(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int lineNum=0;
		int lineWidth=0;
		
		
		for (int i = 0; i < getChildCount(); i++) {
			View view=getChildAt(i);
			view.measure(0, 0);
			if(lineWidth+view.getMeasuredWidth()<=getResources().getDisplayMetrics().widthPixels){
				
			}else{
				lineNum++;
				lineWidth=0;
			}
			
			view.layout(lineWidth, view.getMeasuredHeight()*lineNum, lineWidth+view.getMeasuredWidth(), view.getMeasuredHeight()*(lineNum+1));
			lineWidth+=view.getMeasuredWidth();
		
		
		}
	}

}
