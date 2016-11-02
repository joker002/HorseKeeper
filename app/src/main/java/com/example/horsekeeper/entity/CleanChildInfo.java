package com.example.horsekeeper.entity;

import java.io.File;

import android.graphics.drawable.Drawable;

/**
 * 子的信息
 * 
 * @author lenovo
 * 
 */
public class CleanChildInfo {
	public boolean isChecked;
	public Drawable icon;
	public String lable;
	public long size;
	public File file;

	public CleanChildInfo(Drawable icon, String lable, long size, File file) {
		super();
		this.icon = icon;
		this.lable = lable;
		this.size = size;
		this.file = file;
	}

	// public CleanChildInfo(Drawable icon2, String lable2, long phoneFileSize,
	// File phoneFile) {
	// }

}
