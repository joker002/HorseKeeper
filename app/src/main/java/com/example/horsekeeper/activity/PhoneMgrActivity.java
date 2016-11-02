package com.example.horsekeeper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.util.FileUtils;
import com.example.horsekeeper.util.MemoryManager;
import com.example.horsekeeper.util.PhoneTextUtil;

/**
 * �ֻ����������
 * 
 * @author cc
 * 
 */
public class PhoneMgrActivity extends BaseActivity implements OnClickListener {
	ProgressBar progressBar;
	TextView tv;
	/**
	 * ��̬����
	 */
	public static final int START = 200;
	public static final int STOP = 201;

	Handler hanler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case START:
				progressBar.setProgress((progressBar.getProgress() + 3) % 100);
				hanler.sendEmptyMessageDelayed(START, 400);
				break;
			case STOP:
				hanler.removeMessages(START);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BatteryRecive receiver = new BatteryRecive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(receiver, filter);

		setContentView(R.layout.activity_phone_mgr_manger);

	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		// mImgRight.setImageResource(R.drawable.ic_child_configs);
		mTvTittle.setText("�ֻ����");

		// LogWapper.e("aaa", "" + PhoneTextUtil.getCpuName());
		// LogWapper.e("aaa", "" + PhoneTextUtil.getCpuCores());
		progressBar = (ProgressBar) findViewById(R.id.pb_phone_battern);
		tv = (TextView) findViewById(R.id.tv_phone_battern);

		TextView tv1 = (TextView) findViewById(R.id.tv_phone_name);
		TextView tv2 = (TextView) findViewById(R.id.tv_phone_battern);
		TextView tv3 = (TextView) findViewById(R.id.tv_phone_running);
		TextView tv4 = (TextView) findViewById(R.id.tv_phone_residue_running);

		TextView tv5 = (TextView) findViewById(R.id.tv_phone_cpu_name);
		TextView tv6 = (TextView) findViewById(R.id.tv_phone_cpu_number);
		TextView tv7 = (TextView) findViewById(R.id.tv_phone_resolution);
		TextView tv8 = (TextView) findViewById(R.id.tv_phone_camera_resolution);
		TextView tv9 = (TextView) findViewById(R.id.tv_phone_base_band);
		TextView tv10 = (TextView) findViewById(R.id.tv_phone_root);

		tv1.setText("" + Build.MODEL);
		tv2.setText("" + Build.VERSION.RELEASE);
		tv3.setText("ȫ�������ڴ�:"
				+ FileUtils.formatLength(MemoryManager.getRunMemory()));
		tv4.setText("ʣ�������ڴ�:"
				+ FileUtils.formatLength(MemoryManager
						.getRunAvailableMemory(this)));
		tv5.setText("cpu����:" + PhoneTextUtil.getCpuName());
		tv6.setText("cpu����:" + PhoneTextUtil.getCpuCores());

		tv7.setText("�ֻ��ֱ���:" + PhoneTextUtil.getPhoneRadios(this));

		tv8.setText("����ֱ���:" + PhoneTextUtil.getCameraRadios());
		tv9.setText("�����汾:" + PhoneTextUtil.getBasebandVersion());
		tv10.setText("�Ƿ�ROOT�ֻ�:"
				+ (PhoneTextUtil.isRoot() == false ? "��" : "��"));
		setOnClickListers(this, mImgLeft);
	}

	/**
	 * ��ؽ�����
	 * 
	 * @author lenovo
	 * 
	 */
	public class BatteryRecive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			int max = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			// ��ǰ�ֻ�״̬
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 100);
			if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
				hanler.removeMessages(START);
				hanler.sendEmptyMessage(START);
			} else {
				hanler.sendEmptyMessage(STOP);
			}
			progressBar.setProgress(level);
			progressBar.setMax(max);
			tv.setText(level * 100 / max + "%");
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
