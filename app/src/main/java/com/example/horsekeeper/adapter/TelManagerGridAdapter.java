package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horsekeeper.R;

/**
 * ͨѶ¼�������������� �ı������
 * 
 * @author lenovo
 * 
 */
public class TelManagerGridAdapter extends BaseAdapter {
	/**
	 * ���� ����new���� Ϊ�˲�ռ�ڴ�ռ�
	 */
	// ArrayList<String> mArrData = new ArrayList<String>();
	ArrayList<String> mArrData;

	/**
	 * ͼƬid������
	 */
	int[] mPicture = {
//			R.drawable.ic_launcher,
//			R.drawable.ic_launcher,
//			R.drawable.ic_launcher,
			R.mipmap.green
//			R.mipmap.notification_information_progress_red_9,
//			R.mipmap.notification_information_progress_yellow_9

	};

	/**
	 * ���������
	 */
	LayoutInflater mInflater;

	/**
	 * ���췽���������������Ⱦ��
	 * 
	 * @param mArrData
	 * @param inflater
	 */
	public TelManagerGridAdapter(ArrayList<String> mArrData,
			LayoutInflater inflater) {
		this.mArrData = mArrData;
		this.mInflater = inflater;
	}

	/**
	 * ���ؼ��ϵĴ�С
	 */
	@Override
	public int getCount() {
		return mArrData == null ? 0 : mArrData.size();
	}

	/**
	 * Ϊ�˸��ⲿʹ��
	 */
	@Override
	public Object getItem(int position) {
		return mArrData.get(position);
	}

	/**
	 * Ϊ�˸��ⲿʹ��
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Ϊ�˸ı����
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// ����һ��MyHolder���͵�myHolder
		MyHolder myHolder;

		// ���ж�convertView�Ƿ�Ϊ�� ��convertViewΪ��ʱ ��Ҫ�ò��������
		if (convertView == null) {

			// inflate(��ӵ�convertView���Ӳ���)
			convertView = mInflater.inflate(R.layout.item_grid_tel_manager,
					null);

			// new MyHolder����
			myHolder = new MyHolder();

			// �ҵ�convertView���ӿؼ�
			myHolder.mTvInfo = (TextView) convertView
					.findViewById(R.id.tv_item_tel_manager_grid);

			// convertView��myHolder������ϵ
			convertView.setTag(myHolder);

		} else {

			// ��Ҫ���� ����ᱨ��ָ�� (ע��)
			// �õ�convertView���õ�myHolder
			myHolder = (MyHolder) convertView.getTag();
		}

		// ��Ⱦ
		// �޸�text��background(ע����Ҫ��idת����ͼƬ��setBackgroundResource����)
		myHolder.mTvInfo.setText(mArrData.get(position));
		// ����ͼƬ�ֻ�
		myHolder.mTvInfo.setBackgroundResource(mPicture[position
				% (mPicture.length)]);

		// ��Ҫ����convertView
		return convertView;
	}

	/**
	 * �ڲ���MyHolder
	 * 
	 * @author lenovo
	 * 
	 */
	class MyHolder {
		TextView mTvInfo;
	}
}
