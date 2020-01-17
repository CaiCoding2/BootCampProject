package com.robotbank.bo;

import java.util.List;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;
import com.robotbank.to.TransHistory;


public interface AccountBo {
	public boolean checkAccount(String custId) throws BusinessException; 
	public Account newAccount(Account acc) throws BusinessException;
	public boolean deposit(String account,double amount)throws BusinessException ;
	public boolean withdrawal(String account,double amount) throws BusinessException;
	public boolean transfer(String transferFrom, String transferTo, double amount) throws BusinessException;
	public List<Account> getAllAccount(String custId) throws BusinessException;
	public List<TransHistory> getAccountTransaction(String account) throws BusinessException;
	public Customer getCustomer(String custInfo) throws BusinessException;
} 
