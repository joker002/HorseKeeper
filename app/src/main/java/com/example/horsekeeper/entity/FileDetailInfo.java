package com.example.horsekeeper.entity;

import java.io.File;
import java.io.Serializable;

/**
 * �ļ���ϸ��Ϣ
 * 
 * @author lenovo
 * 
 */
public class FileDetailInfo implements Serializable {
	/**
	 * ѡ��
	 */
	private boolean isChecked;
	/**
	 * �ļ���
	 */
	private String fileName;
	/**
	 * �ļ����һ���޸ĵ�ʱ��
	 */
	private long fileTime;
	/**
	 * �ļ���С
	 */
	private long fileSize;
	/**
	 * �ļ��Ķ���
	 */
	public static File file;

	public int fileType;

	public FileDetailInfo(String fileName, long fileTime, long fileSize,
			File file) {
		super();
		this.fileName = fileName;
		this.fileTime = fileTime;
		this.fileSize = fileSize;
		this.file = file;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileTime() {

		return fileTime;
	}

	public void setFileTime(long fileTime) {
		this.fileTime = fileTime;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileDetailInfo [isChecked=" + isChecked + ", fileName="
				+ fileName + ", fileTime=" + fileTime + ", fileSize="
				+ fileSize + ", file=" + file + "]";
	}

}
