package com.example.horsekeeper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.util.Constants;

/**
 * 设置按钮
 * 
 * @author lenovo
 * 
 */
public class SettingActivity extends BaseActivity implements OnClickListener {
	ImageView mImageView;
	SharedPreferences preferences;
	String isBootStart = "IS_BOOT_START";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("设置");

		preferences = getSharedPreferences(Constants.PREFRENCE_NAME,
				MODE_PRIVATE);

		// LogWapper.e("aaa", "" + isBootStart);

		boolean flag = preferences.getBoolean(isBootStart, false);

		mImageView = (ImageView) findViewById(R.id.iv_setting_first_switch);

		mImageView.setSelected(flag);

		setOnClickListers(this, mImageView);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_setting_first_switch:
			mImageView.setSelected(!mImageView.isSelected());
			// 开机启动 广播
			break;
		}
	}

	/**
	 * 开机启动广播接收器
	 * 
	 * @author lenovo
	 * 
	 */
	public class StartReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 开机启动 ANR---->activity 5s没响应 广播接收器10s没有相应 线程耗时操作
			// Thread+Handler 解决ANR异常
			// FC 强制关闭 Error:OOM(outOfMemoryError StackOverFolwError)
			// RuntimeException

			// ToastUtil.toastLong(context, "aaa");

			if (mImageView.isSelected()) {
				Intent intent2 = new Intent(context, HomeActivity.class);
				// Activity的启动方式
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent2);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// 把参数保存在SharePrefrence

		Editor editor = preferences.edit();
		// LogWapper.e("aaa", "" + mImageView.isSelected());
		editor.putBoolean(isBootStart, mImageView.isSelected());
		// 提交生效
		editor.commit();
		super.onDestroy();
	}
}
