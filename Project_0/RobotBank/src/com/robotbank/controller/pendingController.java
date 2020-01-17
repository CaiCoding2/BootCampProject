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
import com.robotbank.bo.EmployeeBo;
import com.robotbank.boimpl.EmployeeBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.User;

/**
 * Servlet implementation class pendingController
 */
@WebServlet("/pending")
public class pendingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pendingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //get all pending account
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("users");
		EmployeeBo ebo= new EmployeeBoImpl();
		PrintWriter out=response.getWriter();
		try {
			if(user.getRole().equals("e")) {
				List<Account> pList=ebo.getAllPendingAccount();
				out.print(gson.toJson(pList));
			}
			
		} catch (BusinessException e) {
			out.print(e.getMessage());
		}
		
		
	}
}
