package com.example.horsekeeper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.DisplayMetrics;

public class PhoneTextUtil {
	/**
	 * 
	 * 拿到手机cpu名称"/proc/cpuinfo" /proc:获取设备运行时的信息 /proc/meminfo 运行时的总内存
	 * 
	 * @return
	 */
	public static String getCpuName() {
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
			String str = buffReader.readLine();
			String[] str1 = str.split(":\\s");
			return str1[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buffReader != null) {
				try {
					buffReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "未知";
	}

	/**
	 * 获取手机cpu的核心数 /sys/devices/system/cpu
	 */
	public static int getCpuCores() {
		File file = new File("/sys/devices/system/cpu");
		// FileFilter 过滤文件
		File[] file1 = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				String str = pathname.getName();
				boolean bol = str.matches("cpu[0-9]");
				return bol;
			}
		});

		return file1.length;
	}

	/**
	 * 获取手机屏幕的分辨率
	 */
	public static String getPhoneRadios(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		return width + "*" + height;
	}

	/**
	 * 获取手机相机分辨率
	 */
	public static String getCameraRadios() {
		// 使用的是hardware包下面的 可能返回为null
		// 打开手机相机需要权限
		Camera camera = Camera.open();
		if (camera == null) {
			return "";
		}

		Parameters params = camera.getParameters();
		List<Size> sizes = params.getSupportedPictureSizes();

		Collections.sort(sizes, new Comparator<Size>() {

			@Override
			public int compare(Size lhs, Size rhs) {
				return rhs.height * rhs.width - lhs.height * lhs.width;
			}
		});
		// 需要关闭相机
		camera.release();

		return sizes.get(0).width + "*" + sizes.get(0).height;

	}

	/**
	 * 获得基带(手机中的一块电路板 网络 通讯 短信)版本
	 * 
	 * @return
	 */
	public static String getBasebandVersion() {
		// Build SDK_INT sdk版本 MODEL型号 RELEASE发布版本 BRAND商标 基带版本

		return Build.getRadioVersion() == null ? "unknown" : Build
				.getRadioVersion();

	}

	/**
	 * 判断手机是否root
	 * 
	 * @return
	 */
	public static boolean isRoot() {
		// 1./system/bin/su 或者/system/xbin/su
		if (new File("/system/bin/su").exists()
				| new File("/system/xbin/su").exists()) {
			return true;
		}
		return false;
	}

	public static boolean isRootTwo() {
		Runtime runtime = Runtime.getRuntime();
		OutputStream ou = null;
		try {
			Process process = runtime.exec("su");
			// 输出流写
			ou = process.getOutputStream();
			// 执行Linux 命令 ls-->List 遍历文件夹 cat--->打开文件夹
			ou.write("ls data \n".getBytes());
			// 命令执行完 需要关闭
			ou.write("exit \n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ou != null) {
				try {
					ou.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;

	}
}
