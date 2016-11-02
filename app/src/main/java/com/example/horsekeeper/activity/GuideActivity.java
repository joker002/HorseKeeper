package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.GuiderPagerAdapter;

/**
 * 引导界面
 * 
 * @author cc
 * 
 */
// 用到了SharedPreferences存储方法(用来存储一些小数据)
// 用到了ViewPager 左右切换当前View 需要适配器 PagerAdapter
public class GuideActivity extends BaseActivity implements
		OnPageChangeListener, OnClickListener {
	private static final String PREFRENCE_NAME = null;
	/**
	 * 写有图片Id的数组
	 */
	public int[] mPicture = { R.drawable.adware_style_applist,
			R.drawable.adware_style_banner, R.drawable.adware_style_creditswall };
	/**
	 * ImageView类型的数组
	 */
	ImageView[] mImg = new ImageView[3];
	/**
	 * ViewPager
	 */
	ViewPager pager;
	/**
	 * 第一个小圆点
	 */
	ImageView mImFirstPoint;
	/**
	 * 第二个小圆点
	 */
	ImageView mImSecondPoint;
	/**
	 * 第三个小圆点
	 */
	ImageView mImThirdPoint;
	/**
	 * 直接跳过按钮
	 */
	Button mBtnPass;
	/**
	 * 存储小数据
	 */
	SharedPreferences preference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 1.找到控件
		// 2.数据源
		// 3.拿到适配器
		// 4.控件

		// setContentView(R.layout.ativity_guide);
		// init();

		// 调用的是BaseActivity
		setContentView(R.layout.ativity_guide);

		// 判断 第一进入执行if语句后面的程序,不是第一次进入进入if语句内 实现跳转
		if (!getData()) {

			Intent intent = new Intent(GuideActivity.this,
					AnimationActivity.class);

			startActivity(intent);

			finish();
		}
	}

	/**
	 * 操作SharedPreferences内的数据 读数据
	 * 
	 * @return
	 */
	private boolean getData() {
		// 读文件(小数据)

		// 得到SharedPreferences对象
		// MODE_PRIVATE(只有本应用可以访问) 0也可以代表 还有三个值
		preference = getSharedPreferences(PREFRENCE_NAME, MODE_PRIVATE);

		// 第二个是默认值 如果key写错了改变为默认值
		boolean preferen = preference.getBoolean("first", true);

		return preferen;
	}

	/**
	 * 初始化控件 以及绑定事件
	 */
	@Override
	void initView() {

		// 找到控件id
		pager = (ViewPager) findViewById(R.id.vp_guide);
		mImFirstPoint = (ImageView) findViewById(R.id.Im_guide_first);
		mImSecondPoint = (ImageView) findViewById(R.id.Im_guide_second);
		mImThirdPoint = (ImageView) findViewById(R.id.Im_guide_third);
		mBtnPass = (Button) findViewById(R.id.btn_guide_pass);

		// 往数组中装数据
		mImg[0] = mImFirstPoint;
		mImg[1] = mImSecondPoint;
		mImg[2] = mImThirdPoint;

		// 拿到数据源
		/**
		 * 泛型为ImageView的集合
		 */
		ArrayList<ImageView> listImg = new ArrayList<ImageView>();
		for (int i = 0; i < mPicture.length; i++) {

			// 创建 imageView 基本属性为默认值
			ImageView img = new ImageView(this);

			// 通过数组下标拿到数组各个图片id
			// 用setImageResource将图片id变成图片
			img.setImageResource(mPicture[i]);

			// 设置图片位置
			img.setScaleType(ScaleType.FIT_END);
			// 将图片添加到集合中
			listImg.add(img);

			// 拿到适配器
			GuiderPagerAdapter adapter = new GuiderPagerAdapter(listImg);
			/**
			 * 启动适配器
			 */
			pager.setAdapter(adapter);
			/**
			 * 监听滑动OnPageChangeListener
			 */
			pager.setOnPageChangeListener(this);
			/**
			 * 监听按钮
			 */
			mBtnPass.setOnClickListener(this);
		}

	}

	/**
	 * 页面滚动状态的改变
	 * 
	 * 1:滑动 2:弹性运动 0:停止
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * arg0 所操作页面的下标
	 * 
	 * arg1 所滑动的偏移百分比
	 * 
	 * arg2 所滑动的偏移像素
	 * 
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/**
	 * 所选界面
	 */
	@Override
	public void onPageSelected(int arg0) {
		/**
		 * 当改变界面时 动点图片改变 Button按钮隐藏(隐藏和只有框时不能点击)或出现
		 */
		mBtnPass.setVisibility(View.GONE);
		for (ImageView img : mImg) {
			img.setImageResource(R.drawable.adware_style_default);
		}
		mImg[arg0].setImageResource(R.drawable.adware_style_selected);
		if (arg0 == 2) {
			mBtnPass.setVisibility(View.VISIBLE);
		}

		// if (arg0 == 0) {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_selected);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_default);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_default);
		// mBtnPass.setVisibility(View.GONE);
		// } else if (arg0 == 1) {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_default);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_selected);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_default);
		// mBtnPass.setVisibility(View.GONE);
		// } else {
		// mImFirstPoint.setImageResource(R.drawable.adware_style_default);
		// mImSecondPoint.setImageResource(R.drawable.adware_style_default);
		// mImThirdPoint.setImageResource(R.drawable.adware_style_selected);
		// mBtnPass.setVisibility(View.VISIBLE);
		// }
	}

	@Override
	public void onClick(View v) {

		// 先调方法获取Editor的对象
		Editor edit = preference.edit();
		// 写
		edit.putBoolean("first", false);
		// 提交
		edit.commit();

		/**
		 * 跳转到另一个Activity
		 */
		Intent intent = new Intent(GuideActivity.this, AnimationActivity.class);

		startActivity(intent);

		finish();
	}

}
