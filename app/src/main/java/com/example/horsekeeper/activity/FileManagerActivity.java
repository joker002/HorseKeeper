package com.example.horsekeeper.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.FileInfo;
import com.example.horsekeeper.util.FileMemoryInfoUtil;
import com.example.horsekeeper.util.FileUtils;

/**
 * �ļ�������
 * 
 * @author lenovo
 * 
 */
// Handler����
// ��ȡ�ֻ����ļ���Ϣ
// Intent�������� �з�Χ1024kb ������Χ������
public class FileManagerActivity extends BaseActivity implements
		OnClickListener {

	TextView mTvmAllTittle;
	TextView mTvmALL;
	TextView mTvmTXT;
	TextView mTvmVEDIO;
	TextView mTvmAUDIO;
	TextView mTvmPIC;
	TextView mTvmZIP;
	TextView mTvmAPK;

	// ���غͼ�ͷ
	ImageView mImageView;
	ProgressBar mProgressBar;
	ImageView mImageView1;
	ProgressBar mProgressBar1;
	ImageView mImageView2;
	ProgressBar mProgressBar2;
	ImageView mImageView3;
	ProgressBar mProgressBar3;
	ImageView mImageView4;
	ProgressBar mProgressBar4;
	ImageView mImageView5;
	ProgressBar mProgressBar5;
	ImageView mImageView6;
	ProgressBar mProgressBar6;

	LinearLayout llAll;
	LinearLayout llTxt;
	LinearLayout llVedio;
	LinearLayout llAudio;
	LinearLayout llPic;
	LinearLayout llZip;
	LinearLayout llApk;
	ArrayList<FileInfo> info;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			info = FileMemoryInfoUtil.list;
			// LogWapper.e("aaa", msg + "");
			switch (msg.what) {
			case 1:
				setText1(info);
				break;
			case 2:
				setText(info);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manager);
		// LogWapper.e("aaa", "ccc");
	}

	@Override
	void initView() {
		mImgLeft.setImageResource(R.drawable.btn_homeasup_default);
		mTvTittle.setText("�ļ�����");

		// ���ؽ������ָ��ͼ��
		mImageView = (ImageView) findViewById(R.id.iv_file_all);
		mProgressBar = (ProgressBar) findViewById(R.id.pb_file_all);
		mImageView1 = (ImageView) findViewById(R.id.iv_file_apk);
		mProgressBar1 = (ProgressBar) findViewById(R.id.pb_file_apk);
		mImageView2 = (ImageView) findViewById(R.id.iv_file_audio);
		mProgressBar2 = (ProgressBar) findViewById(R.id.pb_file_audio);
		mImageView3 = (ImageView) findViewById(R.id.iv_file_pic);
		mProgressBar3 = (ProgressBar) findViewById(R.id.pb_file_pic);
		mImageView4 = (ImageView) findViewById(R.id.iv_file_txt);
		mProgressBar4 = (ProgressBar) findViewById(R.id.pb_file_txt);
		mImageView5 = (ImageView) findViewById(R.id.iv_file_vedio);
		mProgressBar5 = (ProgressBar) findViewById(R.id.pb_file_vedio);
		mImageView6 = (ImageView) findViewById(R.id.iv_file_zip);
		mProgressBar6 = (ProgressBar) findViewById(R.id.pb_file_zip);

		mTvmAllTittle = (TextView) findViewById(R.id.ll_file_all_home);
		mTvmALL = (TextView) findViewById(R.id.ll_file_all);
		mTvmTXT = (TextView) findViewById(R.id.ll_file_txt);
		mTvmVEDIO = (TextView) findViewById(R.id.ll_file_vedio);
		mTvmAUDIO = (TextView) findViewById(R.id.ll_file_audio);
		mTvmPIC = (TextView) findViewById(R.id.ll_file_pic);
		mTvmZIP = (TextView) findViewById(R.id.ll_file_zip);
		mTvmAPK = (TextView) findViewById(R.id.ll_file_apk);

		// LinearLayout�ؼ�
		llAll = (LinearLayout) findViewById(R.id.ll_file_manager_all);
		llTxt = (LinearLayout) findViewById(R.id.ll_file_manager_txt);
		llVedio = (LinearLayout) findViewById(R.id.ll_file_manager_vedio);
		llAudio = (LinearLayout) findViewById(R.id.ll_file_manager_audio);
		llPic = (LinearLayout) findViewById(R.id.ll_file_manager_pic);
		llZip = (LinearLayout) findViewById(R.id.ll_file_manager_zip);
		llApk = (LinearLayout) findViewById(R.id.ll_file_manager_apk);

		FileMemoryInfoUtil.getFileMemory(handler);

		setOnClickListers(this, llAll, llTxt, llVedio, llAudio, llPic, llZip,
				llApk, mImgLeft);
		// llAll.setOnClickListener(this);
		// llTxt.setOnClickListener(this);
		// llVedio.setOnClickListener(this);
		// llAudio.setOnClickListener(this);
		// llPic.setOnClickListener(this);
		// llZip.setOnClickListener(this);
		// llApk.setOnClickListener(this);
	}

	public void setText(ArrayList<FileInfo> info) {
		mTvmAllTittle
				.setText(FileUtils.formatLength(info.get(0).getFileSize()));
		mTvmALL.setText(FileUtils.formatLength(info.get(0).getFileSize()));
		mTvmTXT.setText(FileUtils.formatLength(info.get(1).getFileSize()));
		mTvmAUDIO.setText(FileUtils.formatLength(info.get(2).getFileSize()));
		mTvmVEDIO.setText(FileUtils.formatLength(info.get(3).getFileSize()));
		mTvmPIC.setText(FileUtils.formatLength(info.get(4).getFileSize()));
		mTvmZIP.setText(FileUtils.formatLength(info.get(5).getFileSize()));
		mTvmAPK.setText(FileUtils.formatLength(info.get(6).getFileSize()));

	}

	public void setText1(ArrayList<FileInfo> info) {

		mImageView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);

		mImageView1.setVisibility(View.VISIBLE);
		mProgressBar1.setVisibility(View.GONE);

		mImageView2.setVisibility(View.VISIBLE);
		mProgressBar2.setVisibility(View.GONE);

		mImageView3.setVisibility(View.VISIBLE);
		mProgressBar3.setVisibility(View.GONE);

		mImageView4.setVisibility(View.VISIBLE);
		mProgressBar4.setVisibility(View.GONE);

		mImageView5.setVisibility(View.VISIBLE);
		mProgressBar5.setVisibility(View.GONE);

		mImageView6.setVisibility(View.VISIBLE);
		mProgressBar6.setVisibility(View.GONE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// LogWapper.e("aaa", "aaa" + FileMemoryInfoUtil.list);
		FileMemoryInfoUtil.list.clear();
		// LogWapper.e("aaa", "bbb" + FileMemoryInfoUtil.list);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, FileManagerShowActivity.class);
		switch (v.getId()) {
		case R.id.ll_file_manager_all:// ����ֵ̫�� ���ܻ����
			FileInfo fileInfoAll = info.get(0);
			// Intent ���ݶ��� �ö������ʵ��Serializable(���л�)�ӿ�
			// putExtra(String name,Serializable value)
			/*
			 * 1ѹ������2������ �������� ��ֵ
			 */
			intent.putExtra("data", fileInfoAll);
			break;
		case R.id.ll_file_manager_txt:
			FileInfo fileInfoTxt = info.get(1);
			intent.putExtra("data", fileInfoTxt);
			break;
		case R.id.ll_file_manager_audio:
			FileInfo fileInfoAudio = info.get(2);
			intent.putExtra("data", fileInfoAudio);
			break;
		case R.id.ll_file_manager_vedio:
			FileInfo fileInfoVedio = info.get(3);
			intent.putExtra("data", fileInfoVedio);
			break;
		case R.id.ll_file_manager_pic:
			FileInfo fileInfoPic = info.get(4);
			intent.putExtra("data", fileInfoPic);
			break;
		case R.id.ll_file_manager_zip:
			FileInfo fileInfoZip = info.get(5);
			intent.putExtra("data", fileInfoZip);
			break;
		case R.id.ll_file_manager_apk:
			FileInfo fileInfoApk = info.get(6);
			intent.putExtra("data", fileInfoApk);
			break;
		default:
			Intent intent1 = new Intent(this, HomeActivity.class);
			startActivity(intent1);
			return;
		}
		startActivity(intent);
	}
	// @Override
	// protected void onStop() {
	//
	// LogWapper.e("aaa", "aaa" + FileMemoryInfoUtil.list);
	// FileMemoryInfoUtil.list.clear();
	// LogWapper.e("aaa", "bbb" + FileMemoryInfoUtil.list);

	// FileMemoryInfoUtil.list.get(0).setFileNumber(0);
	// FileMemoryInfoUtil.list.get(0).setFileSize(0);
	// FileMemoryInfoUtil.list.get(0).setFileTyle(0);
	// }
}
