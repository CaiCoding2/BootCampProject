package com.robotbank.bo;

import java.util.List;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.AccountType;



public interface AccountTypeBo {
	public List<AccountType> getAccountTypeList() throws BusinessException;
}
