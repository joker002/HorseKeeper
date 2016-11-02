package com.example.horsekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.util.MemoryManager;
import com.example.horsekeeper.view.HomeArcView;
import com.example.horsekeeper.view.HomeArcView.OnScorceChangeListner;

/**
 * ������
 * 
 * @author lenovo
 * 
 */
// �Զ���View xml(include)
// �ص�
// ���Զ���View���õ���Timer ����#schedule ��д��onMeasure����
public class HomeActivity extends BaseActivity implements OnClickListener,
		OnScorceChangeListner {
	/**
	 * ImageViewԲ��ͼƬ
	 */
	ImageView mTvScoreSpeed;

	ImageView mAnimation;
	/**
	 * HomeArcView�Զ���view�ؼ�
	 */
	HomeArcView mHvArc;
	/**
	 * TextViewԲ������
	 */
	TextView mTvShowScore;

	/**
	 * ����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// ���ø���
		super.onCreate(savedInstanceState);

		// ������ͼ ����BaseActivity�ķ���
		setContentView(R.layout.activity_home);

		// MemoryManager.getRunMemory();
	}

	/**
	 * ��дHomeActivity�ĳ��󷽷�initView
	 */
	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.ic_launcher);
		mImgRight.setImageResource(R.drawable.ic_child_configs);
		mTvTittle.setText("��׿�ܼ�");
		/**
		 * �ҵ�Բ��ͼƬ�ؼ�
		 */
		mTvScoreSpeed = (ImageView) findViewById(R.id.iv_home_score);
		/**
		 * �ҵ��Զ���view�ؼ�
		 */
		mHvArc = (HomeArcView) findViewById(R.id.hv_home);
		/**
		 * �ҵ�Բ�����ֿؼ�
		 */
		mTvShowScore = (TextView) findViewById(R.id.tv_home_num);

		// �̳�Activity��BaseActivity�ķ���setOnClickListers
		setOnClickListers(this, mTvScoreSpeed,
				findViewById(R.id.tv_telmgr_tel_home),
				findViewById(R.id.pv_softmgr),
				findViewById(R.id.tv_filemgr_home),
				findViewById(R.id.tv_rocket),
				findViewById(R.id.tv_icon_phonemgr_home),
				findViewById(R.id.tv_icon_sdclean_home),
				findViewById(R.id.iv_base_layout_right));

		/*
		 * 1.��ȡ�ڴ�Akb 2.��ȡʣ���ڴ�Bkb 3.��ռ�ڴ�ٷֱ�(A��B)/A 4.����Ƕ�(A-B)/A *360
		 */

		/**
		 * ����HomeArcView��setOnScorceChangeListner����
		 */
		mHvArc.setOnScorceChangeListner(this);

		/**
		 * ����HomeArcView��setAngleWithAnim����
		 */
		MemoryManager.getRunAvailableMemory(this);
		MemoryManager.getRunMemory();

		mHvArc.setAngleWithAnim(320);
	}

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_home_score:
			// ����--->�����Զ���View
			// mHvArc.setAngle(150);
			/*
			 * �����ڴ�
			 * 
			 * ��һ�� 1.��ȡ�ڴ�Akb 2.��ȡʣ���ڴ�Bkb 3.��ռ�ڴ�ٷֱ�(A��B)/A 4.����Ƕ�(A-B)/A *360
			 */
			mHvArc.setAngleWithAnim(170);
			return;
		case R.id.tv_telmgr_tel_home:
			// ��ת
			// 1 ��ʼ��intent
			// 2 ����Intent����setClass
			intent.setClass(this, TelManagerActivity.class);
			// 3 ����starActivity(����)
			break;
		case R.id.pv_softmgr:
			// ��ת
			// 1 ��ʼ��intent
			// 2 ����Intent����setClass
			intent.setClass(this, SoftManagerActivity.class);
			// 3 ����starActivity(����)
			break;
		case R.id.tv_filemgr_home:
			intent.setClass(this, FileManagerActivity.class);
			break;
		case R.id.tv_rocket:
			intent.setClass(this, RocketManagerActivity.class);
			break;
		case R.id.tv_icon_phonemgr_home:
			intent.setClass(this, PhoneMgrActivity.class);
			break;
		case R.id.tv_icon_sdclean_home:
			intent.setClass(this, CleanManagerActivity.class);
			break;
		case R.id.iv_base_layout_right:
			intent.setClass(this, SettingActivity.class);
			break;
		}
		// ����
		startActivity(intent);
	}

	/**
	 * ��дHomeArcView��OnScorceChangeListner����onChange
	 */
	@Override
	public void onChange(float offset) {
		int score1 = (int) (offset / 360 * 100);
		// int���͵���Ҫ��""
		mTvShowScore.setText(score1 + "");
	}
}
