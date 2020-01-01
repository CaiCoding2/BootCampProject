package com.revature.bank.bo;

import com.revature.bank.dao.CustomerDao;
import com.revature.bank.to.Customer;
import com.revature.exception.BusinessException;

public interface CustomerBo {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
	public CustomerDao getDao();
}
