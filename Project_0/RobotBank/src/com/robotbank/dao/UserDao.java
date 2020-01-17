package com.robotbank.dao;

import com.robotbank.exception.BusinessException;
import com.robotbank.to.User;
import com.robotbank.to.uc;

public interface UserDao {
	public boolean createUser(User user) throws BusinessException;
	public User authenticateUser(String user, String password) throws BusinessException;
	public uc check(String user) throws BusinessException;
}
