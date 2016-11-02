package com.example.horsekeeper.adapter;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.FileDetailInfo;
import com.example.horsekeeper.util.FileMemoryInfoUtil;
import com.example.horsekeeper.util.FileUtils;

/**
 * 文件管理二级界面适配器
 * 
 * @author lenovo
 * 
 */
public class FileManagerShowAdapter extends BaseAdapter {
	ArrayList<FileDetailInfo> mInfo;
	Context mContext;
	LayoutInflater inflater;

	public FileManagerShowAdapter(ArrayList<FileDetailInfo> mInfo,
			Context mContext) {
		this.mContext = mContext;
		this.mInfo = mInfo;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mInfo == null ? 0 : mInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_file_manager_show,
					null);
			holder = new MyHolder();
			holder.mSeacher = (CheckBox) convertView
					.findViewById(R.id.cb_file_show);
			holder.mIcon = (ImageView) convertView
					.findViewById(R.id.iv_file_show_icon);
			holder.mFileName = (TextView) convertView
					.findViewById(R.id.tv_file_show_name);
			holder.mFileTime = (TextView) convertView
					.findViewById(R.id.tv_file_show_time);
			holder.mFileSize = (TextView) convertView
					.findViewById(R.id.tv_file_show_size);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		// 渲染
		final FileDetailInfo info = mInfo.get(position);

		holder.mSeacher.setChecked(info.isChecked());

		holder.mSeacher
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// 拿到监听 buttonView(代表我们所选的CheckBox)
						// isChecked (当前此CheckBox的选中状态)

						// 在此改变实体中的内容 在删除的时候
						info.setChecked(isChecked);
					}
				});

		holder.mFileName.setText(info.getFileName());

		holder.mFileSize.setText(FileUtils.formatLength(info.getFileSize()));

		DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(info.getFileTime());
		String s = d.format(d1);

		holder.mFileTime.setText(s + "");

		File file = info.file;
		if (info.fileType == FileMemoryInfoUtil.FILE_PIC) {
			// 加载图片的缩略图
			// try {
			// Bitmap bit = BitmapFactory.decodeStream(new FileInputStream(
			// file));
			// holder.mIcon.setImageBitmap(bit);
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// 第三方 jar包
			// prcoss(?)
			Glide.with(mContext).load(file).into(holder.mIcon);

		} else {
			// 其他根据文件类型 展示相应的图片
		}
		return convertView;
	}

	class MyHolder {
		CheckBox mSeacher;
		ImageView mIcon;
		TextView mFileName;
		TextView mFileTime;
		TextView mFileSize;
	}
}
