package com.robotbank.boimpl;

import java.util.ArrayList;
import java.util.List;

import com.robotbank.bo.EmployeeBo;
import com.robotbank.dao.EmployeeDao;
import com.robotbank.daoimpl.EmployeeDaoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Employee;
import com.robotbank.to.TransHistory;

public class EmployeeBoImpl implements EmployeeBo {
	private EmployeeDao dao = null;
	@Override
	public List<Account> getAllPendingAccount() throws BusinessException {
		List<Account> pendingList = new ArrayList<>();
		pendingList = getDao().getAllPendingAccount();
		return pendingList;
	}

	@Override
	public boolean proccessAccount(Account account) throws BusinessException {
		return getDao().proccessAccount(account);
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

	@Override
	public Employee getEmployeeInfo(int id) throws BusinessException {
		Employee emp = null;
		emp =  getDao().getEmployeeInfo(id);
		return emp;
	}

}
