package com.example.horsekeeper.util;

import android.util.Log;

import com.example.horsekeeper.BuildConfig;

/**
 * Log工具包
 * 
 * @author lenovo
 * 
 */
public class LogWapper {
	/**
	 * 打印日志的开关 自己的开关与调试的开关
	 */
	private static boolean DEBUG = true & BuildConfig.DEBUG;

	/**
	 * 打印error级别的Log日志
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
	 * 打印warning级别的Log日志
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
	 * 打印debug级别的Log日志
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
	 * 打印info级别的Log日志
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
