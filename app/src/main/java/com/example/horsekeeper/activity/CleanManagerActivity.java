package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.CleanManagerAdapter;
import com.example.horsekeeper.adapter.CleanManagerAdapter.ChildHolder;
import com.example.horsekeeper.entity.CleanChildInfo;
import com.example.horsekeeper.entity.CleanGroupInfo;
import com.example.horsekeeper.util.CleanUtil;
import com.example.horsekeeper.util.FileUtils;
import com.example.horsekeeper.util.ToastUtil;

/**
 * �������
 * 
 * @author lenovo
 * 
 */
public class CleanManagerActivity extends BaseActivity implements
		OnClickListener, Runnable, OnCheckedChangeListener,
		OnGroupClickListener, OnChildClickListener {

	// 09-11 17:48:58.404: E/AndroidRuntime(18680): java.lang.SecurityException:
	// Package com.meizu.flyme.service.find does not belong to 10130

	TextView mTvAllSize;
	/**
	 * ����ΪCleanGroupInfo�ļ���
	 */
	ArrayList<CleanGroupInfo> infos;
	/**
	 * ������
	 */
	CleanManagerAdapter adapter;
	/**
	 * ȫѡѡ��
	 */
	CheckBox mChAll;
	/**
	 * ɾ����ť
	 */
	Button mButton;
	/**
	 * ����
	 */
	boolean flag = true;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// ��ʼ�����
			case CleanUtil.INIT_FINISH:
				adapter.mList = CleanUtil.getData();
				adapter.notifyDataSetChanged();
				break;
			// ������
			case CleanUtil.LOADING:
				// ��Ҫ�����������
				long allSize = 0;
				for (CleanGroupInfo groupInfo : CleanUtil.getData()) {
					allSize += groupInfo.groupSize;
				}
				mTvAllSize.setText(FileUtils.formatLength(allSize));
				break;
			// �����
			case CleanUtil.FINISH:
				// �ı����״̬
				for (CleanGroupInfo groupInfo : CleanUtil.getData()) {
					groupInfo.isLoading = false;
				}
				adapter.mList = CleanUtil.getData();
				adapter.notifyDataSetChanged();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean_manager);
	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("��������");

		// 4096 block size ===�����С��λ--->����
		ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.elv_clean_list);

		mTvAllSize = (TextView) findViewById(R.id.tv_clean_size);
		mChAll = (CheckBox) findViewById(R.id.cb_clean_all);
		mButton = (Button) findViewById(R.id.btn_clean_list);

		mChAll.setOnCheckedChangeListener(this);

		setOnClickListers(this, mImgLeft, mButton);

		// new CleanUtil().setOnFileloadingListener(this);

		// ����Դ(Ӧ�������߳�)
		// infos = CleanUtil.getClearInfo(this);
		new Thread(this).start();

		adapter = new CleanManagerAdapter(infos, this);
		mExpandableListView.setAdapter(adapter);

		mExpandableListView.setOnGroupClickListener(this);
		mExpandableListView.setOnChildClickListener(this);

	}

	/**
	 * ���߳�������
	 */
	@Override
	public void run() {
		// ���̼߳�������
		infos = CleanUtil.getClearInfo(this, handler);
		// �������
	}

	// @Override
	// public void onLoadingFinish() {
	// ���ݼ�����ϵ��¼� info
	// �ֶ���������������
	//
	// ToastUtil.toastLong(this, "�������");
	// }

	/**
	 * ѡȡ����ѡ��
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.cb_clean_all:// ȫѡ
			// 1. ������Child����isChecked���� 2.ˢ�½���
			if (flag) {
				for (CleanGroupInfo groupInfo : adapter.mList) {
					for (CleanChildInfo info : groupInfo.content) {
						info.isChecked = isChecked;
					}
				}

				// 1. adapter.notifyDataSetChanged();

				// ����

				// ArrayList<View> views = adapter.getAllChildView();
				// for (View view : views) {
				// LogWapper.e("aaa", "----" + view);

				// ChildHolder holder = (ChildHolder) view.getTag();
				// holder.mCheck.setChecked(isChecked);
				// }

			}
			// ˢ�½���
			adapter.notifyDataSetChanged();
			break;

		}
	}

	/**
	 * �����ť
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// ��������
		case R.id.btn_clean_list:
			// �Ƿ���������ɾ��
			boolean hasDelect = false;
			for (CleanGroupInfo groupInfo : adapter.mList) {
				for (CleanChildInfo info : groupInfo.content) {
					if (info.isChecked) {
						// �����»�ȡ���ݵĿ���
						hasDelect = true;
						// ����
						FileUtils.deleteFolderFile(info.file, true);
					}
				}
			}
			// ����ɾ�����----------------���»�ȡ������
			if (hasDelect) {
				// ��ȫѡ��ȥ��
				mChAll.setChecked(false);
				// ���»�ȡ����
				new Thread(this).start();
			} else {
				ToastUtil.toastLong(this, "û��ѡ������");
			}
			break;
		default:
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			return;
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		for (CleanGroupInfo groupInfo : CleanUtil.getData()) {
			groupInfo.isLoading = true;
		}
	}

	/**
	 * �����¼�
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// ������ true �������false
		// ���� true ˵����ǰ�����������Ч false��Ч

		// ��TM��ʲô��
		return adapter.mList.get(1).isLoading;
	}

	/**
	 * �ӵ���¼�
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// ����------->1.Child���ݱ� 2.�����
		CleanChildInfo info = adapter.mList.get(groupPosition).content
				.get(childPosition);

		//
		info.isChecked = !info.isChecked;
		// 1.
		// adapter.notifyDataSetChanged();

		// 2.v �������View ��õ�
		ChildHolder holder = (ChildHolder) v.getTag();
		holder.mCheck.setChecked(!holder.mCheck.isChecked());

		// 3.v �������View
		// CheckBox checkBox=(CheckBox)
		// v.findViewById(R.id.ch_child_check);
		// checkBox.setChecked(!checkBox.isChecked());

		// 1.�ж� �Ƿ�Ӱ��ȫѡ��ť ѡ�� ��ѡ��
		if (!holder.mCheck.isChecked()) { // ȫѡȡ��
			flag = false;
			mChAll.setChecked(false);
			flag = true;
		}
		boolean isAllChecked = true;
		// ȫѡ ѡ��-----------> ÿ��CheckBox������ ��һ�������Ͳ���

		for (CleanGroupInfo groupInfo : adapter.mList) {
			for (CleanChildInfo child : groupInfo.content) {
				if (!child.isChecked) {
					isAllChecked = false;
					break;
				}
			}
			if (!isAllChecked) {
				break;
			}
		}
		flag = false;
		mChAll.setChecked(isAllChecked);
		flag = true;
		return true;
	}
}
