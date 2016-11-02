package com.example.horsekeeper.util;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Debug;

import com.example.horsekeeper.entity.AppInfo;
import com.example.horsekeeper.entity.Speedinfo;

/**
 * 枚举工厂模式
 * 
 * @author lenovo
 * 
 */
public enum ProcessType {
	/**
	 * 用户的
	 */
	USER {
		@Override
		public ArrayList<Speedinfo> getRunningProgress(Context context) {
			return getRunningProcess(context, USER);
		}
	},
	/**
	 * 系统的
	 */
	SYSTEM {
		@Override
		// 返回系统的运行进程
		public ArrayList<Speedinfo> getRunningProgress(Context context) {
			return getRunningProcess(context, SYSTEM);
		}
	};

	public ArrayList<Speedinfo> getRunningProcess(Context context,
			ProcessType type) {
		ArrayList<Speedinfo> speedListInfo = new ArrayList<Speedinfo>();
		// 拿到用户进程信息
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 拿到运行中的进程信息
		List<RunningAppProcessInfo> listRunningProcess = am
				.getRunningAppProcesses();

		// 拿到已经安装的 用户 应用信息
		ArrayList<AppInfo> installApp = type == USER ? SoftContent.getSoftinfo(
				context, SoftContent.USER_SOFT) : SoftContent.getSoftinfo(
				context, SoftContent.SYSTEM_SOFT);

		for (RunningAppProcessInfo runningAppProcessInfo : listRunningProcess) {
			int pid = runningAppProcessInfo.pid;// 进程id
			String processAppProcessName = runningAppProcessInfo.processName;// 进程名称
			int uid = runningAppProcessInfo.uid;// 用户id
			String[] pakList = runningAppProcessInfo.pkgList;

			Debug.MemoryInfo[] memInfo = am
					.getProcessMemoryInfo(new int[] { pid });
			long memProcess = memInfo[0].dalvikPrivateDirty * 1024;// 单位kb
			for (String string : pakList) {

				for (AppInfo insApp : installApp) {
					// 说明匹配成功 此安装App正在进行
					if (string.equals(insApp.getPackageName())) {
						Speedinfo info = new Speedinfo(insApp.getIcon(),
								insApp.getLable(), memProcess, USER, string);
						speedListInfo.add(info);
					}
				}
			}
		}
		return speedListInfo;
	}

	public abstract ArrayList<Speedinfo> getRunningProgress(Context context);

}
