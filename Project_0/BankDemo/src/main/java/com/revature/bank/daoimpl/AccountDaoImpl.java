package com.revature.bank.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.bank.dao.AccountDao;
import com.revature.bank.to.Account;
import com.revature.bank.to.AccountType;
import com.revature.bank.to.Customer;
import com.revature.bank.to.TransHistory;
import com.revature.bank.to.TransType;
import com.revature.exception.BusinessException;
import com.revature.util.DBConnection;

public class AccountDaoImpl implements AccountDao {

	@Override
	public Account newAccount(Account acc) throws BusinessException {
		try(Connection connection = DBConnection.getConnection()){
			String sql = "{call ACCOUNTNUM(?,?,?,?,?)}";
			CallableStatement call = connection.prepareCall(sql);
			call.setDouble(2, acc.getBalance());
			call.setInt(3, acc.getaType().getTypeId());
			call.setInt(4,acc.getStatusId());
			call.setString(5,acc.getCustInfo());
			call.registerOutParameter(1, java.sql.Types.VARCHAR);
			
			call.execute();
			acc.setAccountNumber(call.getString(1));
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return acc;
	}

	@Override
	public boolean deposit(String account,double amount,Date date) throws BusinessException {
		int i = 0;
		double total = 0;
		boolean found = false;
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select balance from account where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				total = set.getDouble("balance") + amount;
				found = true;
			}
			if(found) {
				sql = "update account set balance = ? where accountnumber = ?";
				ps = connection.prepareStatement(sql);
				ps.setDouble(1, total);
				ps.setString(2, account);
				i = ps.executeUpdate();
				if(i == 1) {
					//add to transaction;
					sql = "insert into transaction(hid,accountnumber,amount,transtype,datelog) values(null,?,?,?,?) ";
					ps = connection.prepareStatement(sql);
					ps.setString(1, account);
					ps.setDouble(2, amount);
					ps.setInt(3, 1);
					ps.setDate(4, new java.sql.Date(date.getTime()));
					return ps.execute();
				}
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return false;
	}

	@Override
	public boolean withdrawal(String account,double amount, Date date) throws BusinessException {
		int i = 0;
		double total = 0;
		boolean found = false;
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select balance from account where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				total = set.getDouble("balance") - amount;
				if(total < 0) {
					throw new BusinessException("Insufficient funds");
				} 
				found = true;
			}
			if(found) {
				sql = "update account set balance = ? where accountnumber = ?";
				ps = connection.prepareStatement(sql);
				ps.setDouble(1, total);
				ps.setString(2, account);
				i = ps.executeUpdate();
				if(i == 1) {
					//add to transaction;
					sql = "insert into transaction(hid,accountnumber,amount,transtype,datelog) values(null,?,?,?,?) ";
					ps = connection.prepareStatement(sql);
					ps.setString(1, account);
					ps.setDouble(2, amount);
					ps.setInt(3, 2);
					ps.setDate(4, new java.sql.Date(date.getTime()));
					return ps.execute();
				}
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return false;
	}

	@Override
	public boolean transfer(String transferFrom, String transferTo, double amount, Date date) throws BusinessException {
		double total = 0;
		int i = 0;
		boolean success = false;
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select balance from account where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, transferFrom);
			ResultSet set = ps.executeQuery();
			if(set.next()) {
				total = set.getDouble("balance") - amount;
				if(total < 0) { // check account balance to withdrawal
					throw new BusinessException("Insufficient funds");
				}else {
					sql = "select statusid, balance from account where accountnumber = ?";
					ps = connection.prepareStatement(sql);
					ps.setString(1,transferTo);
					set = ps.executeQuery();
					if(set.next()) {
						if(set.getInt("statusid") == 3) {
							double balance = set.getDouble("balance"); // update recipient balance 
							sql = "update account set balance = ? where accountnumber = ?";
							ps = connection.prepareStatement(sql);
							ps.setDouble(1, amount + balance);
							ps.setString(2, transferTo);
							i = ps.executeUpdate();
							if(i == 1) { // insert recipient transaction log
								sql = "insert into transaction(hid,accountnumber,amount,transtype,transfrom,transto,datelog) values(null,?,?,?,?,?,?) ";
								ps = connection.prepareStatement(sql);
								ps.setString(1, transferTo);
								ps.setDouble(2, amount);
								ps.setInt(3, 3);
								ps.setString(4, transferFrom);
								ps.setString(5, transferTo);
								ps.setDate(6, new java.sql.Date(date.getTime()));
								ps.executeUpdate();
								success = true;
							}
						}else {
							throw new BusinessException("Account is not active can not transfer");
						}
					}else {
						throw new BusinessException("Tranfer account has not been found or not a active account");
					}
					
				}
			}
			
			if(success) {
				i = 0;
				sql = "update account set balance=? where accountnumber=?";
				ps = connection.prepareStatement(sql);
				ps.setDouble(1, total);
				ps.setString(2, transferFrom);
				i = ps.executeUpdate();
				if(i == 1) {
					sql = "insert into transaction(hid,accountnumber,amount,transtype,transfrom,transto,datelog) values(null,?,?,?,?,?,?) ";
					ps = connection.prepareStatement(sql);
					ps.setString(1, transferFrom);
					ps.setDouble(2, amount);
					ps.setInt(3, 3);
					ps.setString(4, transferFrom);
					ps.setString(5, transferTo);
					ps.setDate(6, new java.sql.Date(date.getTime()));
					return ps.execute();
	
				}
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return false;
	}

	@Override
	public List<Account> getAllAccount(String custId) throws BusinessException  {
		List<Account> aList = new ArrayList<>();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from account where custinfo = ?";
			PreparedStatement ps = connection.prepareCall(sql);
			ps.setString(1, custId);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				Account acc = new Account();
				acc.setAccountNumber(set.getString("accountnumber"));
				acc.setBalance(set.getDouble("balance"));
				if(set.getInt("typeid") == 1) {
					acc.setaType(new AccountType(1,"checking"));
				}else {
					acc.setaType(new AccountType(1,"saving"));
				}
				acc.setStatusId(set.getInt("statusid"));
				acc.setCustInfo(set.getString("custinfo"));
				aList.add(acc);
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		 return aList;
	}

	@Override
	public boolean checkAccount(String custId) throws BusinessException {
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from account where custinfo = ?";
			PreparedStatement ps = connection.prepareCall(sql);
			ps.setString(1, custId);
			ResultSet set = ps.executeQuery();
			int count = 0;
			while(set.next()) {
				count++;
			}
			if(count < 5) {
				return true;
			}else {
				return false;
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
	}

	@Override
	public List<TransHistory> getAccountTransaction(String account) throws BusinessException {
		List<TransHistory> transList = new ArrayList<TransHistory>();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from transaction where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				TransHistory trans = new TransHistory();
				trans.setHID(set.getInt("HID"));
				trans.setAccountNum(set.getString("ACCOUNTNUMBER"));
				trans.setAmount(set.getDouble("AMOUNT"));
				if(set.getInt("TRANSTYPE")  == 1){
					trans.settType(new TransType(set.getInt("TRANSTYPE") ,"Deposit"));
				}else if(set.getInt("transtype")  == 2){
					trans.settType(new TransType(set.getInt("TRANSTYPE") ,"Withdrawal"));
				}else {
					trans.settType(new TransType(set.getInt("TRANSTYPE") ,"Transfer"));
				}
				trans.setTransFrom(set.getString("TRANSFROM"));
				trans.setTransTo(set.getString("TRANSTO"));
				trans.setLog(set.getDate("DATELOG"));
				transList.add(trans);
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return transList;
	}

	@Override
	public Customer getCustomer(String custInfo) throws BusinessException {
		Customer cust = new Customer();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from customer where cid =? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, custInfo);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				cust.setCID(custInfo);
				cust.setFname(set.getString("fname"));
				cust.setLname(set.getString("lname"));
				cust.setDob(set.getDate("dob"));
				cust.setGender(set.getString("gender"));
				cust.setUserId(set.getInt("userid"));
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return cust;
	}
}
