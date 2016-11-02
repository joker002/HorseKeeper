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
 * 主界面
 * 
 * @author lenovo
 * 
 */
// 自定义View xml(include)
// 回掉
// 在自定义View中用到了Timer 对象#schedule 重写了onMeasure方法
public class HomeActivity extends BaseActivity implements OnClickListener,
		OnScorceChangeListner {
	/**
	 * ImageView圆饼图片
	 */
	ImageView mTvScoreSpeed;

	ImageView mAnimation;
	/**
	 * HomeArcView自定义view控件
	 */
	HomeArcView mHvArc;
	/**
	 * TextView圆饼数字
	 */
	TextView mTvShowScore;

	/**
	 * 启动
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// 调用父类
		super.onCreate(savedInstanceState);

		// 加载视图 这是BaseActivity的方法
		setContentView(R.layout.activity_home);

		// MemoryManager.getRunMemory();
	}

	/**
	 * 重写HomeActivity的抽象方法initView
	 */
	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.ic_launcher);
		mImgRight.setImageResource(R.drawable.ic_child_configs);
		mTvTittle.setText("安卓管家");
		/**
		 * 找到圆饼图片控件
		 */
		mTvScoreSpeed = (ImageView) findViewById(R.id.iv_home_score);
		/**
		 * 找到自定义view控件
		 */
		mHvArc = (HomeArcView) findViewById(R.id.hv_home);
		/**
		 * 找到圆饼数字控件
		 */
		mTvShowScore = (TextView) findViewById(R.id.tv_home_num);

		// 继承Activity的BaseActivity的方法setOnClickListers
		setOnClickListers(this, mTvScoreSpeed,
				findViewById(R.id.tv_telmgr_tel_home),
				findViewById(R.id.pv_softmgr),
				findViewById(R.id.tv_filemgr_home),
				findViewById(R.id.tv_rocket),
				findViewById(R.id.tv_icon_phonemgr_home),
				findViewById(R.id.tv_icon_sdclean_home),
				findViewById(R.id.iv_base_layout_right));

		/*
		 * 1.读取内存Akb 2.读取剩余内存Bkb 3.已占内存百分比(A—B)/A 4.算出角度(A-B)/A *360
		 */

		/**
		 * 调用HomeArcView的setOnScorceChangeListner方法
		 */
		mHvArc.setOnScorceChangeListner(this);

		/**
		 * 调用HomeArcView的setAngleWithAnim方法
		 */
		MemoryManager.getRunAvailableMemory(this);
		MemoryManager.getRunMemory();

		mHvArc.setAngleWithAnim(320);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_home_score:
			// 加速--->控制自定义View
			// mHvArc.setAngle(150);
			/*
			 * 清理内存
			 * 
			 * 算一次 1.读取内存Akb 2.读取剩余内存Bkb 3.已占内存百分比(A—B)/A 4.算出角度(A-B)/A *360
			 */
			mHvArc.setAngleWithAnim(170);
			return;
		case R.id.tv_telmgr_tel_home:
			// 跳转
			// 1 初始化intent
			// 2 调用Intent方法setClass
			intent.setClass(this, TelManagerActivity.class);
			// 3 启动starActivity(对象)
			break;
		case R.id.pv_softmgr:
			// 跳转
			// 1 初始化intent
			// 2 调用Intent方法setClass
			intent.setClass(this, SoftManagerActivity.class);
			// 3 启动starActivity(对象)
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
		// 启动
		startActivity(intent);
	}

	/**
	 * 重写HomeArcView里OnScorceChangeListner方法onChange
	 */
	@Override
	public void onChange(float offset) {
		int score1 = (int) (offset / 360 * 100);
		// int类型的需要加""
		mTvShowScore.setText(score1 + "");
	}
}
