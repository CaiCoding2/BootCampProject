package com.bank.dao;

import java.util.List;

import com.bank.to.AccountType;
import com.bank.exception.BusinessException;

public interface AccountTypeDao {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
