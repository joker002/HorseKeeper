package com.example.horsekeeper.util;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.os.Handler;

import com.example.horsekeeper.entity.FileDetailInfo;
import com.example.horsekeeper.entity.FileInfo;

/**
 * 获取手机中文件的信息
 * 
 * @author lenovo
 * 
 */
// Handler
public class FileMemoryInfoUtil {

	public static final int FILE_ALL = 200;
	public static final int FILE_TXT = 201;
	public static final int FILE_VEDIO = 202;
	public static final int FILE_AUDIO = 203;
	public static final int FILE_PIC = 204;
	public static final int FILE_ZIP = 205;
	public static final int FILE_APK = 206;

	public static ArrayList<FileInfo> list = new ArrayList<FileInfo>();

	static void a() {
		FileInfo infoAll = new FileInfo();
		infoAll.setFileTyle(FILE_ALL);
		list.add(infoAll);

		FileInfo infoTxt = new FileInfo();
		infoAll.setFileTyle(FILE_TXT);
		list.add(infoTxt);

		FileInfo infoVedio = new FileInfo();
		infoAll.setFileTyle(FILE_VEDIO);
		list.add(infoVedio);

		FileInfo infoAudio = new FileInfo();
		infoAll.setFileTyle(FILE_AUDIO);
		list.add(infoAudio);

		FileInfo infoPic = new FileInfo();
		infoAll.setFileTyle(FILE_PIC);
		list.add(infoPic);

		FileInfo infoZip = new FileInfo();
		infoAll.setFileTyle(FILE_ZIP);
		list.add(infoZip);

		FileInfo infoApk = new FileInfo();
		infoAll.setFileTyle(FILE_APK);
		list.add(infoApk);

	}

	static Handler mHandler;

	/**
	 * 获取文件的大小(分类)
	 */
	public static void getFileMemory(final Handler handler) {
		a();
		mHandler = handler;

		// 文件位置
		final File file = Environment.getExternalStorageDirectory();

		new Thread() {
			@Override
			public void run() {
				searchFile(file);

				handler.sendEmptyMessage(1);

			};
		}.start();
	}

	/**
	 * 搜索文件
	 */
	public static void searchFile(File file) {

		// isDirectory是否是文件夹
		if (file.isDirectory() && file.canRead()) {
			File[] file1 = file.listFiles();

			for (File file2 : file1) {
				searchFile(file2);
			}
		} else {
			getFileInfo(file);
		}
	}

	/**
	 * 获取文件信息
	 */
	private static void getFileInfo(File file) {

		long fileLength = file.length();// 大小
		String fileName = file.getName();// 文件名
		String[] strs = fileName.split("\\.");// 截取

		if (strs.length >= 2) {
			// 文件后缀
			String fileSuffix = strs[strs.length - 1];

			list.get(0).fileSize += fileLength;

			// list.get(0).setFileSize(list.get(0).getFileSize() + length);

			list.get(0).setFileNumber(list.get(0).getFileNumber() + 1);

			// 装详细的信息
			FileDetailInfo fileDetail = new FileDetailInfo(fileName,
					file.lastModified(), fileLength, file);

			// LogWapper.e("aaa", "ddd" + fileDetail);

			list.get(0).fileDetail.add(fileDetail);

			if (FileUtils.isTextFile(fileSuffix)) {
				fileDetail.fileType = FILE_ALL;
				list.get(1).fileDetail.add(fileDetail);
				list.get(1).fileSize += fileLength;
				list.get(1).setFileNumber(list.get(1).getFileNumber() + 1);
			} else if (FileUtils.isVideoFile(fileSuffix)) {
				fileDetail.fileType = FILE_VEDIO;
				list.get(2).fileDetail.add(fileDetail);
				list.get(2).fileSize += fileLength;
				list.get(2).setFileNumber(list.get(2).getFileNumber() + 1);
			} else if (FileUtils.isAudioFile(fileSuffix)) {
				fileDetail.fileType = FILE_AUDIO;
				list.get(3).fileDetail.add(fileDetail);
				list.get(3).fileSize += fileLength;
				list.get(3).setFileNumber(list.get(3).getFileNumber() + 1);
			} else if (FileUtils.isImageFile(fileSuffix)) {
				fileDetail.fileType = FILE_PIC;
				list.get(4).fileDetail.add(fileDetail);
				list.get(4).fileSize += fileLength;
				list.get(4).setFileNumber(list.get(4).getFileNumber() + 1);
			} else if (FileUtils.isZipFile(fileSuffix)) {
				fileDetail.fileType = FILE_ZIP;
				list.get(5).fileDetail.add(fileDetail);
				list.get(5).fileSize += fileLength;
				list.get(5).setFileNumber(list.get(5).getFileNumber() + 1);
			} else if (FileUtils.isProgramFile(fileSuffix)) {
				fileDetail.fileType = FILE_APK;
				list.get(6).fileDetail.add(fileDetail);
				list.get(6).fileSize += fileLength;
				list.get(6).setFileNumber(list.get(6).getFileNumber() + 1);
			}
		} else {
		}

		mHandler.sendEmptyMessage(2);

	}
}
