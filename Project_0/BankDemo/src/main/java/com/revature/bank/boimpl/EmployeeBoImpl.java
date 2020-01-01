package com.revature.bank.boimpl;

import java.util.ArrayList;
import java.util.List;

import com.revature.bank.bo.EmployeeBo;
import com.revature.bank.dao.EmployeeDao;
import com.revature.bank.daoimpl.EmployeeDaoImpl;
import com.revature.bank.to.Account;
import com.revature.bank.to.TransHistory;
import com.revature.exception.BusinessException;

public class EmployeeBoImpl implements EmployeeBo {
	private EmployeeDao dao = null;
	@Override
	public List<Account> getAllPendingAccount() throws BusinessException {
		List<Account> pendingList = new ArrayList<>();
		pendingList = getDao().getAllPendingAccount();
		return pendingList;
	}

	@Override
	public boolean proccessAccount(String aNumber, int process) throws BusinessException {
		return getDao().proccessAccount(aNumber, process);
	}

	@Override
	public Account searchByAccountNumber(String aNumber) throws BusinessException {
		Account account = null;
		if(aNumber.matches("BR[0-9]{6}")) {
			account = getDao().searchByAccountNumber(aNumber);
		}else {
			throw new BusinessException("Enter acount# " + aNumber+" is invalid");
		}
		return account;
	}

	@Override
	public List<TransHistory> viewAllAccountTransaction(String aNumber)throws BusinessException {
		List<TransHistory> transList = null;
		if(aNumber.matches("BR[0-9]{6}")) {
			transList = getDao().viewAllAccountTransaction(aNumber);
		}else {
			throw new BusinessException("Enter acount# " + aNumber+" is invalid");
		}
		return transList;
	}
	
	public EmployeeDao getDao() {
		if(dao == null) {
			dao = new EmployeeDaoImpl();
		}
		return dao;
	}

}
