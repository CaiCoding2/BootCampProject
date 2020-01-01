package com.revature.bank.dao;

import com.revature.bank.to.User;
import com.revature.exception.BusinessException;

public interface UserDao {
	public boolean createUser(User user) throws BusinessException;
	public User authenticateUser(String user, String password) throws BusinessException;
}
