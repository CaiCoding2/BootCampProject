package com.revature.bank.boimpl;

import com.revature.bank.bo.UserBo;
import com.revature.bank.dao.UserDao;
import com.revature.bank.daoimpl.UserDaoImpl;
import com.revature.bank.to.User;
import com.revature.exception.BusinessException;

public class UserBoImpl implements UserBo{
	private UserDao dao;
	@Override
	public boolean createUser(User user) throws BusinessException{
		if(user.getUsername().matches("^[0-9a-zA-Z]*$")) { // number and alphabet only
			user.setRole("c");
			return getDao().createUser(user);
		}else {
			throw new BusinessException("Entered username " + user.getUsername() + " is invalid ... Enter alphabet and number only");
		}
	}

	@Override
	public User authenticateUser(String user, String password) throws BusinessException{
		User users = null;
		if(user.matches("^[0-9a-zA-Z]*$")) { // number and alphabet only
			users =  getDao().authenticateUser(user, password);
		}else {
			throw new BusinessException("Entered username " + user + " is invalid ... Enter alphabet and number only");
		}
		return users;
	}

	public UserDao getDao() {
		if(dao == null) {
			dao = new UserDaoImpl();
		}
		return dao;
	}

}
