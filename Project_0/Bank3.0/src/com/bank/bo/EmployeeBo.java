package com.bank.bo;

import java.util.List;

import com.bank.to.Account;
import com.bank.to.Employee;
import com.bank.to.TransHistory;
import com.bank.exception.BusinessException;

public interface EmployeeBo {
	public List<Account> getAllPendingAccount() throws BusinessException;
	public boolean proccessAccount(String aNumber, int process) throws BusinessException;
	public Account searchByAccountNumber(String aNumber) throws BusinessException;
	public List<TransHistory> viewAllAccountTransaction(String aNumber) throws BusinessException;
	public Employee getEmployeeInfo(int id) throws BusinessException;
}
