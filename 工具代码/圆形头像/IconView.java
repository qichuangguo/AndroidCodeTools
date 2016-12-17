package com.qfxu.iconview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IconView extends View {
	// 申请一个自定义的点击监听成员
	private OnMyClickListener onMyClickListener = null;

	// 对点击监听成员增添set方法，方便外部实现
	public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
		this.onMyClickListener = onMyClickListener;
	}

	// 图像渲染器
	private BitmapShader bitmapShader = null;
	// 这个bitmap是给图像渲染器使用的
	private Bitmap bitmap = null;

	private Paint paint = null;

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		// 初始化bitmapshader
		bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);
		// 把图像渲染器交给画笔，从此以后，此画笔绘制的任何图形，都会被渲染
		paint.setShader(bitmapShader);

	}

	public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();

	}

	public IconView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public IconView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// canvas.drawRect(0, 0, getRight(), getBottom(), paint);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 10,
				paint);
	}

	private boolean isDown = false;

	// 它是触摸的方法，是onclick的底层方法
	// 返回值，如果为true就是消费事件，如果是false就是不消费
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		// //event.getAction()是获取具体的动作
		// event.getX()
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 勾股定理来判断是否命中区域
			if ((event.getX() - getWidth() / 2)
					* (event.getX() - getWidth() / 2)
					+ (event.getY() - getHeight() / 2)
					* (event.getY() - getHeight() / 2) <= (getWidth() / 2 - 10)
					* (getWidth() / 2 - 10)) {
				isDown = true;
			}

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
			// 勾股定理来判断是否命中区域
			if ((event.getX() - getWidth() / 2)
					* (event.getX() - getWidth() / 2)
					+ (event.getY() - getHeight() / 2)
					* (event.getY() - getHeight() / 2) <= (getWidth() / 2 - 10)
					* (getWidth() / 2 - 10)) {
				if (isDown) {
					if (onMyClickListener != null) {
						onMyClickListener.onClick(IconView.this);
					}
					isDown=false;
				}
			}
			break;

		default:
			break;
		}

		return true;
	}

}
