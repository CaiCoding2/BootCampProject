package com.bank.dao;

import java.util.Date;
import java.util.List;

import com.bank.to.Account;
import com.bank.to.Customer;
import com.bank.to.TransHistory;
import com.bank.exception.BusinessException;

public interface AccountDao {
	public boolean checkAccount(String custId) throws BusinessException; 
	public Account newAccount(Account acc) throws BusinessException;
	public boolean deposit(String account,double amount, Date date) throws BusinessException;
	public boolean withdrawal(String account,double amount, Date date) throws BusinessException;
	public boolean transfer(String transferFrom, String transferTo, double amount, Date date) throws BusinessException;
	public List<Account> getAllAccount(String custId) throws BusinessException ;
	public List<TransHistory> getAccountTransaction(String account) throws BusinessException;
	public Customer getCustomer(String custInfo) throws BusinessException;
}
