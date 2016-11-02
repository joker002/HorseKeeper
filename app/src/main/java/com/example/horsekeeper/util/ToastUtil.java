package com.example.horsekeeper.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast的工具类
 * 
 * @author lenovo
 * 
 */
public class ToastUtil {

	public static void toastShort(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
}
