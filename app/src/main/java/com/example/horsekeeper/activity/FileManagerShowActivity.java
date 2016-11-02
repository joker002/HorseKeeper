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
 * �ļ��������������
 * 
 * @author lenovo
 * 
 */
// ��Ŀ��� Intent������ͼ
// ɾ��
public class FileManagerShowActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	/**
	 * �ļ�����
	 */
	TextView mFileCount;
	/**
	 * �ļ���С
	 */
	TextView mFileSize;
	/**
	 * ListView�ļ�����
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
		mTvTittle.setText("�ļ�����");

		// if ("data".equals("fileInfoAll")) {
		// mTvTittle.setText("ȫ��");
		// }

		mListView = (ListView) findViewById(R.id.lv_file_manager_show);

		mFileCount = (TextView) findViewById(R.id.tv_file_manager_show_count);
		mFileSize = (TextView) findViewById(R.id.tv_file_manager_show_size);
		mBtnShowDelete = (Button) findViewById(R.id.btn_file_manager_show_delete);

		Intent intent = getIntent();
		// ����(����)FileManagerActivity����������

		// ����
		fileInfo = (FileInfo) intent.getSerializableExtra("data");

		mFileCount.setText(fileInfo.getFileNumber() + "");
		mFileSize.setText(FileUtils.formatLength(fileInfo.getFileSize()));

		// ������
		adapter = new FileManagerShowAdapter(fileInfo.fileDetail, this);

		mListView.setAdapter(adapter);

		// ����Ŀ�ĵ���¼�
		mListView.setOnItemClickListener(this);

		// ��ť
		mBtnShowDelete.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// LogWapper.e("aaa", "ccc");
		// �õ�ʵ��
		FileDetailInfo info = (FileDetailInfo) adapter.getItem(position);
		File file = info.file;

		// ������ͼ ֪ͨϵͳȥ�򿪴��ļ� ����Ҫ��ϵͳ�����ļ�·��
		// ��Ҫ�����ļ���ʽMIME
		Intent intent = new Intent(Intent.ACTION_VIEW);

		// Uri.fromFile(file)
		intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "*/*");
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_file_manager_show_delete:
			// ��������ʵ��
			// ConcurrentModificationException
			// ��������⵽����Ĳ����޸ģ��������������޸�ʱ���׳����쳣
			// ���ܵ��� ����forѭ��
			for (FileDetailInfo info : fileInfo.fileDetail) {
				if (info.isChecked()) {
					info.file.delete();
					// �Ƴ������Ŀ
					fileInfo.fileDetail.remove(info);
					break;
				}
			}
			// LogWapper.e("aaa", "aaa");
			// ˢ���б�
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}
}
