package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.RocketManagerShowAdapter;
import com.example.horsekeeper.entity.Speedinfo;
import com.example.horsekeeper.util.ProcessType;

/**
 * 手机加速主界面
 * 
 * @author lenovo
 * 
 */
// ActivityManager 管理正在运行的软件
public class RocketManagerActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnCheckedChangeListener {

	Context context;
	/**
	 * 切换按钮
	 */
	Button mBtnJump;
	/**
	 * 一键清理
	 */
	Button mBtnClear;

	ListView mList;
	LinearLayout mLlLoading;

	int index;

	boolean isFinish = true;

	ActivityManager am;

	CheckBox mCheckAll;
	/**
	 * 用户进程信息
	 */
	ArrayList<Speedinfo> Info;
	RocketManagerShowAdapter mAdapter;
	/**
	 * 静态常量
	 */
	public static final int MESSAGE_CONPLETE = 201;

	Handler handle = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_CONPLETE:
				// 在此数据加载完毕 更改数据
				mBtnClear.setClickable(true);
				mLlLoading.setVisibility(View.GONE);
				mList.setVisibility(View.VISIBLE);
				mAdapter.setData(Info);
				// 调用getView
				mAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rocket_manager);
	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		// mImgRight.setImageResource(R.drawable.ic_child_configs);
		mTvTittle.setText("手机加速");

		// 设置手机信息
		// 系统版本
		String relase = Build.VERSION.RELEASE;
		// api level
		int sdk = Build.VERSION.SDK_INT;
		// 型号
		String model = Build.MODEL;
		// 商标
		String brand = Build.BOARD;

		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		mCheckAll = (CheckBox) findViewById(R.id.cb_rocket_clear_all);
		mList = (ListView) findViewById(R.id.lv_rocket);
		mBtnJump = (Button) findViewById(R.id.btn_rocket_jump);
		mLlLoading = (LinearLayout) findViewById(R.id.ll_rocket_loading);
		mBtnClear = (Button) findViewById(R.id.btn_rocket_manager_clear);

		mAdapter = new RocketManagerShowAdapter(Info, this);

		mList.setAdapter(mAdapter);

		setOnClickListers(this, mBtnJump, mBtnClear, mImgLeft);

		mList.setOnItemClickListener(this);
		mCheckAll.setOnCheckedChangeListener(this);

		getData(index);
	}

	public void getData(final int index) {
		if (!isFinish) {
			this.index--;
			return;
		}

		mBtnJump.setText(index == 0 ? "获取系统的" : "获取用户的");
		mLlLoading.setVisibility(View.VISIBLE);
		mList.setVisibility(View.GONE);

		isFinish = false;
		new Thread() {
			@Override
			public void run() {
				Info = index == 0 ? ProcessType.USER
						.getRunningProgress(RocketManagerActivity.this)
						: ProcessType.SYSTEM
								.getRunningProgress(RocketManagerActivity.this);
				isFinish = true;
				// 通知主线程改变
				handle.sendEmptyMessage(MESSAGE_CONPLETE);
			};
		}.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_rocket_jump:
			index++;
			getData(index % 2);
			// 线程的开启(前提 上次数据已经加载完毕)
			break;
		case R.id.btn_rocket_manager_clear:
			for (Speedinfo speedInfo : Info) {
				if (speedInfo.isCheck) {
					// 需要权限
					am.killBackgroundProcesses(speedInfo.packageName);
				}
			}
			// 刷新 再去拿一次
			getData(index % 2);
			// 进度条刷新
			break;
		default:
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			return;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Speedinfo speedInfo = Info.get(position);
		speedInfo.isCheck = !speedInfo.isCheck;
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (Speedinfo speedInfo : Info) {
			speedInfo.isCheck = isChecked;
		}
		mAdapter.notifyDataSetChanged();
	}
}
