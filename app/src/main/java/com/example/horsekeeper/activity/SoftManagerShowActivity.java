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
 * 软件管理二级界面
 * 
 * @author lenovo
 * 
 */
// ListView复用机制 需要保存isChected的状态
public class SoftManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * 适配器
	 */
	SoftManagerShowAdapter adapter;
	/**
	 * AppInfo泛型的集合
	 */
	ArrayList<AppInfo> listData;
	/**
	 * 判断
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
		// 初始化
		Intent intent = getIntent();

		// 拿数据 从SoftManagerActivity跳转时传来的数据
		flag = intent.getIntExtra("flag", SoftContent.All_SOFT);
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		if (flag == SoftContent.All_SOFT) {
			mTvTittle.setText("所有软件");
		} else if (flag == SoftContent.SYSTEM_SOFT) {
			mTvTittle.setText("系统软件");
		} else if (flag == SoftContent.USER_SOFT) {
			mTvTittle.setText("用户软件");
		}
		// 找到控件
		listView = (ListView) findViewById(R.id.lv_soft_manager);
		Button btnDelete = (Button) findViewById(R.id.btn_soft_manager_show_delete);

		// 卸载按钮监听
		btnDelete.setOnClickListener(this);

		// 数据源
		// listData = SoftContent.getSoftinfo(this, flag);

		// 加载适配器
		// adapter = new SoftManagerShowAdapter(listData, this);
		// listView.setAdapter(adapter);

		// listView的监听
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// SoftManagerShowAdapter.MyHolder holder = (MyHolder) view.getTag();
		// 拿到CheckBox
		// CheckBox ch=(CheckBox)
		// view.findViewById(R.id.ch_soft_manager_show_item_choose);

		// AppInfo app = (AppInfo) adapter.getItem(position);

		// isChecked()拿到当前的状态
		// holder.checkBox.setChecked(！holder.checkBox.isChecked());
		// if (holder.checkBox.isChecked()) {
		// holder.checkBox.setChecked(false);
		// } else {
		// holder.checkBox.setChecked(true);
		// }
		//
		// app.isChecked = holder.checkBox.isChecked();

		AppInfo app = (AppInfo) adapter.getItem(position);
		// app.isChecked = !app.isChecked;

		// 需要记住isChecked的状态
		listData.get(position).setChecked(!app.isChecked);

		// 数据变了 界面没有变
		// 刷新ListView界面 适配器的方法
		// 刷新ListView 自动调用getView方法
		adapter.notifyDataSetChanged();
		// adapter.notifyDataSetInvalidated();// 刷新ListView 自动调用getView方法
	}

	@Override
	public void onClick(View v) {
		// 卸载---所有选择
		/*
		 * 隐视意图 获得所有所选择的包名
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
		// 重新查找数据
		// 刷新界面---耗时
		listData = SoftContent.getSoftinfo(this, flag);
		adapter = new SoftManagerShowAdapter(listData, this);
		listView.setAdapter(adapter);
	}
}
