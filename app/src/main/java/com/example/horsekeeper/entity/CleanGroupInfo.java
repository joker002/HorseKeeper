package com.example.horsekeeper.entity;

import java.util.ArrayList;

/**
 * 组的信息
 * 
 * @author lenovo
 * 
 */
public class CleanGroupInfo {
	/**
	 * 祖名
	 */
	public String groupName;
	/**
	 * 所占内存
	 */
	public long groupSize;
	/**
	 * 加载
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
