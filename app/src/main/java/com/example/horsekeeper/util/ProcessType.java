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
 * ö�ٹ���ģʽ
 * 
 * @author lenovo
 * 
 */
public enum ProcessType {
	/**
	 * �û���
	 */
	USER {
		@Override
		public ArrayList<Speedinfo> getRunningProgress(Context context) {
			return getRunningProcess(context, USER);
		}
	},
	/**
	 * ϵͳ��
	 */
	SYSTEM {
		@Override
		// ����ϵͳ�����н���
		public ArrayList<Speedinfo> getRunningProgress(Context context) {
			return getRunningProcess(context, SYSTEM);
		}
	};

	public ArrayList<Speedinfo> getRunningProcess(Context context,
			ProcessType type) {
		ArrayList<Speedinfo> speedListInfo = new ArrayList<Speedinfo>();
		// �õ��û�������Ϣ
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// �õ������еĽ�����Ϣ
		List<RunningAppProcessInfo> listRunningProcess = am
				.getRunningAppProcesses();

		// �õ��Ѿ���װ�� �û� Ӧ����Ϣ
		ArrayList<AppInfo> installApp = type == USER ? SoftContent.getSoftinfo(
				context, SoftContent.USER_SOFT) : SoftContent.getSoftinfo(
				context, SoftContent.SYSTEM_SOFT);

		for (RunningAppProcessInfo runningAppProcessInfo : listRunningProcess) {
			int pid = runningAppProcessInfo.pid;// ����id
			String processAppProcessName = runningAppProcessInfo.processName;// ��������
			int uid = runningAppProcessInfo.uid;// �û�id
			String[] pakList = runningAppProcessInfo.pkgList;

			Debug.MemoryInfo[] memInfo = am
					.getProcessMemoryInfo(new int[] { pid });
			long memProcess = memInfo[0].dalvikPrivateDirty * 1024;// ��λkb
			for (String string : pakList) {

				for (AppInfo insApp : installApp) {
					// ˵��ƥ��ɹ� �˰�װApp���ڽ���
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
