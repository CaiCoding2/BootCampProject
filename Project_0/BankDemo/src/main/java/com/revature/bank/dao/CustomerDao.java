package com.revature.bank.dao;

import com.revature.bank.to.Customer;
import com.revature.exception.BusinessException;

public interface CustomerDao {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
}
