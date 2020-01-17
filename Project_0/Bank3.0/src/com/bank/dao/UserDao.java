package com.bank.dao;

import com.bank.to.User;
import com.bank.exception.BusinessException;

public interface UserDao {
	public boolean createUser(User user) throws BusinessException;
	public User authenticateUser(String user, String password) throws BusinessException;
}
