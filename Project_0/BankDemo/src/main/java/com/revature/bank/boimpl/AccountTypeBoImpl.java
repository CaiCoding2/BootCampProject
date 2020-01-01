package com.revature.bank.boimpl;

import java.util.List;

import com.revature.bank.bo.AccountTypeBo;
import com.revature.bank.dao.AccountTypeDao;
import com.revature.bank.daoimpl.AccountTypeDaoImpl;
import com.revature.bank.to.AccountType;
import com.revature.exception.BusinessException;

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
