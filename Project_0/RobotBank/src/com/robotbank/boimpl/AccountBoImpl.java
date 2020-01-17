package com.robotbank.boimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.robotbank.bo.AccountBo;
import com.robotbank.dao.AccountDao;
import com.robotbank.daoimpl.AccountDaoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;
import com.robotbank.to.TransHistory;


public class AccountBoImpl implements AccountBo {
	private AccountDao dao = null;
	private DateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");

	Date date = null;
	@Override
	public Account newAccount(Account acc) throws BusinessException {
		try {
			//acc.setBalance(0); // 0 balance
			//acc.setStatusId(1); // default pending
			acc = getDao().newAccount(acc);
		} catch (BusinessException e ) {
			
		}
		return acc;
	}

	@Override
	public boolean deposit(String account,double amount) throws BusinessException {
		String dateString = dateformat.format(new Date());
		try {//get data 
			date = dateformat.parse(dateString);
		} catch(ParseException e) {
			throw new BusinessException("System date format error");
		}
		//InputMismatchException
		
		if(amount <= 0) {
			throw new BusinessException("Invalid amount to depoist");
		}
		String a = Double.toString(amount);
		if(a.matches("^\\d*(\\.\\d{1,2})?$")) {
			if(account.matches("BR[0-9]{6}")) {
				return getDao().deposit(account,amount,date);
			}else {
				throw new BusinessException("account number is invalid");
			}
		}
		else {throw new BusinessException("Invalid format.... (#.##)");}
	}
	
	@Override
	public boolean withdrawal(String account,double amount) throws BusinessException {
		String dateString = dateformat.format(new Date());
		try {//get data 
			date = dateformat.parse(dateString);
		} catch(ParseException e) {
			throw new BusinessException("System date format error");
		}
		
		if(amount <= 0) {
			throw new BusinessException("Invalid amount to withdrawal");	
		}
		String a = Double.toString(amount);
		if(a.matches("^\\d*(\\.\\d{1,2})?$")) {
			if(account.matches("BR[0-9]{6}")) {
				return getDao().withdrawal(account, amount,date);
			}else {
				throw new BusinessException("account number is invalid");
			}
		}
		else {throw new BusinessException("Invalid format.... (#.##)");}
		
		
		
	}

	@Override
	public boolean transfer(String transferFrom, String transferTo, double amount) throws BusinessException {
		String dateString = dateformat.format(new Date());
		try {//get data 
			date = dateformat.parse(dateString);
		} catch(ParseException e) {
			throw new BusinessException("System date format error");
		}
		if(amount <= 0) {
			throw new BusinessException("Invalid amount to transfer");
		}
		String a = Double.toString(amount);
		if(a.matches("^\\d*(\\.\\d{1,2})?$")) {
			if(transferFrom.matches("BR[0-9]{6}")){
				if(transferTo.matches("BR[0-9]{6}")) {
					return getDao().transfer(transferFrom, transferTo, amount,date);
				}else {
					throw new BusinessException("recipient account number is invalid");
				}
			}else {
				throw new BusinessException("account number is invalid");
			}
		}else {
			throw new BusinessException("Invalid format.... (#.##)");
		}
		
		
	}
	
	public AccountDao getDao() {
		if(dao == null) {
			dao = new AccountDaoImpl();
		}
		return dao;
	}

	@Override
	public List<Account> getAllAccount(String custId) throws BusinessException {
		List<Account> aList = null;
		if(custId.matches("BR[0-9]{4}[A-Z]{1}")) {
			aList = getDao().getAllAccount(custId);
		}else {
			throw new BusinessException("Enter customer# " + custId+" is invalid");
		}
		return aList;
	}

	@Override
	public boolean checkAccount(String custId) throws BusinessException {
		if(custId.matches("BR[0-9]{4}[A-Z]{1}")) {
			return  getDao().checkAccount(custId);
		}else {
			throw new BusinessException("Enter customer# " + custId+" is invalid");
		}
	}

	@Override
	public List<TransHistory> getAccountTransaction(String account) throws BusinessException {
		List<TransHistory> tList = null;
		if(account.matches("BR[0-9]{6}")) {
			tList = getDao().getAccountTransaction(account);
		}else {
			throw new BusinessException("Enter customer# " + account+" is invalid");
		}
		return tList;
	}

	@Override
	public Customer getCustomer(String custInfo) throws BusinessException {
		Customer cust = null;
		if(custInfo.matches("BR[0-9]{4}[A-Z]{1}")) {
			 cust = getDao().getCustomer(custInfo);
		}else {
			throw new BusinessException("Enter customer# " + custInfo+" is invalid");
		}
		return cust;
	}
}
