package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robotbank.bo.AccountBo;
import com.robotbank.boimpl.AccountBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/transcation")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    //perform task depend on the transaction type 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Account account = new Account();
		AccountBo bo = new AccountBoImpl();
		RequestDispatcher rd = null;
		try {
			account.setAccountNumber(request.getParameter("accNum"));
			account.setBalance(Integer.parseInt(request.getParameter("balance")));
			account.setaType(Integer.parseInt(request.getParameter("trans")));
			boolean success = false;
			if(account.getaType() == 1) { // transfer	
				success = bo.deposit(account.getAccountNumber(), account.getBalance());
			}else if(account.getaType() == 2) {
				success = bo.withdrawal(account.getAccountNumber(), account.getBalance());
			}else {
				String recipient = request.getParameter("recipient");
				success = bo.transfer(account.getAccountNumber(), recipient, account.getBalance());
			}
			if(success) {
				out.print("success");
			}
		}catch(BusinessException e) {
			rd= request.getRequestDispatcher("customer.html");
			rd.include(request, response);
			out.print("<center><span style='color:red;'>" + e.getMessage() + "</span></center>");
		}
	}
	

}
