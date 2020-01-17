package com.robotbank.dao;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.Customer;

public interface CustomerDao {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
}
