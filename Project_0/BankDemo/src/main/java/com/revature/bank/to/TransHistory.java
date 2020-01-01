package com.revature.bank.to;

import java.util.Date;

public class TransHistory {
	private int HID;
	private String AccountNum;
	private double amount;
	private TransType tType;
	private String transFrom;
	private String transTo;
	private Date log;
	
	public TransHistory() {}

	public TransHistory(int hID, String accountNum, double amount, TransType tType, String transFrom, String transTo,
			Date log) {
		super();
		HID = hID;
		AccountNum = accountNum;
		this.amount = amount;
		this.tType = tType;
		this.transFrom = transFrom;
		this.transTo = transTo;
		this.log = log;
	}

	public int getHID() {
		return HID;
	}

	public void setHID(int hID) {
		HID = hID;
	}

	public String getAccountNum() {
		return AccountNum;
	}

	public void setAccountNum(String accountNum) {
		AccountNum = accountNum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransType gettType() {
		return tType;
	}

	public void settType(TransType tType) {
		this.tType = tType;
	}

	public String getTransFrom() {
		return transFrom;
	}

	public void setTransFrom(String transFrom) {
		this.transFrom = transFrom;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}

	public Date getLog() {
		return log;
	}

	public void setLog(Date log) {
		this.log = log;
	}
	@Override
	public String toString() {
		return "Amount: " + amount+", Type: "+ tType.getTransType()
				+", transFrom: " + transFrom + ", TransTo: " + transTo + ", Date: "+log;
	}
	
}
