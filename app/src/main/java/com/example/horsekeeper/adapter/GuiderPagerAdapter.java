package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ����ҳ��ViewPager��������
 * 
 * @author lenovo
 * 
 */
public class GuiderPagerAdapter extends PagerAdapter {
	/**
	 * �Զ��弯��
	 */
	ArrayList<ImageView> listImg;

	/**
	 * �вι��췽�� ������
	 * 
	 * @param listImg
	 */
	public GuiderPagerAdapter(ArrayList<ImageView> listImg) {
		this.listImg = listImg;
	}

	/**
	 * Pager������
	 */
	@Override
	public int getCount() {
		return listImg == null ? 0 : listImg.size();
		// return Integer.MAX_VALUE;
	}

	/**
	 * ��¶���ⲿʹ�� �����鿴View��Object�Ƿ���� �Զ�����
	 * 
	 * arg0 arg1 instantiateItem�ķ���ֵ
	 * 
	 * ֻ�з���ֵ��trueʱ �Ż�չʾ��Ч��
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * ��ʼ������Ŀ(ͼƬ) container ����(�������view) position �±� return ��Ҫ��ӵ���ͼ
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// �����ͼ---->ViewPager

		// ��ͨ���±��ü����е�Ԫ��
		ImageView imgView = listImg.get(position);
		// ����ͼƬ�ĳ���
		// imgView.setScaleType(ScaleType.FIT_XY);

		// �����ͼ addView
		container.addView(imgView);

		// ������Ҫ��ӵ���ͼ
		return imgView;
	}

	/**
	 * ��������Ŀ container ���� position ���ٵ��±� object instantiateItem�ķ���ֵ
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((View) object);
	}
}
