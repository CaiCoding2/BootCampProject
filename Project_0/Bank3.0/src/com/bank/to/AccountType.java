package com.bank.to;

public class AccountType {
	private int typeId;
	private String types;
	
	public AccountType() {}

	public AccountType(int typeId, String types) {
		super();
		this.typeId = typeId;
		this.types = types;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	
}
