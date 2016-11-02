package com.example.horsekeeper.activity;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.adapter.FileManagerShowAdapter;
import com.example.horsekeeper.entity.FileDetailInfo;
import com.example.horsekeeper.entity.FileInfo;
import com.example.horsekeeper.util.FileMemoryInfoUtil;
import com.example.horsekeeper.util.FileUtils;

/**
 * 文件管理类二级界面
 * 
 * @author lenovo
 * 
 */
// 条目点击 Intent隐视意图
// 删除
public class FileManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * 文件数量
	 */
	TextView mFileCount;
	/**
	 * 文件大小
	 */
	TextView mFileSize;
	/**
	 * ListView文件属性
	 */
	ListView mListView;
	FileInfo fileInfo;
	Button mBtnShowDelete;
	Handler handler = new Handler();
	FileManagerShowAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FileMemoryInfoUtil.getFileMemory(handler);
		setContentView(R.layout.activity_file_manager_show);
	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("文件管理");

		// if ("data".equals("fileInfoAll")) {
		// mTvTittle.setText("全部");
		// }

		mListView = (ListView) findViewById(R.id.lv_file_manager_show);

		mFileCount = (TextView) findViewById(R.id.tv_file_manager_show_count);
		mFileSize = (TextView) findViewById(R.id.tv_file_manager_show_size);
		mBtnShowDelete = (Button) findViewById(R.id.btn_file_manager_show_delete);

		Intent intent = getIntent();
		// 接收(对象)FileManagerActivity传来的数据

		// 数据
		fileInfo = (FileInfo) intent.getSerializableExtra("data");

		mFileCount.setText(fileInfo.getFileNumber() + "");
		mFileSize.setText(FileUtils.formatLength(fileInfo.getFileSize()));

		// 适配器
		adapter = new FileManagerShowAdapter(fileInfo.fileDetail, this);

		mListView.setAdapter(adapter);

		// 子条目的点击事件
		mListView.setOnItemClickListener(this);

		// 按钮
		mBtnShowDelete.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// LogWapper.e("aaa", "ccc");
		// 拿到实体
		FileDetailInfo info = (FileDetailInfo) adapter.getItem(position);
		File file = info.file;

		// 隐视意图 通知系统去打开此文件 并且要给系统传递文件路径
		// 需要传递文件格式MIME
		Intent intent = new Intent(Intent.ACTION_VIEW);

		// Uri.fromFile(file)
		intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "*/*");
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_file_manager_show_delete:
			// 遍历所有实体
			// ConcurrentModificationException
			// 当方法检测到对象的并发修改，但不允许这种修改时，抛出此异常
			// 不能迭代 可以for循环
			for (FileDetailInfo info : fileInfo.fileDetail) {
				if (info.isChecked()) {
					info.file.delete();
					// 移除这个条目
					fileInfo.fileDetail.remove(info);
					break;
				}
			}
			// LogWapper.e("aaa", "aaa");
			// 刷新列表
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}
}
