package com.example.horsekeeper.entity;

import java.util.ArrayList;

/**
 * �����Ϣ
 * 
 * @author lenovo
 * 
 */
public class CleanGroupInfo {
	/**
	 * ����
	 */
	public String groupName;
	/**
	 * ��ռ�ڴ�
	 */
	public long groupSize;
	/**
	 * ����
	 */
	public boolean isLoading = true;

	public ArrayList<CleanChildInfo> content;

	public CleanGroupInfo(String groupName, long groupSize,
			ArrayList<CleanChildInfo> content) {
		super();
		this.groupName = groupName;
		this.groupSize = groupSize;
		this.content = content;
	}

}
