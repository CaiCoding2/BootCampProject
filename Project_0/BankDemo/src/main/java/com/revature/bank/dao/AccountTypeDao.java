package com.revature.bank.dao;

import java.util.List;

import com.revature.bank.to.AccountType;
import com.revature.exception.BusinessException;

public interface AccountTypeDao {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
