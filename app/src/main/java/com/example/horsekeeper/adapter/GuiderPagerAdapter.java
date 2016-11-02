package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 引导页面ViewPager的适配器
 * 
 * @author lenovo
 * 
 */
public class GuiderPagerAdapter extends PagerAdapter {
	/**
	 * 自定义集合
	 */
	ArrayList<ImageView> listImg;

	/**
	 * 有参构造方法 传集合
	 * 
	 * @param listImg
	 */
	public GuiderPagerAdapter(ArrayList<ImageView> listImg) {
		this.listImg = listImg;
	}

	/**
	 * Pager的数量
	 */
	@Override
	public int getCount() {
		return listImg == null ? 0 : listImg.size();
		// return Integer.MAX_VALUE;
	}

	/**
	 * 暴露给外部使用 用来查看View和Object是否相等 自动调用
	 * 
	 * arg0 arg1 instantiateItem的返回值
	 * 
	 * 只有返回值是true时 才会展示出效果
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * 初始化子条目(图片) container 容器(可以添加view) position 下标 return 所要添加的视图
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 添加视图---->ViewPager

		// 先通过下标拿集合中的元素
		ImageView imgView = listImg.get(position);
		// 控制图片的长宽
		// imgView.setScaleType(ScaleType.FIT_XY);

		// 添加视图 addView
		container.addView(imgView);

		// 返回所要添加的视图
		return imgView;
	}

	/**
	 * 销毁子条目 container 容器 position 销毁的下标 object instantiateItem的返回值
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((View) object);
	}
}
