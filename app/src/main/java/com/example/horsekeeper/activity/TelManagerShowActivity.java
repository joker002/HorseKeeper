package com.example.horsekeeper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.TelManagerShowListAdapter;
import com.example.horsekeeper.db.TELDBUtil;
import com.example.horsekeeper.entity.TelInfo;
import com.example.horsekeeper.util.Constants;

/**
 * 通讯大全二级界面
 * 
 * @author lenovo
 * 
 */
// Intent 跳转 隐视意图和显示意图
// 将一个View加载到另一个View下面
public class TelManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	/**
	 * 定义的ListView
	 */
	ListView listView;

	/**
	 * 定义的通讯录二级界面 ListView适配器
	 */
	TelManagerShowListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// 会有默认的宽高
		listView = new ListView(this);

		// 这是BaseActivity的方法

		setContentView(listView);

	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mImgRight.setVisibility(View.INVISIBLE);
		mTvTittle.setText("运营商");

		// 第一种传值 使用Bundle

		// Intent intent = getIntent();
		// Bundle bun = intent.getExtras();
		// int position = bun.getInt(Constants.PUT_POSITION);

		// 第二种传值(使用广泛,简单)
		// getIntent() activity的方法
		Intent intent = getIntent();

		// 拿到从TelManagerActivity传过来的值
		// 没有值 会自动赋值-1
		int position = intent.getIntExtra(Constants.PUT_POSITION, -1);
		// LogWapper.e("aaa", "-----" + position);

		// 需要return
		// finish需要onDestroy 会执行activity中的生命周期 执行后面的代码 应该加上return
		if (position == -1) {
			Toast.makeText(this, "参数异常", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// 加载适配器
		adapter = new TelManagerShowListAdapter(this,
				TELDBUtil.selectTable("table" + (position + 1)));

		listView.setAdapter(adapter);

		// listView的点击事件
		listView.setOnItemClickListener(this);
		setOnClickListers(this, mImgLeft);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// BaseAdapter的方法getItem
		TelInfo info = (TelInfo) adapter.getItem(position);

		// 拿到电话号码
		String number = info.getNumber();
		// 在电脑术语中，统一资源标识符（Uniform Resource Identifier，或URI)是一个用于标识某一互联网资源名称的字符串。

		// 该种标识允许用户对任何（包括本地和互联网）的资源通过特定的协议进行交互操作。URI由包括确定语法和相关协议的方案所定义。
		Uri uri = Uri.parse("tel:" + number);
		// 详情见Android_jump包
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, TelManagerActivity.class);
		startActivity(intent);
	}
}
