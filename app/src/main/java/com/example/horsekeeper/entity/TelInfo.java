package com.example.horsekeeper.entity;

public class TelInfo {
	private String name;
	private String number;

	public TelInfo(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "TelInfo [name=" + name + ", number=" + number + "]";
	}

}
