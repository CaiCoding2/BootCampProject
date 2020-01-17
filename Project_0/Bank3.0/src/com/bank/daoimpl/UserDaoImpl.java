package com.bank.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

import com.bank.dao.UserDao;
import com.bank.to.User;
import com.bank.exception.BusinessException;
import com.bank.util.DBConnection;

public class UserDaoImpl implements UserDao {
	@Override
	public boolean createUser(User user) throws BusinessException{
		//create user 
		try(Connection connection = DBConnection.getConnection()){
			PreparedStatement pstm= connection.prepareStatement("insert into users(username,password,role) values(?,?,?)" );
			pstm.setString(1, user.getUsername());
			pstm.setString(2, user.getPassword());
			pstm.setString(3, user.getRole());
			int i = pstm.executeUpdate();
			if(i == 1) {
				return true;
			}
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Username has been used, please use another username.");
		}
		return false;
	}

	@Override
	public User authenticateUser(String user, String password) throws BusinessException {
		User users = null;
		try(Connection connection = DBConnection.getConnection()){
			PreparedStatement pstm= connection.prepareStatement("select * from users where username=? and password =? ");
			pstm.setString(1,user);
			pstm.setString(2, password);
			ResultSet set = pstm.executeQuery();
			if(set.next()) {
				users = new User();
				users.setUserId(set.getInt(1));
				users.setUsername(set.getString(2));
				users.setPassword(set.getString(3));
				users.setRole(set.getString(4));
			} else {
				throw new BusinessException("Username and Password does not match.\n");
			}
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error...Contact support" + e);
		}
		return users;
	}
}
