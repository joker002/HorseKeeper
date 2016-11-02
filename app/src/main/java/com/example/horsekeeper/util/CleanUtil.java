package com.example.horsekeeper.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;

import com.example.horsekeeper.R;
import com.example.horsekeeper.entity.CleanChildInfo;
import com.example.horsekeeper.entity.CleanGroupInfo;

/**
 * 垃圾清理数据
 * 
 * @author lenovo
 * 
 */
public class CleanUtil {
	/*
	 * 实体 GroupInfo ---> name size isLoading ArrayList<ChidInfo> content;
	 * 
	 * ChildInfo ---->isChecked icon lable size File
	 */
	private static ArrayList<CleanGroupInfo> mCleanGroupList;
	/**
	 * mCleanGroupList初始化完成
	 */
	public static final int INIT_FINISH = 200;
	/**
	 * 加载中
	 */
	public static final int LOADING = 201;
	/**
	 * 已完成
	 */
	public static final int FINISH = 202;

	public static String[] groupName = { "手机内存垃圾", "手机缓存垃圾", "sd内存垃圾",
			"sd缓存垃圾", "文件缓存垃圾" };

	static long size = 0;

	public static Handler mHandler;

	/**
	 * 获取数据
	 * 
	 * @param context
	 * @param handler
	 * @return
	 */
	public static ArrayList<CleanGroupInfo> getClearInfo(Context context,
			Handler handler) {
		mHandler = handler;
		init();
		// 数据初始化完成
		handler.sendEmptyMessage(INIT_FINISH);
		/*
		 * 1.拿到所有包名 2.拿到所有应用的Context 3.拿到对应文件
		 */
		PackageManager pm = context.getPackageManager();

		List<PackageInfo> lists = pm.getInstalledPackages(0);

		for (PackageInfo packageInfo : lists) {
			String packageName = packageInfo.packageName;
			Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
			String lable = packageInfo.applicationInfo.loadLabel(pm).toString();
			// 根据包名拿到包对应的Context
			Context pkgContext = getpackageContext(packageName, context);
			if (pkgContext == null) {
				continue;
			}
			// 手机内存
			File phoneFile = pkgContext.getFilesDir();
			// 手机内存读取大小
			long phoneFileSize = getSize(phoneFile);
			if (phoneFileSize > 0) {
				CleanChildInfo cInfo = new CleanChildInfo(icon, lable,
						phoneFileSize, phoneFile);
				mCleanGroupList.get(0).content.add(cInfo);
				mCleanGroupList.get(0).groupSize += phoneFileSize;
			}

			// 手机缓存垃圾
			File phoneChche = pkgContext.getCacheDir();
			// 手机缓存读取大小
			long phoneChcheSize = getSize(phoneChche);
			if (phoneChcheSize > 0) {
				CleanChildInfo cInfo = new CleanChildInfo(icon, lable,
						phoneChcheSize, phoneChche);
				mCleanGroupList.get(1).content.add(cInfo);
				mCleanGroupList.get(1).groupSize += phoneChcheSize;
			}

			// sd内存垃圾
			File sdFile = pkgContext.getExternalFilesDir(null);
			// Sd内存读取大小
			long sdFileSize = getSize(sdFile);
			if (sdFileSize > 0) {
				CleanChildInfo cInfo = new CleanChildInfo(icon, lable,
						sdFileSize, sdFile);
				mCleanGroupList.get(2).content.add(cInfo);
				mCleanGroupList.get(2).groupSize += sdFileSize;
			}

			// SD缓存垃圾
			File SDCache = pkgContext.getExternalCacheDir();
			// sd内存读取大小
			long SDCacheSize = getSize(SDCache);
			if (SDCacheSize > 0) {
				CleanChildInfo cInfo = new CleanChildInfo(icon, lable,
						SDCacheSize, SDCache);
				mCleanGroupList.get(3).content.add(cInfo);
				mCleanGroupList.get(3).groupSize += SDCacheSize;
			}
		}
		// 读取文件缓存垃圾
		File cacheFile = Environment.getDownloadCacheDirectory();
		listCacheFile(cacheFile, context);
		mHandler.sendEmptyMessage(FINISH);
		// mHandler.sendEmptyMessage(FINISH);

		// 加载完成
		// mOnFileloadingListener.onLoadingFinish();

		return mCleanGroupList;
	}

	// Handler handle = new Handler() {
	// @Override
	// public void handleMessage(android.os.Message msg) {
	// mOnFileloadingListener.onLoadingFinish();
	// };
	// };

	/**
	 * 遍历cacheFile文件
	 * 
	 * @param cacheFile
	 */
	public static void listCacheFile(File cacheFile, Context context) {
		if (cacheFile != null && cacheFile.exists()) {
			if (cacheFile.isDirectory() && cacheFile.canRead()) {
				File[] files = cacheFile.listFiles();
				for (File file : files) {
					listCacheFile(file, context);
				}
			} else {
				CleanChildInfo cInfo = new CleanChildInfo(context
						.getResources().getDrawable(R.drawable.ic_launcher),
						cacheFile.getName(), cacheFile.length(), cacheFile);
				mCleanGroupList.get(4).groupSize += cacheFile.length();
				mCleanGroupList.get(4).content.add(cInfo);
			}
		}
	}

	/**
	 * 泛型为CleanGroupInfo
	 * 
	 * @return ArrayList<CleanGroupInfo>
	 */
	public static ArrayList<CleanGroupInfo> getData() {
		return mCleanGroupList;
	}

	/**
	 * 初始化集合
	 */
	public static void init() {
		if (mCleanGroupList == null) {
			mCleanGroupList = new ArrayList<CleanGroupInfo>();
			for (int i = 0; i < groupName.length; i++) {
				ArrayList<CleanChildInfo> content = new ArrayList<CleanChildInfo>();

				CleanGroupInfo info = new CleanGroupInfo(groupName[i], 0,
						content);

				mCleanGroupList.add(info);
			}
		} else {
			for (CleanGroupInfo info : mCleanGroupList) {
				info.content.clear();
				info.groupSize = 0;
			}
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param pkgFile
	 * @return
	 */
	private static long getSize(File file) {
		size = 0;
		mHandler.sendEmptyMessage(LOADING);
		return getFileSize(file);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param file
	 * @return
	 */
	private static long getFileSize(File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory() && file.canRead()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					getFileSize(file2);
				}
			} else {
				size += file.length();
			}
		}
		return size;
	}

	/**
	 * 通过包名 拿到对应包的Context
	 * 
	 * @param packageName
	 * @param context
	 * @return
	 */
	public static Context getpackageContext(String packageName, Context context) {
		Context pkgContext = null;
		try {
			pkgContext = context.createPackageContext(packageName,
					Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return pkgContext;
	}

	// static OnFileloadingListener mOnFileloadingListener;

	// public void setOnFileloadingListener(
	// OnFileloadingListener mOnFileloadingListener) {
	// this.mOnFileloadingListener = mOnFileloadingListener;
	// }

	/**
	 * 回调接口
	 * 
	 * @author lenovo
	 * 
	 */
	// public interface OnFileloadingListener {
	// void onLoadingFinish();
	// }
}
