package com.bank.to;

import java.util.Date;

public class Customer {
	private String CID;
	private String fname;
	private String lname;
	private Date dob;
	private String gender;
	private int UserId;
	
	public Customer() {}

	public Customer(String cID, String fname, String lname, Date dob, String gender, int userId) {
		super();
		CID = cID;
		this.fname = fname;
		this.lname = lname;
		this.dob = dob;
		this.gender = gender;
		UserId = userId;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}
	
	
	
	
	
}
