package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.AppInfo;

/**
 * 用来展示软件列表
 * 
 * @author lenovo
 * 
 */
public class SoftManagerShowAdapter extends BaseAdapter {
	/**
	 * AppInfo泛型的集合
	 */
	ArrayList<AppInfo> mList;
	/**
	 * Context
	 */
	Context mContext;
	/**
	 * 渲染器
	 */
	LayoutInflater inflater;

	/**
	 * 构造方法
	 * 
	 * @param mList
	 *            集合泛型为AppInfo
	 * @param mContext
	 *            Context
	 */
	public SoftManagerShowAdapter(ArrayList<AppInfo> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;

		// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		MyHolder holder;

		if (convertView == null) {

			convertView = inflater.inflate(
					R.layout.item_soft_manager_show_list, null);
			holder = new MyHolder();

			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.ch_soft_manager_show_item_choose);
			holder.imgIcon = (ImageView) convertView
					.findViewById(R.id.iv_soft_manager_show_item_icon);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tv_soft_manager_show_item_name);
			holder.tvPackageName = (TextView) convertView
					.findViewById(R.id.tv_soft_manager_show_item_package_name);
			holder.tvVersionName = (TextView) convertView
					.findViewById(R.id.tv_soft_manager_show_item_versionname);

			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		AppInfo appInfo = mList.get(position);

		holder.checkBox.setChecked(appInfo.isChecked);
		holder.imgIcon.setImageDrawable(appInfo.getIcon());
		holder.tvName.setText(appInfo.getLable());
		holder.tvPackageName.setText(appInfo.getPackageName());
		holder.tvVersionName.setText(appInfo.getVersionName());

		return convertView;
	}

	/**
	 * 
	 * @author lenovo
	 * 
	 */
	public class MyHolder {
		public CheckBox checkBox;
		ImageView imgIcon;
		TextView tvName;
		TextView tvPackageName;
		TextView tvVersionName;
	}
}
