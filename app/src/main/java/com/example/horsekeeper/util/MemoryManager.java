package com.example.horsekeeper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

/**
 * 获取手机内存信息
 * 
 * @author lenovo
 * 
 */
public class MemoryManager {
	/*
	 * RAM（random access memory）即随机存储内存，这种存储器在断电时将丢失其存储内容，故主要用于存储短时间使用的程序。
	 * 
	 * ROM（Read-Only Memory）即只读内存，是一种只能读出事先所存数据的固态半导体存储器。
	 * 
	 * 手机中的RAM和ROM与电脑类似，由于RAM被称为随机存取内存，也就是运行内存，它支撑的是手机软件的运行，存放手机软件运行后进行的数据交换等工作。也就是
	 * ，RAM决定了您的手机可以开多少后台程序，当然，RAM越大，手机的运行速度就越快。一旦手机关机，RAM中的数据就丢失，开机后也不会恢复。
	 */

	/*
	 * 手机中的机身内存(ROM) 分为 手机内置空间和手机外置空间
	 * 
	 * 
	 * 手机内置空间分为 手机自身的存储空间+手机的内置sd卡
	 * 
	 * 
	 * 手机外置存储空间 主要是指 手机的外置sd卡
	 */

	/**
	 * 获取手机自身内置内存
	 * 
	 */
	public static long getSelfMemory(boolean isAvailable) {
		// 拿到自身内存的一个文件 ?(包含手机SD卡)

		// Environment.getDataDirectory() 拿到自身存储的空间的目录
		File file = Environment.getDataDirectory();

		// LogWapper.e("aaa", "获取手机自身内置内存" + getMemory(file.getAbsolutePath()));

		return getMemory(file.getAbsolutePath(), isAvailable);
	}

	/**
	 * 获取手机自身sd卡内置内存
	 */
	public static long getSelfSDCardsMemory(boolean isAvailable) {
		// 判断是否为挂载状态
		if (!Environment.getExternalStorageDirectory().equals(
				Environment.MEDIA_MOUNTED)) {
			return 0;
		}

		File file = Environment.getExternalStorageDirectory();
		// LogWapper.e("aaa", "获取手机自身sd卡" + getMemory(file.getAbsolutePath()));
		return getMemory(file.getAbsolutePath(), isAvailable);
	}

	/**
	 * 手机内置存储大小
	 */
	// public static long getphoneInnerMemory() {
	// LogWapper.e("aaa", "手机内置存储大小"
	// + (getSelfMemory() + getSelfSDCardsMemory()));
	// return getSelfMemory() + getSelfSDCardsMemory();
	// }

	/**
	 * 手机外置sd卡存储大小
	 */
	public static long getOutSDCardMemory(Context context, boolean isAvailable) {
		long outSDCardMemory = 0;
		StorageManager manager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);

		Class<?> managerClass = manager.getClass();
		try {
			Method method = managerClass.getDeclaredMethod("getVolumePaths");
			// 不进行访问校验
			method.setAccessible(true);

			String[] paths = (String[]) method.invoke(manager);
			for (String string : paths) {
				if (!string.equals(Environment.getExternalStorageDirectory()
						.getAbsolutePath())) {
					outSDCardMemory += getMemory(string, isAvailable);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// LogWapper.e("aaa", "外置SD" + outSDCardMemory);
		return outSDCardMemory;
	}

	/**
	 * 计算内存
	 */
	public static long getMemory(String filePath, boolean isAvailable) {

		// 用来计算内存大小
		StatFs statf = new StatFs(filePath);

		// 拿到内存块数量
		// statf.getAvailableBlocks();(可用)
		long blockCount = isAvailable == true ? statf.getBlockCountLong()
				: statf.getAvailableBlocksLong();

		// 单位 B
		long blickSize = statf.getBlockSizeLong();

		// LogWapper.e("aaa", "内存" + (blockCount * blickSize));

		return blockCount * blickSize;

	}

	/**
	 * 手机运行时可用内存
	 */
	public static long getRunAvailableMemory(Context context) {

		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo info = new MemoryInfo();
		manager.getMemoryInfo(info);
		long info1 = info.availMem;

		// 拿到手机的全部运行内存(有些手机拿不到，不推荐使用)
		// long info2 = info.totalMem;

		// LogWapper.e("aaa", "--1--" + info1);
		// LogWapper.e("aaa", "--2--" + info2);
		return info1;
	}

	/**
	 * 拿到手机的运行时总内存
	 */
	public static long getRunMemory() {
		long totalMemory = 0;
		BufferedReader read = null;
		try {
			// 文件路径
			Reader rea = new FileReader("/proc/meminfo");
			read = new BufferedReader(rea);
			String info = read.readLine();

			String[] str = info.split("\\s+");
			// 单位kb
			totalMemory = Long.parseLong(str[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LogWapper.e("aaa", totalMemory * 1024 + "");

		// 保留位数
		// Decimalformat decimalformat = new Decimalformat("#.00");

		return totalMemory * 1024;
	}
}
