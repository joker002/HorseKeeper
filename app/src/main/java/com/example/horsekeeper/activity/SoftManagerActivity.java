package com.example.horsekeeper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.util.FileUtils;
import com.example.horsekeeper.util.MemoryManager;
import com.example.horsekeeper.util.SoftContent;
import com.example.horsekeeper.view.PieCustumView;

/**
 * �������������
 * 
 * @author lenovo
 * 
 */
// PackageManager������ ������Ҫְ���ǹ���Ӧ�ó������ͨ���������ǿ��Ի�ȡӦ�ó�����Ϣ
// ��ȡ�ֻ����ڴ���ϢMemoryManager������

public class SoftManagerActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_manager);
	}

	@Override
	void initView() {

		PieCustumView p = (PieCustumView) findViewById(R.id.pv_soft_manager);

		long selfMemory = MemoryManager.getSelfMemory(true);
		long outSDCardMemory = MemoryManager.getOutSDCardMemory(this, true);

		// LogWapper.e("aaa", "" + selfMemory);
		// LogWapper.e("aaa", "" + outSDCardMemory);
		Float self = (float) ((selfMemory * 0.1 / (selfMemory + outSDCardMemory)) * 10);
		// LogWapper.e("aaa", "" + self);
		p.setAngle(self);

		LinearLayout linAll = (LinearLayout) findViewById(R.id.ll_soft_manager_all_soft);
		LinearLayout linSystem = (LinearLayout) findViewById(R.id.ll_soft_manager_system_soft);
		LinearLayout linUser = (LinearLayout) findViewById(R.id.ll_soft_manager_user_soft);

		ProgressBar p1 = (ProgressBar) findViewById(R.id.pb_soft_manager_self);
		ProgressBar p2 = (ProgressBar) findViewById(R.id.pb_soft_manager);
		TextView tv1 = (TextView) findViewById(R.id.tv_soft_manager_self);
		TextView tv2 = (TextView) findViewById(R.id.tv_soft_manager);

		long l = selfMemory - MemoryManager.getSelfMemory(false);
		long l1 = outSDCardMemory
				- MemoryManager.getOutSDCardMemory(this, false);

		int i = (int) ((l * 100 / selfMemory));
		int i1 = (int) ((l1 * 100 / outSDCardMemory));

		// LogWapper.e("aaa", "---" + l1 / outSDCardMemory);
		// LogWapper.e("aaa", "---" + i1);

		p1.setProgress(i);
		p2.setProgress(i1);

		tv1.setText("����:"
				+ FileUtils.formatLength(MemoryManager.getSelfMemory(false))
				+ "/" + FileUtils.formatLength(selfMemory));
		tv2.setText("����:"
				+ FileUtils.formatLength(MemoryManager.getOutSDCardMemory(this,
						false)) + "/" + FileUtils.formatLength(outSDCardMemory));

		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("�������");

		setOnClickListers(this, linAll, linSystem, linUser, mImgLeft);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, SoftManagerShowActivity.class);
		switch (v.getId()) {
		case R.id.ll_soft_manager_all_soft:
			intent.putExtra("flag", SoftContent.All_SOFT);
			break;
		case R.id.ll_soft_manager_system_soft:
			intent.putExtra("flag", SoftContent.SYSTEM_SOFT);
			break;
		case R.id.ll_soft_manager_user_soft:
			intent.putExtra("flag", SoftContent.USER_SOFT);
			break;
		// ����
		default:
			finish();
			return;
		}
		startActivity(intent);
	}
}
