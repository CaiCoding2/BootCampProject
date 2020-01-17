package com.robotbank.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.robotbank.dao.UserDao;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.User;
import com.robotbank.to.uc;
import com.robotbank.util.DBConnection;

import java.lang.ClassNotFoundException;



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

	@Override
	public uc check(String user) throws BusinessException {
		uc u = new uc();
		try(Connection connection = DBConnection.getConnection()){
			PreparedStatement pstm= connection.prepareStatement("select username from users where username=?");
			pstm.setString(1, user);
			ResultSet set = pstm.executeQuery();
			if(set.next()) {
				u.setUname(set.getString("username"));
			}else {
				u.setUname("#");
			}
		}catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Interal Error...Contact support" + e);
		}
		return u;
	}
}
