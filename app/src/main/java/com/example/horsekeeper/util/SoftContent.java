package com.example.horsekeeper.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.horsekeeper.entity.AppInfo;

/**
 * 获取软件信息的工具类
 * 
 * @author cc
 * 
 */
public class SoftContent {
	/**
	 * 所有
	 */
	public static final int All_SOFT = 200;
	/**
	 * 系统
	 */
	public static final int SYSTEM_SOFT = 201;
	/**
	 * 用户
	 */
	public static final int USER_SOFT = 202;

	/**
	 * 静态方法 返回值为ArrayList<AppInfo>
	 * 
	 * @param mContext
	 * @param type
	 * @return
	 */
	public static ArrayList<AppInfo> getSoftinfo(Context mContext, int type) {
		// 三个集合 用来分类装软件
		ArrayList<AppInfo> listAll = new ArrayList<AppInfo>();
		ArrayList<AppInfo> listSystem = new ArrayList<AppInfo>();
		ArrayList<AppInfo> listUser = new ArrayList<AppInfo>();

		/**
		 * (mContext.getPackageManager())Return PackageManager instance to find
		 * global package information.
		 */
		PackageManager pm = mContext.getPackageManager();

		/**
		 * Return a List of "all packages" that are installed on the device.
		 */
		List<PackageInfo> list = pm.getInstalledPackages(0);

		for (PackageInfo packageInfo : list) {

			// 直接可以拿到包名和版本名
			String packageName = packageInfo.packageName;
			String versionName = packageInfo.versionName;

			/**
			 * Information you can retrieve about a particular application. This
			 * corresponds to information collected from the
			 * AndroidManifest.xml's &lt;application&gt; tag.
			 */
			ApplicationInfo appInfo = packageInfo.applicationInfo;

			// 拿到图标和应用名
			Drawable icon = appInfo.loadIcon(pm);
			// appInfo.loadLabel(pm);
			String lable = appInfo.loadLabel(pm).toString();

			AppInfo appInfo1 = new AppInfo(lable, packageName, versionName,
					icon);

			// (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0
			// 判断是否是系统的软件
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				listSystem.add(appInfo1);
			} else {
				listUser.add(appInfo1);
			}

			listAll.add(appInfo1);
		}

		if (type == SYSTEM_SOFT) {
			return listSystem;
		} else if (type == USER_SOFT) {
			return listUser;
		}
		return listAll;
	}
}
