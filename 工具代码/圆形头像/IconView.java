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
	// ����һ���Զ���ĵ��������Ա
	private OnMyClickListener onMyClickListener = null;

	// �Ե��������Ա����set�����������ⲿʵ��
	public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
		this.onMyClickListener = onMyClickListener;
	}

	// ͼ����Ⱦ��
	private BitmapShader bitmapShader = null;
	// ���bitmap�Ǹ�ͼ����Ⱦ��ʹ�õ�
	private Bitmap bitmap = null;

	private Paint paint = null;

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		// ��ʼ��bitmapshader
		bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR,
				Shader.TileMode.MIRROR);
		// ��ͼ����Ⱦ���������ʣ��Ӵ��Ժ󣬴˻��ʻ��Ƶ��κ�ͼ�Σ����ᱻ��Ⱦ
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

	// ���Ǵ����ķ�������onclick�ĵײ㷽��
	// ����ֵ�����Ϊtrue���������¼��������false���ǲ�����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		// //event.getAction()�ǻ�ȡ����Ķ���
		// event.getX()
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ���ɶ������ж��Ƿ���������
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
			// ���ɶ������ж��Ƿ���������
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
