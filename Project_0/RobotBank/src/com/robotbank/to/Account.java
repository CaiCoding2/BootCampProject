package com.robotbank.to;

public class Account {
	private String accountNumber;
	private double balance;
	private int aType;
	private int statusId;
	private String custInfo;
	
	public Account() {}

	public Account(String accountNumber, double balance, int aType, int statusId, 
			String custInfo) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.aType = aType;
		this.statusId = statusId;
		this.custInfo = custInfo;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getaType() {
		return aType;
	}

	public void setaType(int aType) {
		this.aType = aType;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(String custInfo) {
		this.custInfo = custInfo;
	}
	
	@Override
	public String toString() {
		
		return "Account#'s: "+ accountNumber + ", Balance: " + balance + ", Type: "+ aType ;
	}
	
	
}
