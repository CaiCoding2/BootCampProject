package com.revature.bank.to.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.revature.bank.to.*;

class TestModelsObjects {
	Account acc = new Account();
	AccountType at = new AccountType();
	Customer cust = new Customer();
	User users = new User();
	Status status = new Status();
	Employee employee = new Employee();
	TransHistory trans = new TransHistory();
	TransType	tType = new TransType();
	@Test
	public void testAccount() { // account and accounttpye
		at.setTypeId(5);
		at.setTypes("bank");
		acc.setAccountNumber("BR0000");
		acc.setBalance(50);
		acc.setCustInfo("c0000");
		acc.setStatusId(1);
		acc.setaType(at);
		assertEquals(acc.getAccountNumber(),"BR0000");
		assertEquals(acc.getBalance(),50);
		assertEquals(acc.getCustInfo(),"c0000");
		assertEquals(acc.getStatusId(),1);
		assertEquals(acc.getaType().getTypes(),"bank");
		assertEquals(acc.getaType().getTypeId(),5);
	}
	@Test 
	public void testCustomer() { // customer
		Date date = new Date();
		cust.setCID("money");
		cust.setUserId(10);
		cust.setFname("Fed");
		cust.setLname("Ton");
		cust.setGender("o");
		cust.setDob(date);
		assertEquals(cust.getCID(), "money");
		assertEquals(cust.getUserId(), 10);
		assertEquals(cust.getFname(), "Fed");
		assertEquals(cust.getLname(), "Ton");
		assertEquals(cust.getGender(), "o");
		assertEquals(cust.getDob(), date);
	}
	@Test
	public void testEmployee() { // employee
		employee.setEID("e2222");
		employee.setFname("Barn");
		employee.setLname("ing");
		assertEquals(employee.getEID(), "e2222");
		assertEquals(employee.getFname(), "Barn");
		assertEquals(employee.getLname(), "ing");
	}
	@Test
	public void testTransHistory() { //transHistory and transType
		Date date = new Date();
		tType.setTransId(1);
		tType.setTransType("Transfer");
		trans.setAccountNum("a0101");
		trans.setAmount(500);
		trans.setHID(1);
		trans.setLog(date);
		trans.setTransFrom("Kroger");
		trans.setTransTo("BOA");
		trans.settType(tType);
		assertEquals(trans.getAccountNum(),"a0101");
		assertEquals(trans.getAmount(),500);
		assertEquals(trans.getHID(),1);
		assertEquals(trans.getLog(),date);
		assertEquals(trans.getTransFrom(), "Kroger");
		assertEquals(trans.getTransTo(), "BOA");
		assertEquals(trans.gettType().getTransType(), "Transfer");
		assertEquals(trans.gettType().getTransId(), 1);
	}
	@Test
	public void testUser() {// user
		users.setUserId(5);
		users.setUsername("demo");
		users.setPassword("test");
		users.setRole("admin");
		assertEquals(users.getUserId(),5);
		assertEquals(users.getUsername(),"demo");
		assertEquals(users.getPassword(), "test");
		assertEquals(users.getRole(), "admin");
	}
	@Test
	public void testStatus() {
		status.setStatus("pending");
		status.setStatusId(55);
		assertEquals(status.getStatus(), "pending");
		assertEquals(status.getStatusId(),55);
	}

}
