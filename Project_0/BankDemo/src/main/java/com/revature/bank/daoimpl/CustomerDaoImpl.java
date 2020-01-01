package com.revature.bank.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.bank.dao.CustomerDao;
import com.revature.bank.to.Customer;
import com.revature.exception.BusinessException;
import com.revature.util.DBConnection;

public class CustomerDaoImpl implements CustomerDao {

	@Override
	public Customer addCustInfo(Customer cust) throws BusinessException {
		try(Connection connection = DBConnection.getConnection()){
			String sql  = "{call CUSTOMERID(?,?,?,?,?,?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(2, cust.getFname());
			callableStatement.setString(3, cust.getLname());
			callableStatement.setDate(4, new java.sql.Date(cust.getDob().getTime()));;
			callableStatement.setString(5, cust.getGender());
			callableStatement.setInt(6, cust.getUserId());
			callableStatement.registerOutParameter(1,java.sql.Types.VARCHAR);
			
			callableStatement.execute();
			cust.setCID(callableStatement.getString(1));
			
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal Error "+ e);
		}
		return cust;
	}

	@Override
	public Customer getCustInfo(int id) throws BusinessException {
		Customer cust = new Customer();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "select * from customer where userid = ?";
			CallableStatement ps = connection.prepareCall(sql);
			ps.setInt("userid", id);;
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				cust.setCID(set.getString(1));
				cust.setFname(set.getString(2));
				cust.setLname(set.getString(3));
				cust.setDob(set.getDate(4));
				cust.setGender(set.getString(5));
				cust.setUserId(set.getInt(6));
			}
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal Error "+ e);
		}
		return cust;
	}
	
}
