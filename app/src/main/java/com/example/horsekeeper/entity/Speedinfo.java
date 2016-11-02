package com.example.horsekeeper.entity;

import android.graphics.drawable.Drawable;

import com.example.horsekeeper.util.ProcessType;

/**
 * 进程信息
 * 
 * @author lenovo
 * 
 */
public class Speedinfo {
	/**
	 * 选中状态
	 */
	public boolean isCheck;
	/**
	 * 应用图标
	 */
	public Drawable icon;
	/**
	 * 应用名称
	 */
	public String lable;
	/**
	 * 进程大小
	 */
	public long processMem;
	/**
	 * 进程类型
	 */
	public ProcessType type;
	/**
	 * 包名
	 */
	// 用来结束进程
	public String packageName;

	public Speedinfo(Drawable icon, String lable, long processMem,
			ProcessType type, String packageName) {
		super();
		this.icon = icon;
		this.lable = lable;
		this.processMem = processMem;
		this.type = type;
		this.packageName = packageName;
	}

}
