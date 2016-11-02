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
 * ���ð�ť
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
		mTvTittle.setText("����");

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
			// �������� �㲥
			break;
		}
	}

	/**
	 * ���������㲥������
	 * 
	 * @author lenovo
	 * 
	 */
	public class StartReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// �������� ANR---->activity 5sû��Ӧ �㲥������10sû����Ӧ �̺߳�ʱ����
			// Thread+Handler ���ANR�쳣
			// FC ǿ�ƹر� Error:OOM(outOfMemoryError StackOverFolwError)
			// RuntimeException

			// ToastUtil.toastLong(context, "aaa");

			if (mImageView.isSelected()) {
				Intent intent2 = new Intent(context, HomeActivity.class);
				// Activity��������ʽ
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent2);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// �Ѳ���������SharePrefrence

		Editor editor = preferences.edit();
		// LogWapper.e("aaa", "" + mImageView.isSelected());
		editor.putBoolean(isBootStart, mImageView.isSelected());
		// �ύ��Ч
		editor.commit();
		super.onDestroy();
	}
}
