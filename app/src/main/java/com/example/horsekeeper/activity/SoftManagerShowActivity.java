package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.SoftManagerShowAdapter;
import com.example.horsekeeper.entity.AppInfo;
import com.example.horsekeeper.util.SoftContent;

/**
 * ��������������
 * 
 * @author lenovo
 * 
 */
// ListView���û��� ��Ҫ����isChected��״̬
public class SoftManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * ������
	 */
	SoftManagerShowAdapter adapter;
	/**
	 * AppInfo���͵ļ���
	 */
	ArrayList<AppInfo> listData;
	/**
	 * �ж�
	 */
	int flag;
	/**
	 * ListView
	 */
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_manager_show);
	}

	@Override
	void initView() {
		// ��ʼ��
		Intent intent = getIntent();

		// ������ ��SoftManagerActivity��תʱ����������
		flag = intent.getIntExtra("flag", SoftContent.All_SOFT);
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		if (flag == SoftContent.All_SOFT) {
			mTvTittle.setText("�������");
		} else if (flag == SoftContent.SYSTEM_SOFT) {
			mTvTittle.setText("ϵͳ���");
		} else if (flag == SoftContent.USER_SOFT) {
			mTvTittle.setText("�û����");
		}
		// �ҵ��ؼ�
		listView = (ListView) findViewById(R.id.lv_soft_manager);
		Button btnDelete = (Button) findViewById(R.id.btn_soft_manager_show_delete);

		// ж�ذ�ť����
		btnDelete.setOnClickListener(this);

		// ����Դ
		// listData = SoftContent.getSoftinfo(this, flag);

		// ����������
		// adapter = new SoftManagerShowAdapter(listData, this);
		// listView.setAdapter(adapter);

		// listView�ļ���
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// SoftManagerShowAdapter.MyHolder holder = (MyHolder) view.getTag();
		// �õ�CheckBox
		// CheckBox ch=(CheckBox)
		// view.findViewById(R.id.ch_soft_manager_show_item_choose);

		// AppInfo app = (AppInfo) adapter.getItem(position);

		// isChecked()�õ���ǰ��״̬
		// holder.checkBox.setChecked(��holder.checkBox.isChecked());
		// if (holder.checkBox.isChecked()) {
		// holder.checkBox.setChecked(false);
		// } else {
		// holder.checkBox.setChecked(true);
		// }
		//
		// app.isChecked = holder.checkBox.isChecked();

		AppInfo app = (AppInfo) adapter.getItem(position);
		// app.isChecked = !app.isChecked;

		// ��Ҫ��סisChecked��״̬
		listData.get(position).setChecked(!app.isChecked);

		// ���ݱ��� ����û�б�
		// ˢ��ListView���� �������ķ���
		// ˢ��ListView �Զ�����getView����
		adapter.notifyDataSetChanged();
		// adapter.notifyDataSetInvalidated();// ˢ��ListView �Զ�����getView����
	}

	@Override
	public void onClick(View v) {
		// ж��---����ѡ��
		/*
		 * ������ͼ ���������ѡ��İ���
		 */
		for (int i = 0; i < listData.size(); i++) {
			if (listData.get(i).isChecked) {

				Intent intent = new Intent(
						Intent.ACTION_DELETE,
						Uri.parse("package:" + listData.get(i).getPackageName()));
				startActivity(intent);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// ���²�������
		// ˢ�½���---��ʱ
		listData = SoftContent.getSoftinfo(this, flag);
		adapter = new SoftManagerShowAdapter(listData, this);
		listView.setAdapter(adapter);
	}
}
