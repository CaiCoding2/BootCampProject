package com.bank.dao;

import com.bank.to.Customer;
import com.bank.exception.BusinessException;

public interface CustomerDao {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
}
