package com.bank.boimpl;

import java.util.List;

import com.bank.bo.AccountTypeBo;
import com.bank.dao.AccountTypeDao;
import com.bank.daoimpl.AccountTypeDaoImpl;
import com.bank.to.AccountType;
import com.bank.exception.BusinessException;

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
