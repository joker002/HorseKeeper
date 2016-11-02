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
 * �����������(�Զ���View)
 * 
 * @author lenovo
 * 
 */

public class HomeArcView extends View {
	/**
	 * ����
	 */
	Paint mPaint;
	/**
	 * �������о���
	 */
	RectF r;
	/**
	 * ����ƫ�ƽǶ�
	 * 
	 * @param context
	 */
	float sweepAngle = 0;
	/**
	 * �Ƿ���������(Ĭ��ֵfalse)
	 * 
	 * @param context
	 */
	boolean isRuning;
	/**
	 * ����̬
	 */
	public static final int RETURN_STATE = 201;
	/**
	 * ǰ��̬
	 * 
	 * @param context
	 */
	public static final int GO_STATE = 202;

	/**
	 * ��ǰ״̬(Ĭ��Ϊ����̬)
	 * 
	 * @param context
	 */
	int state = RETURN_STATE;
	/**
	 * ÿ�ε�ƫ���ٶ�(�������ٶ�)
	 */
	float speed = 8;
	/**
	 * TextView
	 * 
	 * @param context
	 */
	TextView mTvShowText;

	/**
	 * �ṩ�ڴ�����ʹ��
	 * 
	 * @param context
	 */
	public HomeArcView(Context context) {
		super(context);
		init();
	}

	/**
	 * �����Զ����� ������style
	 * 
	 * @param context
	 * @param attrs
	 */
	public HomeArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * �����Զ����� ����style
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
	 * view�еķ��� �õ������еĿ�� ��Ҫת��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// �õ������еĿ�� ��Ҫת��
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// ��Ҫת�� ת��������ֵ
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		// �������о���
		r = new RectF(0, 0, width, height);
	}

	/**
	 * ��ʼ��Paint����
	 */
	public void init() {
		mPaint = new Paint();

		// ��ɫ
		// mPaint.setColor(getResources().getColor(R.color.lll));
		// mPaint.setColor(Color.BLACK);
		mPaint.setColor(0xffff8c00);

		// ���ʴ�С
		mPaint.setStrokeWidth(5);

		// �����
		mPaint.setAntiAlias(true);

		// new Thread(this).start();
	}

	/**
	 * ���ýǶ�û�ж���
	 */
	// public void setAngle(float angle) {
	// sweepAngle = angle;
	// postInvalidate();
	// }

	/**
	 * ���ýǶȴ�����
	 */
	public void setAngleWithAnim(final float angle) {
		// �ж� ����������У�û�з�Ӧ
		if (isRuning) {
			return;
		}

		isRuning = true;

		// ����
		final Timer time = new Timer();
		/*
		 * 1ʱ��������� 2�ӳٶ��ִ�� 3ִ��֮��ÿ�����ִ��һ��run ����ֹͣ �����ֶ�ֹͣ
		 * 
		 * ��Android�� ���̲߳���ˢ��UI UI�߳̾������߳�
		 * 
		 * task--���Ǳ����ȵ����� delay--�����Ժ���Ϊ��λ���ӳ�֮ǰ���������ִ�С�
		 * period--����������ִ������֮��ĺ����ʱ�䡣
		 */

		/**
		 * time schedule����
		 */
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				switch (state) {
				case RETURN_STATE:
					// ����
					sweepAngle -= speed;
					// ��TextView
					if (sweepAngle <= 0) {
						sweepAngle = 0;
						state = GO_STATE;
					}
					// �ػ�
					postInvalidate();
					break;
				case GO_STATE:// ǰ��̬
					sweepAngle += speed;

					// ��TextView ���������߳����޸�UI
					if (sweepAngle >= angle) {
						sweepAngle = angle;

						// �ֶ�ֹͣtime��
						time.cancel();

						isRuning = false;
						state = RETURN_STATE;
					}
					// �ػ�
					postInvalidate();
				}
			}
		}, 50, 40);
		// ǰ��

	}

	/*
	 * public void setAngleWithAnim(final float angle, TextView TvShowText) { //
	 * �ж� ����������У�û�з�Ӧ mTvShowText = TvShowText; if (isRuning) { return; }
	 * isRuning = true; // ���� final Timer time = new Timer();
	 * 
	 * 1ʱ��������� 2�ӳٶ��ִ�� 3ִ��֮��ÿ�����ִ��һ��run ����ֹͣ �����ֶ�ֹͣ
	 * 
	 * ��Android�� ���̲߳���ˢ��UI UI�߳̾������߳�
	 * 
	 * time.schedule(new TimerTask() {
	 * 
	 * @Override public void run() { switch (state) { case RETURN_STATE: // ����
	 * sweepAngle -= speed; // ��TextView
	 * 
	 * if (sweepAngle <= 0) { sweepAngle = 0; state = GO_STATE; }
	 * postInvalidate(); // �ػ� break; case GO_STATE:// ǰ��̬ sweepAngle += speed;
	 * // ��TextView
	 * 
	 * if (sweepAngle >= angle) { sweepAngle = angle; time.cancel(); isRuning =
	 * false; state = RETURN_STATE; } postInvalidate(); } }
	 * 
	 * }, 50, 40); // ǰ��
	 * 
	 * }
	 */

	/**
	 * �ṩһ��Canvas����(���߳�)�Զ�����
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ��������
		canvas.drawArc(r, -90, sweepAngle, true, mPaint);

		mOnScorceChangeListner.onChange(sweepAngle);

		// int score1 = (int) (sweepAngle / 360 * 100);
		// if (mTvShowText != null) {
		// mTvShowText.setText("" + score1);
		// }
	}

	/*
	 * @Override public void run() { for (int i = 0; i < 100; i++) { try {
	 * sweepAngle += 3; Thread.sleep(100); // �ʲ� postInvalidate(); } catch
	 * (InterruptedException e) { e.printStackTrace(); } } }
	 */

	// �ص�
	OnScorceChangeListner mOnScorceChangeListner;

	/**
	 * ���ⲿ�ýӿ�(�ص�)
	 * 
	 * @param mOnScorceChangeListner
	 */
	public void setOnScorceChangeListner(
			OnScorceChangeListner mOnScorceChangeListner) {
		this.mOnScorceChangeListner = mOnScorceChangeListner;
	}

	/**
	 * �����������ı�(�ӿ�)
	 * 
	 * @author lenovo
	 * 
	 */
	public interface OnScorceChangeListner {
		void onChange(float offset);
	}
}
