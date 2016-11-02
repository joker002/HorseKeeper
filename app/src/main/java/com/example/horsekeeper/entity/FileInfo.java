package com.example.horsekeeper.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 文件信息
 * 
 * @author lenovo
 * 
 */
public class FileInfo implements Serializable {
	/**
	 * 文件类型
	 */
	private static int fileTyle;
	/**
	 * 文件大小
	 */
	public long fileSize;
	/**
	 * 文件数量
	 */
	private int fileNumber;

	public ArrayList<FileDetailInfo> fileDetail = new ArrayList<FileDetailInfo>();

	public static int getFileTyle() {
		return fileTyle;
	}

	public void setFileTyle(int fileTyle) {
		this.fileTyle = fileTyle;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

}
