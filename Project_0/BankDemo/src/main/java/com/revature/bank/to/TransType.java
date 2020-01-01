package com.revature.bank.to;

public class TransType {
	private int transId;
	private String transType;
	
	public TransType() {}

	public TransType(int transId, String transType) {
		super();
		this.transId = transId;
		this.transType = transType;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	} 
	
	
	
}
