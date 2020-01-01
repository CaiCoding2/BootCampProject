package com.revature.bank.bo;

import com.revature.bank.dao.UserDao;
import com.revature.bank.to.User;
import com.revature.exception.BusinessException;

public interface UserBo {
	public boolean createUser(User user) throws BusinessException;
	public User authenticateUser(String user, String password) throws BusinessException;
	public UserDao getDao();
}
