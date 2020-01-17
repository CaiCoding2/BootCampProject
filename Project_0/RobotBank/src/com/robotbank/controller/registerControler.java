package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robotbank.bo.AccountBo;
import com.robotbank.bo.CustomerBo;
import com.robotbank.bo.UserBo;
import com.robotbank.boimpl.AccountBoImpl;
import com.robotbank.boimpl.CustomerBoImpl;
import com.robotbank.boimpl.UserBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;
import com.robotbank.to.User;
import com.robotbank.to.uc;

/**
 * Servlet implementation class registerControler
 */
@WebServlet("/register")
public class registerControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerControler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String user = request.getParameter("username");
		
		UserBo bo = new UserBoImpl();
		PrintWriter out = response.getWriter();
		try {
			uc u = bo.check(user);
			if(u.getUname().equals("#")) {
				out.print("#");
			}
			else {
				out.print("@");
			}
		}catch(BusinessException e) {
			out.print(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String user = request.getParameter("username");
		String password = request.getParameter("Password");
		String fname = request.getParameter("FirstName");
		String lname = request.getParameter("LastName");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(user + " " + password);
		System.out.println(fname + " " + lname);
		System.out.println(gender + " " + dob);
		System.out.println(type);
		if(user =="" |password==""| fname==""|lname ==""| dob==""|gender=="") {
			RequestDispatcher rd = request.getRequestDispatcher("register.html");
			rd.include(request, response);
			System.out.println("empty");
		}else {
			UserBo uBo = new UserBoImpl();
			CustomerBo cBo = new CustomerBoImpl();
			AccountBo aBo = new AccountBoImpl();
			User users = new User();
			Customer customer  = new Customer();
			PrintWriter out = response.getWriter();
			
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				sdf.setLenient(false);
				customer.setDob(sdf.parse(dob));
			} catch (ParseException e) {
					RequestDispatcher rd = request.getRequestDispatcher("register.html");
					rd.include(request, response);
					out.print("date is not right format");
			}
			customer.setFname(fname);
			customer.setLname(lname);
			customer.setGender(gender);
			users.setUsername(user);
			users.setPassword(password);
			try {
				uBo.createUser(users);
				users = uBo.authenticateUser(user, password);
				customer.setUserId(users.getUserId());
				customer.setGender(gender);
				customer = cBo.addCustInfo(customer);
				Account acc = new Account();
				acc.setaType(type);
				acc.setBalance(0);
				acc.setCustInfo(customer.getCID());
				acc.setStatusId(1);
				acc=aBo.newAccount(acc);
				if(acc.getAccountNumber() != null) {
					response.sendRedirect("login.html");
				}
			}catch(BusinessException e) {}
			
			
			
			
		}
	
		
		
	}

}
