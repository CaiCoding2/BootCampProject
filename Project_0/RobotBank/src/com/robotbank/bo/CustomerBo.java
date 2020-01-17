package com.robotbank.bo;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.Customer;

public interface CustomerBo {
	public Customer addCustInfo(Customer cust) throws BusinessException;
	public Customer getCustInfo(int id) throws BusinessException;
}
