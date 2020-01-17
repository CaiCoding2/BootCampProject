package com.bank.to;

public class Employee {
	private String EID;
	private String fname;
	private String lname;
	private int userId;
	public Employee () {}

	public Employee(String eID, String fname, String lname, int userId) {
		super();
		EID = eID;
		this.fname = fname;
		this.lname = lname;
		this.userId = userId;
	}

	public String getEID() {
		return EID;
	}

	public void setEID(String eID) {
		EID = eID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
