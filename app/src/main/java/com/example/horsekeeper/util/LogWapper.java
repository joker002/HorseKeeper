package com.example.horsekeeper.util;

import android.util.Log;

import com.example.horsekeeper.BuildConfig;

/**
 * Log���߰�
 * 
 * @author lenovo
 * 
 */
public class LogWapper {
	/**
	 * ��ӡ��־�Ŀ��� �Լ��Ŀ�������ԵĿ���
	 */
	private static boolean DEBUG = true & BuildConfig.DEBUG;

	/**
	 * ��ӡerror�����Log��־
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void e(String tag, String msg) {
		if (DEBUG) {

			Log.e(tag, msg);
		}
	}

	/**
	 * ��ӡwarning�����Log��־
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void w(String tag, String msg) {
		if (DEBUG) {

			Log.w(tag, msg);
		}
	}

	/**
	 * ��ӡdebug�����Log��־
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void d(String tag, String msg) {
		if (DEBUG) {

			Log.d(tag, msg);
		}
	}

	/**
	 * ��ӡinfo�����Log��־
	 * 
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static void i(String tag, String msg) {
		if (DEBUG) {

			Log.i(tag, msg);
		}
	}
}
