package com.bank.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dao.AccountTypeDao;
import com.bank.to.AccountType;
import com.bank.exception.BusinessException;
import com.bank.util.DBConnection;

public class AccountTypeDaoImpl implements AccountTypeDao {

	@Override
	public List<AccountType> getAccountTypeList() throws BusinessException {
		List<AccountType> aTypeList = new ArrayList<>();
		try(Connection connection = DBConnection.getConnection()){
			String sql ="Select * from AccountType";
			PreparedStatement pstm = connection.prepareStatement(sql);
			ResultSet set = pstm.executeQuery();
			while(set.next()) {
				AccountType aType = new AccountType(set.getInt("typeid"),set.getString("types"));
				aTypeList.add(aType);
			}
			
		} catch(ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal Invalid " + e);
		}
		return aTypeList;
	}

}
