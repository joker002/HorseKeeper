package com.example.horsekeeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.util.LogWapper;

/**
 * Activity�Ļ���
 * 
 * @author lenovo
 * 
 */
public abstract class BaseActivity extends Activity {
	// ��ȡ��ǰActivity������
	public String TAG = this.getClass().getSimpleName();
	RelativeLayout mRelativeLayout;
	/**
	 * ��Ⱦ��
	 */
	LayoutInflater mInflater;

	ImageView mImgLeft;
	ImageView mImgRight;
	TextView mTvTittle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWapper.e(TAG, "onCreate----");

		// �����ุ���setContentView
		super.setContentView(R.layout.base_layout);

		mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_base_content);
		mInflater = getLayoutInflater();
		mImgLeft = (ImageView) findViewById(R.id.iv_base_layout_lift);
		mImgRight = (ImageView) findViewById(R.id.iv_base_layout_right);
		mTvTittle = (TextView) findViewById(R.id.tv_base_layout_tittle);
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogWapper.e(TAG, "onStart----");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogWapper.e(TAG, "onResume----");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogWapper.e(TAG, "onPause----");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogWapper.e(TAG, "onStop----");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogWapper.e(TAG, "onRestart----");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogWapper.e(TAG, "onDestroy----");
	}

	/**
	 * �Ǽ� ģ����� �ڼ��ز���֮���Զ����� initView����
	 */
	@Override
	public final void setContentView(int id) {

		mInflater.inflate(id, mRelativeLayout);

		initView();
	}

	@Override
	public final void setContentView(View view) {

		mRelativeLayout.addView(view);
		initView();
	}

	/**
	 * BaseActivity�ĳ��󷽷�
	 */
	abstract void initView();

	/**
	 * 
	 * @param lister
	 *            ���ü����¼�
	 * @param views
	 * 
	 */
	protected void setOnClickListers(OnClickListener lister, View... views) {
		if (lister == null) {
			return;
		}
		for (View view : views) {
			view.setOnClickListener(lister);
		}

	}

	/**
	 * 
	 * @param lister
	 *            ���ü����¼�
	 * @param ids
	 *            �ؼ�id
	 */
	protected void setOnClickListers(OnClickListener lister, int... ids) {
		if (lister == null) {
			return;
		}
		for (int id : ids) {
			findViewById(id).setOnClickListener(lister);
		}

	}
}
