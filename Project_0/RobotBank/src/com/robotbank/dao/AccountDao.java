package com.robotbank.dao;

import java.util.Date;
import java.util.List;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;
import com.robotbank.to.TransHistory;



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
