package com.revature.bank.bo;

import java.util.List;

import com.revature.bank.to.AccountType;
import com.revature.exception.BusinessException;

public interface AccountTypeBo {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
