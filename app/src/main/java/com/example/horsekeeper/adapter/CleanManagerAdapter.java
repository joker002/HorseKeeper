package com.example.horsekeeper.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.CleanChildInfo;
import com.example.horsekeeper.entity.CleanGroupInfo;
import com.example.horsekeeper.util.FileUtils;
import com.example.horsekeeper.util.LogWapper;

public class CleanManagerAdapter extends BaseExpandableListAdapter {

	/**
	 * 泛型为CleanGroupInfo的集合
	 */
	public ArrayList<CleanGroupInfo> mList;
	Context mContext;
	LayoutInflater mInflater;
	/**
	 * 泛型为View的集合
	 */
	ArrayList<View> mView = new ArrayList<View>();

	public CleanManagerAdapter(ArrayList<CleanGroupInfo> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// 可以不判空
		// return mList.get(groupPosition).content == null ? 0 : mList
		// .get(groupPosition).content.size();
		return mList.get(groupPosition).content.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mList.get(groupPosition).content.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHandler holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_clean_group, null);
			holder = new GroupHandler();

			holder.mTvFlag = (ImageView) convertView
					.findViewById(R.id.iv_clean_item_group);
			holder.mTvGroupName = (TextView) convertView
					.findViewById(R.id.tv_clean_item_group_name);
			holder.mTvGroupSize = (TextView) convertView
					.findViewById(R.id.tv_clean_item_group_size);
			holder.mProgressBar = (ProgressBar) convertView
					.findViewById(R.id.pgb_clean_item_group);
			convertView.setTag(holder);
		} else {
			holder = (GroupHandler) convertView.getTag();
		}

		CleanGroupInfo info = mList.get(groupPosition);

		holder.mTvFlag.setSelected(isExpanded);
		holder.mTvGroupName.setText(mList.get(groupPosition).groupName);
		holder.mTvGroupSize.setText(FileUtils.formatLength(info.groupSize));

		if (info.isLoading) {
			holder.mTvGroupSize.setVisibility(View.GONE);
			holder.mProgressBar.setVisibility(View.VISIBLE);
		} else {
			holder.mTvGroupSize.setVisibility(View.VISIBLE);
			holder.mProgressBar.setVisibility(View.GONE);
		}

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_clean_child, null);
			holder = new ChildHolder();

			holder.mCheck = (CheckBox) convertView
					.findViewById(R.id.cb_clean_child);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_clean_child_icon);
			holder.lable = (TextView) convertView
					.findViewById(R.id.tv_clean_child_name);
			holder.mTvChildSize = (TextView) convertView
					.findViewById(R.id.tv_clean_child_age);

			convertView.setTag(holder);

		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		CleanChildInfo cInfo = mList.get(groupPosition).content
				.get(childPosition);
		holder.mCheck.setChecked(cInfo.isChecked);
		holder.icon.setImageDrawable(cInfo.icon);
		holder.lable.setText(cInfo.lable);
		holder.mTvChildSize.setText(FileUtils.formatLength(cInfo.size));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}

	public class GroupHandler {
		ImageView mTvFlag;// 标记
		TextView mTvGroupName;
		TextView mTvGroupSize;
		ProgressBar mProgressBar;
	}

	public class ChildHolder {
		public CheckBox mCheck;
		ImageView icon;
		TextView lable;
		TextView mTvChildSize;
	}

	/**
	 * 
	 * @return ArrayList<View>
	 */
	public ArrayList<View> getAllChildView() {
		LogWapper.e("aaa", "" + mView);
		return mView;
	}
}
