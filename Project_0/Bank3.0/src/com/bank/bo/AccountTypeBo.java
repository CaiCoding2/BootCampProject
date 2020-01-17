package com.bank.bo;

import java.util.List;

import com.bank.to.AccountType;
import com.bank.exception.BusinessException;

public interface AccountTypeBo {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
