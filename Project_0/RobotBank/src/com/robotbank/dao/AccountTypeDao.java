package com.robotbank.dao;

import java.util.List;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.AccountType;



public interface AccountTypeDao {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
