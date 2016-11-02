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
 * 软件管理的饼图
 * 
 * @author lenovo
 * 
 */
public class PieCustumView extends View {
	/**
	 * 定义画笔
	 */
	Paint mPaint;
	/**
	 * 内切矩形
	 */
	RectF mRectF;
	/**
	 * 橙色
	 */
	int o = 0xffff8c00;
	/**
	 * 蓝色
	 */
	int b = 0xff000099;

	/**
	 * 绿色
	 */
	int g = 0xff00aa00;

	/**
	 * 角度和速度
	 */
	float mBlueAngle;
	float mGreenAngle;
	int speed = 4;

	/**
	 * 三个重载的构造方法
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
	 * 设置角度
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
	 * 内切矩形
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		mRectF = new RectF(0, 0, width, height);
	}

	/**
	 * 画笔
	 */
	public void init() {
		mPaint = new Paint();
		// 抗锯齿
		mPaint.setAntiAlias(true);
	}

	/**
	 * 画布
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
