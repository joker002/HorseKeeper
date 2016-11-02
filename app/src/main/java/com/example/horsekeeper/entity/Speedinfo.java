package com.example.horsekeeper.entity;

import android.graphics.drawable.Drawable;

import com.example.horsekeeper.util.ProcessType;

/**
 * ������Ϣ
 * 
 * @author lenovo
 * 
 */
public class Speedinfo {
	/**
	 * ѡ��״̬
	 */
	public boolean isCheck;
	/**
	 * Ӧ��ͼ��
	 */
	public Drawable icon;
	/**
	 * Ӧ������
	 */
	public String lable;
	/**
	 * ���̴�С
	 */
	public long processMem;
	/**
	 * ��������
	 */
	public ProcessType type;
	/**
	 * ����
	 */
	// ������������
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
