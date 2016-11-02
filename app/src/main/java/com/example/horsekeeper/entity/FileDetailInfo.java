package com.example.horsekeeper.entity;

import java.io.File;
import java.io.Serializable;

/**
 * 文件详细信息
 * 
 * @author lenovo
 * 
 */
public class FileDetailInfo implements Serializable {
	/**
	 * 选框
	 */
	private boolean isChecked;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件最后一次修改的时间
	 */
	private long fileTime;
	/**
	 * 文件大小
	 */
	private long fileSize;
	/**
	 * 文件的对象
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
