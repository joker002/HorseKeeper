package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.Speedinfo;
import com.example.horsekeeper.util.FileUtils;
import com.example.horsekeeper.util.ProcessType;

/**
 * 手机加速适配器
 * 
 * @author lenovo
 * 
 */
public class RocketManagerShowAdapter extends BaseAdapter {

	Context mContect;
	ArrayList<Speedinfo> mList;
	LayoutInflater mFlater;

	public RocketManagerShowAdapter(ArrayList<Speedinfo> mList, Context mContect) {
		this.mList = mList;
		this.mContect = mContect;
		mFlater = (LayoutInflater) mContect
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
			holder = new MyHolder();

			convertView = mFlater.inflate(R.layout.item_rocket_manager, null);

			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.cb_rocket_manager);

			holder.mIvIcon = (ImageView) convertView
					.findViewById(R.id.iv_rocket_manager);
			holder.mTvFileName = (TextView) convertView
					.findViewById(R.id.tv_rocket_manager_name);
			holder.mTvMemory = (TextView) convertView
					.findViewById(R.id.tv_rocket_manager_size);
			holder.mTvIsSystem = (TextView) convertView
					.findViewById(R.id.tv_rocket_manager_system);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}

		Speedinfo speed = mList.get(position);

		Log.e("aaa", "holder=" + holder + "holder.checkBox=" + holder.checkBox
				+ "speed=" + speed + "speed.isCheck" + speed.isCheck);

		// 09-07 15:46:29.074: E/aaa(4029):
		// holder=com.example.horsekeeper.adapter.RocketManagerShowAdapter$MyHolder@454c55d0

		// holder.checkBox=android.widget.CheckBox{45489118VFED..C. ......ID
		// 0,0-0,0 #7f070048app:id/cb_rocket_manager}

		// speed.isCheck=null

		holder.checkBox.setChecked(speed.isCheck);

		holder.mIvIcon.setImageDrawable(speed.icon);
		holder.mTvFileName.setText(speed.lable);
		holder.mTvMemory.setText(FileUtils.formatLength(speed.processMem));
		holder.mTvIsSystem
				.setVisibility(speed.type == ProcessType.USER ? View.INVISIBLE
						: View.VISIBLE);

		return convertView;
	}

	class MyHolder {
		CheckBox checkBox;
		ImageView mIvIcon;
		TextView mTvFileName;
		TextView mTvMemory;
		TextView mTvIsSystem;
	}

	public void setData(ArrayList<Speedinfo> userInfo) {
		this.mList = userInfo;
	}
}
