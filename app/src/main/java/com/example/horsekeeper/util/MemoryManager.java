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
 * ��ȡ�ֻ��ڴ���Ϣ
 * 
 * @author lenovo
 * 
 */
public class MemoryManager {
	/*
	 * RAM��random access memory��������洢�ڴ棬���ִ洢���ڶϵ�ʱ����ʧ��洢���ݣ�����Ҫ���ڴ洢��ʱ��ʹ�õĳ���
	 * 
	 * ROM��Read-Only Memory����ֻ���ڴ棬��һ��ֻ�ܶ��������������ݵĹ�̬�뵼��洢����
	 * 
	 * �ֻ��е�RAM��ROM��������ƣ�����RAM����Ϊ�����ȡ�ڴ棬Ҳ���������ڴ棬��֧�ŵ����ֻ���������У�����ֻ�������к���е����ݽ����ȹ�����Ҳ����
	 * ��RAM�����������ֻ����Կ����ٺ�̨���򣬵�Ȼ��RAMԽ���ֻ��������ٶȾ�Խ�졣һ���ֻ��ػ���RAM�е����ݾͶ�ʧ��������Ҳ����ָ���
	 */

	/*
	 * �ֻ��еĻ����ڴ�(ROM) ��Ϊ �ֻ����ÿռ���ֻ����ÿռ�
	 * 
	 * 
	 * �ֻ����ÿռ��Ϊ �ֻ�����Ĵ洢�ռ�+�ֻ�������sd��
	 * 
	 * 
	 * �ֻ����ô洢�ռ� ��Ҫ��ָ �ֻ�������sd��
	 */

	/**
	 * ��ȡ�ֻ����������ڴ�
	 * 
	 */
	public static long getSelfMemory(boolean isAvailable) {
		// �õ������ڴ��һ���ļ� ?(�����ֻ�SD��)

		// Environment.getDataDirectory() �õ�����洢�Ŀռ��Ŀ¼
		File file = Environment.getDataDirectory();

		// LogWapper.e("aaa", "��ȡ�ֻ����������ڴ�" + getMemory(file.getAbsolutePath()));

		return getMemory(file.getAbsolutePath(), isAvailable);
	}

	/**
	 * ��ȡ�ֻ�����sd�������ڴ�
	 */
	public static long getSelfSDCardsMemory(boolean isAvailable) {
		// �ж��Ƿ�Ϊ����״̬
		if (!Environment.getExternalStorageDirectory().equals(
				Environment.MEDIA_MOUNTED)) {
			return 0;
		}

		File file = Environment.getExternalStorageDirectory();
		// LogWapper.e("aaa", "��ȡ�ֻ�����sd��" + getMemory(file.getAbsolutePath()));
		return getMemory(file.getAbsolutePath(), isAvailable);
	}

	/**
	 * �ֻ����ô洢��С
	 */
	// public static long getphoneInnerMemory() {
	// LogWapper.e("aaa", "�ֻ����ô洢��С"
	// + (getSelfMemory() + getSelfSDCardsMemory()));
	// return getSelfMemory() + getSelfSDCardsMemory();
	// }

	/**
	 * �ֻ�����sd���洢��С
	 */
	public static long getOutSDCardMemory(Context context, boolean isAvailable) {
		long outSDCardMemory = 0;
		StorageManager manager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);

		Class<?> managerClass = manager.getClass();
		try {
			Method method = managerClass.getDeclaredMethod("getVolumePaths");
			// �����з���У��
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

		// LogWapper.e("aaa", "����SD" + outSDCardMemory);
		return outSDCardMemory;
	}

	/**
	 * �����ڴ�
	 */
	public static long getMemory(String filePath, boolean isAvailable) {

		// ���������ڴ��С
		StatFs statf = new StatFs(filePath);

		// �õ��ڴ������
		// statf.getAvailableBlocks();(����)
		long blockCount = isAvailable == true ? statf.getBlockCountLong()
				: statf.getAvailableBlocksLong();

		// ��λ B
		long blickSize = statf.getBlockSizeLong();

		// LogWapper.e("aaa", "�ڴ�" + (blockCount * blickSize));

		return blockCount * blickSize;

	}

	/**
	 * �ֻ�����ʱ�����ڴ�
	 */
	public static long getRunAvailableMemory(Context context) {

		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo info = new MemoryInfo();
		manager.getMemoryInfo(info);
		long info1 = info.availMem;

		// �õ��ֻ���ȫ�������ڴ�(��Щ�ֻ��ò��������Ƽ�ʹ��)
		// long info2 = info.totalMem;

		// LogWapper.e("aaa", "--1--" + info1);
		// LogWapper.e("aaa", "--2--" + info2);
		return info1;
	}

	/**
	 * �õ��ֻ�������ʱ���ڴ�
	 */
	public static long getRunMemory() {
		long totalMemory = 0;
		BufferedReader read = null;
		try {
			// �ļ�·��
			Reader rea = new FileReader("/proc/meminfo");
			read = new BufferedReader(rea);
			String info = read.readLine();

			String[] str = info.split("\\s+");
			// ��λkb
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

		// ����λ��
		// Decimalformat decimalformat = new Decimalformat("#.00");

		return totalMemory * 1024;
	}
}
