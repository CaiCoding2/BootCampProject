package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.robotbank.bo.AccountBo;
import com.robotbank.boimpl.AccountBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;

/**
 * Servlet implementation class viewAccountController
 */
@WebServlet("/view")
public class viewAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewAccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		//System.out.println("getting all account");
		PrintWriter out=response.getWriter();
		HttpSession session =request.getSession(false);
		if(session != null) {
			Customer cust = (Customer) session.getAttribute("customer");
			AccountBo abo = new AccountBoImpl();
			try {
				List<Account> alist  = abo.getAllAccount(cust.getCID());
				//System.out.println(alist.get(0).getAccountNumber());
				out.print(gson.toJson(alist));
			}catch (BusinessException e) {
				out.print(e.getMessage());
			}
			
		}
	}

}
