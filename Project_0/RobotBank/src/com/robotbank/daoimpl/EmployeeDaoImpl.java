package com.robotbank.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.robotbank.dao.EmployeeDao;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Employee;
import com.robotbank.to.TransHistory;
import com.robotbank.to.TransType;
import com.robotbank.util.DBConnection;



public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public List<Account> getAllPendingAccount() throws BusinessException {
		List<Account> pendingList = new ArrayList<>();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from account where statusid = 1";
			PreparedStatement ps = connection.prepareCall(sql);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				Account acc = new Account();
				acc.setAccountNumber(set.getString("accountnumber"));
				acc.setBalance(set.getDouble("balance"));
				if(set.getInt("typeid") == 1) { acc.setaType(1);}
				else {acc.setaType(2);}
				acc.setStatusId(set.getInt("statusid"));
				acc.setCustInfo(set.getString("custinfo"));
				pendingList.add(acc);
			}
			if(pendingList.size() == 0) {
				throw new BusinessException("There is no pending account to be process.");
			}
			
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal Error" + e);
		}
		return pendingList;
	}

	@Override
	public boolean proccessAccount(Account account) throws BusinessException {
		try(Connection connection = DBConnection.getConnection()){
			String sql  = "update account set statusid=? where accountnumber=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, account.getStatusId());
			ps.setString(2, account.getAccountNumber());
			ps.executeUpdate();
			
			
			return true;
			
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal Error" + e);
		}
	}

	@Override
	public Account searchByAccountNumber(String aNumber) throws BusinessException {
		Account account = null;
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from account where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, aNumber);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				account = new Account();
				account.setAccountNumber(aNumber);
				account.setBalance(set.getDouble("balance"));
				account.setaType(set.getInt("typeid"));
				account.setStatusId(set.getInt("statusid"));
				account.setCustInfo(set.getString("custinfo"));
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error " + e);
		}
		return account;
	}

	@Override
	public List<TransHistory> viewAllAccountTransaction(String aNumber)throws BusinessException {
		List<TransHistory> transList = new ArrayList<TransHistory>();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from transaction where accountnumber = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, aNumber);
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
	public Employee getEmployeeInfo(int id) throws BusinessException {
		Employee emp = new Employee();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from employee where userid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				emp.setEID(set.getString("EID"));
				emp.setFname(set.getString("fname"));
				emp.setLname(set.getString("lname"));
				emp.setUserId(id);
			}
		}catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error.....Contact support");
		}
		return emp;
	}

}
