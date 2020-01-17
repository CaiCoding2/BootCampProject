package com.robotbank.dao;

import java.util.List;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Employee;
import com.robotbank.to.TransHistory;


public interface EmployeeDao {
	public List<Account> getAllPendingAccount() throws BusinessException;
	public boolean proccessAccount(Account account) throws BusinessException;
	public Account searchByAccountNumber(String aNumber) throws BusinessException;
	public List<TransHistory> viewAllAccountTransaction(String aNumber) throws BusinessException;
	public Employee getEmployeeInfo(int id) throws BusinessException;
}
