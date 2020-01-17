package com.robotbank.boimpl;

import java.util.List;

import com.robotbank.bo.AccountTypeBo;
import com.robotbank.dao.AccountTypeDao;
import com.robotbank.daoimpl.AccountTypeDaoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.AccountType;



public class AccountTypeBoImpl implements AccountTypeBo  {
	private AccountTypeDao dao = null;
	@Override
	public List<AccountType> getAccountTypeList()  throws BusinessException{
		List<AccountType> atList = null;
		try {
			atList =  getDao().getAccountTypeList();
		} catch (BusinessException e) {
			
		}
		return atList;
		
	}
	
	public AccountTypeDao getDao() {
		if(dao == null) {
			dao = new AccountTypeDaoImpl();
		}
		return dao;
		
	}
	
}
