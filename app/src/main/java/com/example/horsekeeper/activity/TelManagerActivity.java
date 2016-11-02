package com.example.horsekeeper.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.TelManagerGridAdapter;
import com.example.horsekeeper.db.TELDBUtil;
import com.example.horsekeeper.util.Constants;

/**
 * ͨѶ¼������ ��ʼ������ ����������
 * 
 * @author lenovo
 * 
 */
// ��Intent��ֵ Bundle(�洢)
// �ļ��Ŀ����Ͳ���
// GridView���񲼾�
// OnItemClickListener��Ŀ�����¼�
// AssetManager �������ʲ���,�����Ϊ���ʵ�ǰӦ�ó�����ʲ��ļ��ṩ�����
public class TelManagerActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * ����һ������ ������String���͵�����
	 */
	ArrayList<String> arrDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����BaseActivity�ķ��� �����initView
		setContentView(R.layout.activity_tel_manager);
	}

	/**
	 * ��ʼ������
	 */
	public void initDate() {
		try {
			// ������ ��������
			TELDBUtil.copeDB(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ������ �õ����ݿ�����
		arrDate = TELDBUtil.selectClasslist();
	}

	/**
	 * �ڸ���setContentView����Զ�����
	 */
	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("ͨѶ��ȫ");
		// mImgRight.setVisibility(View.INVISIBLE);

		// ��ʼ������
		initDate();

		// �ҵ�GridView�ؼ� ��Ҫ������
		GridView gridView = (GridView) findViewById(R.id.gv_tel_manager);

		// ��ʼ��ͨѶ¼��������������
		// getLayoutInflater()�����õ���Ⱦ��
		TelManagerGridAdapter adapter = new TelManagerGridAdapter(arrDate,
				getLayoutInflater());

		// ���ؼ����ص��������� �ؼ���.setAdapter(������)
		gridView.setAdapter(adapter);
		// �кܶ��,���кܶ���
		// setOnItemClickListener��Ŀ����¼�
		gridView.setOnItemClickListener(this);

		setOnClickListers(this, mImgLeft);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(this, TelManagerShowActivity.class);

		// ��ת��ʱ��ֵ �����ȷ���Bundle����

		// Bundle bun = new Bundle();
		// bun.putInt(Constants.PUT_POSITION, position);
		// intent.putExtras(bun);

		// Constants.PUT_POSITION �Լ�����ĳ���(��ֵ��)

		intent.putExtra(Constants.PUT_POSITION, position);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
