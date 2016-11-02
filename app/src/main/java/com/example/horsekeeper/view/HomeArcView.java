package com.example.horsekeeper.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 主界面的扇形(自定义View)
 * 
 * @author lenovo
 * 
 */

public class HomeArcView extends View {
	/**
	 * 画笔
	 */
	Paint mPaint;
	/**
	 * 扇形内切矩形
	 */
	RectF r;
	/**
	 * 扇形偏移角度
	 * 
	 * @param context
	 */
	float sweepAngle = 0;
	/**
	 * 是否在运行中(默认值false)
	 * 
	 * @param context
	 */
	boolean isRuning;
	/**
	 * 返回态
	 */
	public static final int RETURN_STATE = 201;
	/**
	 * 前进态
	 * 
	 * @param context
	 */
	public static final int GO_STATE = 202;

	/**
	 * 当前状态(默认为后退态)
	 * 
	 * @param context
	 */
	int state = RETURN_STATE;
	/**
	 * 每次的偏移速度(进度条速度)
	 */
	float speed = 8;
	/**
	 * TextView
	 * 
	 * @param context
	 */
	TextView mTvShowText;

	/**
	 * 提供在代码中使用
	 * 
	 * @param context
	 */
	public HomeArcView(Context context) {
		super(context);
		init();
	}

	/**
	 * 布局自动调用 不包含style
	 * 
	 * @param context
	 * @param attrs
	 */
	public HomeArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 布局自动调用 包含style
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public HomeArcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * view中的方法 拿到布局中的宽高 需要转化
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 拿到布局中的宽高 需要转化
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 需要转化 转化成像素值
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		// 设置内切矩形
		r = new RectF(0, 0, width, height);
	}

	/**
	 * 初始化Paint画笔
	 */
	public void init() {
		mPaint = new Paint();

		// 颜色
		// mPaint.setColor(getResources().getColor(R.color.lll));
		// mPaint.setColor(Color.BLACK);
		mPaint.setColor(0xffff8c00);

		// 画笔大小
		mPaint.setStrokeWidth(5);

		// 抗锯齿
		mPaint.setAntiAlias(true);

		// new Thread(this).start();
	}

	/**
	 * 设置角度没有动画
	 */
	// public void setAngle(float angle) {
	// sweepAngle = angle;
	// postInvalidate();
	// }

	/**
	 * 设置角度带动画
	 */
	public void setAngleWithAnim(final float angle) {
		// 判断 如果在运行中，没有反应
		if (isRuning) {
			return;
		}

		isRuning = true;

		// 回退
		final Timer time = new Timer();
		/*
		 * 1时间任务对象 2延迟多久执行 3执行之后每过多久执行一次run 永不停止 除非手动停止
		 * 
		 * 在Android中 子线程不能刷新UI UI线程就是主线程
		 * 
		 * task--这是被调度的任务。 delay--这是以毫秒为单位的延迟之前的任务就是执行。
		 * period--这是在连续执行任务之间的毫秒的时间。
		 */

		/**
		 * time schedule方法
		 */
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				switch (state) {
				case RETURN_STATE:
					// 减少
					sweepAngle -= speed;
					// 改TextView
					if (sweepAngle <= 0) {
						sweepAngle = 0;
						state = GO_STATE;
					}
					// 重绘
					postInvalidate();
					break;
				case GO_STATE:// 前进态
					sweepAngle += speed;

					// 改TextView 不能在子线程中修改UI
					if (sweepAngle >= angle) {
						sweepAngle = angle;

						// 手动停止time？
						time.cancel();

						isRuning = false;
						state = RETURN_STATE;
					}
					// 重绘
					postInvalidate();
				}
			}
		}, 50, 40);
		// 前进

	}

	/*
	 * public void setAngleWithAnim(final float angle, TextView TvShowText) { //
	 * 判断 如果在运行中，没有反应 mTvShowText = TvShowText; if (isRuning) { return; }
	 * isRuning = true; // 回退 final Timer time = new Timer();
	 * 
	 * 1时间任务对象 2延迟多久执行 3执行之后每过多久执行一次run 永不停止 除非手动停止
	 * 
	 * 在Android中 子线程不能刷新UI UI线程就是主线程
	 * 
	 * time.schedule(new TimerTask() {
	 * 
	 * @Override public void run() { switch (state) { case RETURN_STATE: // 减少
	 * sweepAngle -= speed; // 改TextView
	 * 
	 * if (sweepAngle <= 0) { sweepAngle = 0; state = GO_STATE; }
	 * postInvalidate(); // 重绘 break; case GO_STATE:// 前进态 sweepAngle += speed;
	 * // 改TextView
	 * 
	 * if (sweepAngle >= angle) { sweepAngle = angle; time.cancel(); isRuning =
	 * false; state = RETURN_STATE; } postInvalidate(); } }
	 * 
	 * }, 50, 40); // 前进
	 * 
	 * }
	 */

	/**
	 * 提供一个Canvas画布(主线程)自动调用
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制扇形
		canvas.drawArc(r, -90, sweepAngle, true, mPaint);

		mOnScorceChangeListner.onChange(sweepAngle);

		// int score1 = (int) (sweepAngle / 360 * 100);
		// if (mTvShowText != null) {
		// mTvShowText.setText("" + score1);
		// }
	}

	/*
	 * @Override public void run() { for (int i = 0; i < 100; i++) { try {
	 * sweepAngle += 3; Thread.sleep(100); // 邮差 postInvalidate(); } catch
	 * (InterruptedException e) { e.printStackTrace(); } } }
	 */

	// 回掉
	OnScorceChangeListner mOnScorceChangeListner;

	/**
	 * 给外部用接口(回掉)
	 * 
	 * @param mOnScorceChangeListner
	 */
	public void setOnScorceChangeListner(
			OnScorceChangeListner mOnScorceChangeListner) {
		this.mOnScorceChangeListner = mOnScorceChangeListner;
	}

	/**
	 * 监听进度条改变(接口)
	 * 
	 * @author lenovo
	 * 
	 */
	public interface OnScorceChangeListner {
		void onChange(float offset);
	}
}
