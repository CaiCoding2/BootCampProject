package com.bank.boimpl;

import com.bank.bo.CustomerBo;
import com.bank.dao.CustomerDao;
import com.bank.daoimpl.CustomerDaoImpl;
import com.bank.to.Customer;
import com.bank.exception.BusinessException;

public class CustomerBoImpl implements CustomerBo {
	private CustomerDao dao = null;
	@Override
	public Customer addCustInfo(Customer cust) throws BusinessException {
		/*
	    if(cust.getFname().matches("^[a-zA-Z]*$")) {}
	    else {throw new BusinessException("Firstname not in right format");}
	  
    	if(cust.getLname().matches("^[a-zA-Z]*$")) {}
    	else {throw new BusinessException("Lastname not in right format");}
	
		if(cust.getGender().matches("^[a-zA-Z]*$") && cust.getGender().equalsIgnoreCase("m")|| 
				cust.getGender().equalsIgnoreCase("f")||
				cust.getGender().equalsIgnoreCase("o")) {}
		else {throw new BusinessException("Gender not correctly input");}
		if(cust.getDob().toString().matches("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}")) {}
		else {throw new BusinessException("DOB not right format");}  */
	
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
