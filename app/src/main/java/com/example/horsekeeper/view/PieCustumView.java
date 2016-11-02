package com.example.horsekeeper.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * �������ı�ͼ
 * 
 * @author lenovo
 * 
 */
public class PieCustumView extends View {
	/**
	 * ���廭��
	 */
	Paint mPaint;
	/**
	 * ���о���
	 */
	RectF mRectF;
	/**
	 * ��ɫ
	 */
	int o = 0xffff8c00;
	/**
	 * ��ɫ
	 */
	int b = 0xff000099;

	/**
	 * ��ɫ
	 */
	int g = 0xff00aa00;

	/**
	 * �ǶȺ��ٶ�
	 */
	float mBlueAngle;
	float mGreenAngle;
	int speed = 4;

	/**
	 * �������صĹ��췽��
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PieCustumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PieCustumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PieCustumView(Context context) {
		super(context);
		init();
	}

	/**
	 * ���ýǶ�
	 * 
	 * @param blueflot
	 */
	public void setAngle(float blueflot) {
		final float blueAngle = blueflot * 360;
		final float greenAngle = 360 - blueflot * 360;

		final Timer time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				mBlueAngle += speed;
				if (mBlueAngle >= blueAngle) {
					mBlueAngle = blueAngle;
				}
				mGreenAngle += speed;
				if (mGreenAngle >= greenAngle) {
					mGreenAngle = greenAngle;
				}
				if (mBlueAngle + mGreenAngle == 360) {
					time.cancel();
				}
				postInvalidate();
			}
		}, 20, 20);
	}

	/**
	 * ���о���
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		mRectF = new RectF(0, 0, width, height);
	}

	/**
	 * ����
	 */
	public void init() {
		mPaint = new Paint();
		// �����
		mPaint.setAntiAlias(true);
	}

	/**
	 * ����
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPaint.setColor(o);
		canvas.drawArc(mRectF, -90, 360, true, mPaint);

		mPaint.setColor(b);
		canvas.drawArc(mRectF, -90, mBlueAngle, true, mPaint);

		mPaint.setColor(g);
		canvas.drawArc(mRectF, mBlueAngle - 90, mGreenAngle, true, mPaint);
	}
}
