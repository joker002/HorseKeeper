package com.example.horsekeeper.entity;

import android.graphics.drawable.Drawable;

/**
 * �����Ϣ
 * 
 * @author lenovo
 * 
 */
public class AppInfo {
	/**
	 * Ӧ������
	 */
	private String lable;
	/**
	 * ����
	 */
	private String packageName;
	/**
	 * �汾��
	 */
	private String versionName;
	/**
	 * ͼ��
	 */
	private Drawable icon;
	/**
	 * ѡ����
	 */
	public boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public AppInfo(String lable, String packageName, String versionName,
			Drawable icon) {
		super();
		this.lable = lable;
		this.packageName = packageName;
		this.versionName = versionName;
		this.icon = icon;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "AppInfo [lable=" + lable + ", packageName=" + packageName
				+ ", versionName=" + versionName + ", icon=" + icon + "]";
	}

}
