package com.revature.bank.bo;

import java.util.List;

import com.revature.bank.to.Account;
import com.revature.bank.to.Employee;
import com.revature.bank.to.TransHistory;
import com.revature.exception.BusinessException;

public interface EmployeeBo {
	public List<Account> getAllPendingAccount() throws BusinessException;
	public boolean proccessAccount(String aNumber, int process) throws BusinessException;
	public Account searchByAccountNumber(String aNumber) throws BusinessException;
	public List<TransHistory> viewAllAccountTransaction(String aNumber) throws BusinessException;
	public Employee getEmployeeInfo(int id) throws BusinessException;
}
