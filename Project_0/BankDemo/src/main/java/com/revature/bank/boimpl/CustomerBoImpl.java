package com.revature.bank.boimpl;

import com.revature.bank.bo.CustomerBo;
import com.revature.bank.dao.CustomerDao;
import com.revature.bank.daoimpl.CustomerDaoImpl;
import com.revature.bank.to.Customer;
import com.revature.exception.BusinessException;

public class CustomerBoImpl implements CustomerBo {
	private CustomerDao dao = null;
	@Override
	public Customer addCustInfo(Customer cust) throws BusinessException {
		return getDao().addCustInfo(cust);
	}
	
	public CustomerDao getDao() {
		if(dao == null) {
			dao = new CustomerDaoImpl();
		}
		return dao;
	}

	@Override
	public Customer getCustInfo(int id) throws BusinessException {
		Customer cust = null;
		try {
			cust =  getDao().getCustInfo(id);
		} catch (BusinessException e) {
			
		}
		return cust;
	}

}
