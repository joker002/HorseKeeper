package com.example.horsekeeper.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * �ļ���Ϣ
 * 
 * @author lenovo
 * 
 */
public class FileInfo implements Serializable {
	/**
	 * �ļ�����
	 */
	private static int fileTyle;
	/**
	 * �ļ���С
	 */
	public long fileSize;
	/**
	 * �ļ�����
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
