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
 * 垃圾清除
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
	 * 泛型为CleanGroupInfo的集合
	 */
	ArrayList<CleanGroupInfo> infos;
	/**
	 * 适配器
	 */
	CleanManagerAdapter adapter;
	/**
	 * 全选选框
	 */
	CheckBox mChAll;
	/**
	 * 删除按钮
	 */
	Button mButton;
	/**
	 * 开关
	 */
	boolean flag = true;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// 初始化完成
			case CleanUtil.INIT_FINISH:
				adapter.mList = CleanUtil.getData();
				adapter.notifyDataSetChanged();
				break;
			// 加载中
			case CleanUtil.LOADING:
				// 需要改上面的总数
				long allSize = 0;
				for (CleanGroupInfo groupInfo : CleanUtil.getData()) {
					allSize += groupInfo.groupSize;
				}
				mTvAllSize.setText(FileUtils.formatLength(allSize));
				break;
			// 已完成
			case CleanUtil.FINISH:
				// 改变加载状态
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
		mTvTittle.setText("垃圾清理");

		// 4096 block size ===块的最小单位--->倍数
		ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.elv_clean_list);

		mTvAllSize = (TextView) findViewById(R.id.tv_clean_size);
		mChAll = (CheckBox) findViewById(R.id.cb_clean_all);
		mButton = (Button) findViewById(R.id.btn_clean_list);

		mChAll.setOnCheckedChangeListener(this);

		setOnClickListers(this, mImgLeft, mButton);

		// new CleanUtil().setOnFileloadingListener(this);

		// 数据源(应放入子线程)
		// infos = CleanUtil.getClearInfo(this);
		new Thread(this).start();

		adapter = new CleanManagerAdapter(infos, this);
		mExpandableListView.setAdapter(adapter);

		mExpandableListView.setOnGroupClickListener(this);
		mExpandableListView.setOnChildClickListener(this);

	}

	/**
	 * 子线程拿数据
	 */
	@Override
	public void run() {
		// 子线程加载数据
		infos = CleanUtil.getClearInfo(this, handler);
		// 加载完毕
	}

	// @Override
	// public void onLoadingFinish() {
	// 数据加载完毕的事件 info
	// 手动改适配器的数据
	//
	// ToastUtil.toastLong(this, "加载完毕");
	// }

	/**
	 * 选取所有选框
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.cb_clean_all:// 全选
			// 1. 改所有Child里面isChecked数据 2.刷新界面
			if (flag) {
				for (CleanGroupInfo groupInfo : adapter.mList) {
					for (CleanChildInfo info : groupInfo.content) {
						info.isChecked = isChecked;
					}
				}

				// 1. adapter.notifyDataSetChanged();

				// 这是

				// ArrayList<View> views = adapter.getAllChildView();
				// for (View view : views) {
				// LogWapper.e("aaa", "----" + view);

				// ChildHolder holder = (ChildHolder) view.getTag();
				// holder.mCheck.setChecked(isChecked);
				// }

			}
			// 刷新界面
			adapter.notifyDataSetChanged();
			break;

		}
	}

	/**
	 * 清除按钮
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 清理垃圾
		case R.id.btn_clean_list:
			// 是否有垃圾可删除
			boolean hasDelect = false;
			for (CleanGroupInfo groupInfo : adapter.mList) {
				for (CleanChildInfo info : groupInfo.content) {
					if (info.isChecked) {
						// 打开重新获取数据的开关
						hasDelect = true;
						// 清理
						FileUtils.deleteFolderFile(info.file, true);
					}
				}
			}
			// 数据删除完毕----------------重新获取新数据
			if (hasDelect) {
				// 将全选框钩去掉
				mChAll.setChecked(false);
				// 重新获取数据
				new Thread(this).start();
			} else {
				ToastUtil.toastLong(this, "没有选定对象");
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
	 * 组点击事件
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// 加载中 true 加载完成false
		// 返回 true 说明当前的这个处理有效 false无效

		// 这TM是什么？
		return adapter.mList.get(1).isLoading;
	}

	/**
	 * 子点击事件
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// 勾上------->1.Child数据变 2.界面变
		CleanChildInfo info = adapter.mList.get(groupPosition).content
				.get(childPosition);

		//
		info.isChecked = !info.isChecked;
		// 1.
		// adapter.notifyDataSetChanged();

		// 2.v 所点击的View 最好的
		ChildHolder holder = (ChildHolder) v.getTag();
		holder.mCheck.setChecked(!holder.mCheck.isChecked());

		// 3.v 所点击的View
		// CheckBox checkBox=(CheckBox)
		// v.findViewById(R.id.ch_child_check);
		// checkBox.setChecked(!checkBox.isChecked());

		// 1.判断 是否影响全选按钮 选中 不选中
		if (!holder.mCheck.isChecked()) { // 全选取消
			flag = false;
			mChAll.setChecked(false);
			flag = true;
		}
		boolean isAllChecked = true;
		// 全选 选中-----------> 每个CheckBox都勾上 有一个不勾就不行

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
