package com.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.boimpl.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		User user = new User();
		RequestDispatcher rd = null;
		try {
			System.out.println("try");
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user = new UserBoImpl().authenticateUser(user.getUsername(), user.getPassword());
			if(user.getUserId() != 0) {
				rd = request.getRequestDispatcher("index.html");
			}
			
		} catch(BusinessException e) {
			rd = request.getRequestDispatcher("login.html");
			rd.include(request, response);
			out.print("<center><span style='color:red;'>" + e.getMessage() + "</span></center>");
		}
		
	
	}

}
