package com.example.horsekeeper.util;

import java.io.File;
import java.text.DecimalFormat;

/**
 * ��ȡ�ļ���Ϣ�İ�����
 */
public class FileUtils {

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊ�ĵ����ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return ������ĵ����ļ�����true�����򷵻�false
	 */
	public static boolean isTextFile(String suffix) {
		String[] str = { "txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
				"pdf", "c", "h", "cpp", "hpp", "java", "js", "html", "xml",
				"xhtml", "css" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊ��Ƶ���ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return �������Ƶ���ļ�����true�����򷵻�false
	 */
	public static boolean isVideoFile(String suffix) {
		String[] str = { "avi", "mp4", "rm", "rmvb", "3gp", "flash" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊ��Ƶ���ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return �������Ƶ���ļ�����true�����򷵻�false
	 */
	public static boolean isAudioFile(String suffix) {
		String[] str = { "mp3", "wav", "wma", };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊͼ�����ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return �����ͼ�����ļ�����true�����򷵻�false
	 */
	public static boolean isImageFile(String suffix) {
		String[] str = { "bmp", "jpg", "gif", "png", "jpeg", "ico", "tiff",
				"xcf" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊѹ���ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return �����ѹ���ļ�����true�����򷵻�false
	 */
	public static boolean isZipFile(String suffix) {
		String[] str = { "zip", "rar", "gz", "tar" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ļ�����׺�жϸ��ļ��Ƿ�Ϊ������ļ�
	 * 
	 * @param suffix
	 *            �ļ����ĺ�׺
	 * 
	 * @return ����ǳ�����ļ�����true�����򷵻�false
	 */
	public static boolean isProgramFile(String suffix) {
		String[] str = { "apk" };
		for (String string : str) {
			if (suffix.equals(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ʽ���ļ�����
	 * 
	 * @param length
	 *            һ��long���͵�������ʾ�ļ��ĳ��ȣ���λ�ǣ�B
	 * 
	 * @return ����һ�����к��ʵ�λ�ģ���ʾ�ļ����ȵ�С��1024���ַ������͵���
	 */
	public static String formatLength(long length) {
		DecimalFormat format = new DecimalFormat(".0");

		if (length >= 1024 && length < 1024 * 1024) {
			double len = length * 1.0 / 1024;
			String string = format.format(len);
			return string + "K";
		} else if (length >= 1024 * 1024 && length < 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024);
			String string = format.format(len);
			return string + "M";
		} else if (length >= 1024 * 1024 * 1024) {
			double len = length * 1.0 / (1024 * 1024 * 1024);
			String string = format.format(len);
			return string + "G";
		}

		return length + "B";
	}

	/**
	 * ɾ��
	 * 
	 * @param file
	 * @param deleteThisPath
	 */
	public static void deleteFolderFile(File file, boolean deleteThisPath) {
		// if (!TextUtils.isEmpty(filePath)) {
		try {
			// directory Ŀ¼
			if (file.isDirectory() && file.canRead()) {// ������滹���ļ�
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i], true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {// ������ļ���ɾ��
					file.delete();
				} else {// Ŀ¼
					if (file.listFiles().length == 0) {// Ŀ¼��û���ļ�����Ŀ¼��ɾ��
						file.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
