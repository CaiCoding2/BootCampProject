package com.bank.bo;

import com.bank.dao.UserDao;
import com.bank.to.User;
import com.bank.exception.BusinessException;

public interface UserBo {
	public boolean createUser(User user) throws BusinessException;
	public User authenticateUser(String user, String password) throws BusinessException;
	public UserDao getDao();
}
