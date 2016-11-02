package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.TelInfo;

/**
 * 通讯录二级界面 ListView适配器
 * 
 * @author lenovo
 * 
 */
public class TelManagerShowListAdapter extends BaseAdapter {

	// ？
	// Context mContext;

	/**
	 * 定义一个集合泛型为name和number
	 */
	ArrayList<TelInfo> mArr;

	/**
	 * 定义一个适配器
	 */
	LayoutInflater mInflater;

	// 通过构造方法传值
	public TelManagerShowListAdapter(Context context, ArrayList<TelInfo> arr) {

		// mContext = context;

		mArr = arr;

		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mArr == null ? 0 : mArr.size();
	}

	/**
	 * 通过下标拿到集合的数据
	 */
	@Override
	public Object getItem(int position) {
		return mArr.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MyHolder holder;
		if (convertView == null) {

			holder = new MyHolder();

			// 加载布局
			convertView = mInflater.inflate(
					R.layout.item_list_tel_manager_show, null);

			// 找到子控件
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_name_tel_manager_show_list);
			holder.number = (TextView) convertView
					.findViewById(R.id.tv_number_tel_manager_show_list);

			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		holder.name.setText(mArr.get(position).getName());
		holder.number.setText(mArr.get(position).getNumber());
		return convertView;
	}

	class MyHolder {
		TextView name;
		TextView number;
	}

}
