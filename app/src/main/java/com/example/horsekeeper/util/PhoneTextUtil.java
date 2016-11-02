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
	 * �õ��ֻ�cpu����"/proc/cpuinfo" /proc:��ȡ�豸����ʱ����Ϣ /proc/meminfo ����ʱ�����ڴ�
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
		return "δ֪";
	}

	/**
	 * ��ȡ�ֻ�cpu�ĺ����� /sys/devices/system/cpu
	 */
	public static int getCpuCores() {
		File file = new File("/sys/devices/system/cpu");
		// FileFilter �����ļ�
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
	 * ��ȡ�ֻ���Ļ�ķֱ���
	 */
	public static String getPhoneRadios(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int height = metrics.heightPixels;
		int width = metrics.widthPixels;
		return width + "*" + height;
	}

	/**
	 * ��ȡ�ֻ�����ֱ���
	 */
	public static String getCameraRadios() {
		// ʹ�õ���hardware������� ���ܷ���Ϊnull
		// ���ֻ������ҪȨ��
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
		// ��Ҫ�ر����
		camera.release();

		return sizes.get(0).width + "*" + sizes.get(0).height;

	}

	/**
	 * ��û���(�ֻ��е�һ���·�� ���� ͨѶ ����)�汾
	 * 
	 * @return
	 */
	public static String getBasebandVersion() {
		// Build SDK_INT sdk�汾 MODEL�ͺ� RELEASE�����汾 BRAND�̱� �����汾

		return Build.getRadioVersion() == null ? "unknown" : Build
				.getRadioVersion();

	}

	/**
	 * �ж��ֻ��Ƿ�root
	 * 
	 * @return
	 */
	public static boolean isRoot() {
		// 1./system/bin/su ����/system/xbin/su
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
			// �����д
			ou = process.getOutputStream();
			// ִ��Linux ���� ls-->List �����ļ��� cat--->���ļ���
			ou.write("ls data \n".getBytes());
			// ����ִ���� ��Ҫ�ر�
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
