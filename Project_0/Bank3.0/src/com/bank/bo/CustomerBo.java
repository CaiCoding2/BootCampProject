package com.bank.bo;

import com.bank.dao.CustomerDao;
import com.bank.to.Customer;
import com.bank.exception.BusinessException;

public interface CustomerBo {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
	public CustomerDao getDao();
}
