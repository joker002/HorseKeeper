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
 * 通讯录主界面 初始化数据 加载适配器
 * 
 * @author lenovo
 * 
 */
// 用Intent传值 Bundle(存储)
// 文件的拷贝和查找
// GridView网格布局
// OnItemClickListener条目监听事件
// AssetManager 即管理资产类,这个类为访问当前应用程序的资产文件提供了入口
public class TelManagerActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * 定义一个集合 用来放String类型的名称
	 */
	ArrayList<String> arrDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这是BaseActivity的方法 会调用initView
		setContentView(R.layout.activity_tel_manager);
	}

	/**
	 * 初始化数据
	 */
	public void initDate() {
		try {
			// 工具类 拷贝方法
			TELDBUtil.copeDB(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 工具类 拿到数据库数据
		arrDate = TELDBUtil.selectClasslist();
	}

	/**
	 * 在父类setContentView里会自动调用
	 */
	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("通讯大全");
		// mImgRight.setVisibility(View.INVISIBLE);

		// 初始化数据
		initDate();

		// 找到GridView控件 需要适配器
		GridView gridView = (GridView) findViewById(R.id.gv_tel_manager);

		// 初始化通讯录的主界面适配器
		// getLayoutInflater()方法拿到渲染器
		TelManagerGridAdapter adapter = new TelManagerGridAdapter(arrDate,
				getLayoutInflater());

		// 将控件加载到适配器中 控件名.setAdapter(适配器)
		gridView.setAdapter(adapter);
		// 有很多框,会有很多点击
		// setOnItemClickListener条目点击事件
		gridView.setOnItemClickListener(this);

		setOnClickListers(this, mImgLeft);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(this, TelManagerShowActivity.class);

		// 跳转的时候传值 数据先放在Bundle里面

		// Bundle bun = new Bundle();
		// bun.putInt(Constants.PUT_POSITION, position);
		// intent.putExtras(bun);

		// Constants.PUT_POSITION 自己定义的常量(键值对)

		intent.putExtra(Constants.PUT_POSITION, position);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
}
