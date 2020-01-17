package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.robotbank.bo.CustomerBo;
import com.robotbank.bo.EmployeeBo;
import com.robotbank.boimpl.CustomerBoImpl;
import com.robotbank.boimpl.EmployeeBoImpl;
import com.robotbank.boimpl.UserBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Customer;
import com.robotbank.to.Employee;
import com.robotbank.to.User;

/**
 * Servlet implementation class loginContoller
 */
@WebServlet(urlPatterns="/login")
public class LoginContoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginContoller() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //login control
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//response
		PrintWriter out = response.getWriter();
		//
		RequestDispatcher rd = null;
		User user = new User();
		try {
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user = new UserBoImpl().authenticateUser(user.getUsername(), user.getPassword());
			if(user.getUserId() != 0) {
	
				//create session
				HttpSession session = request.getSession();
				session.setAttribute("users", user);
				session.setMaxInactiveInterval(300);
				//System.out.println(user.getUserId() + " " + user.getUsername());
				//redirect them to different page base 
				if(user.getRole().equals("e")) {
					EmployeeBo ebo = new EmployeeBoImpl();
					Employee employee = new Employee();
					employee = ebo.getEmployeeInfo(user.getUserId());
					session.setAttribute("employee", employee);
					response.sendRedirect("staff");
				}else {
					CustomerBo co = new CustomerBoImpl();
					Customer customer= new Customer();
					customer = co.getCustInfo(user.getUserId());
					session.setAttribute("customer", customer);
					response.sendRedirect("client");
				}
			}
			
		} 
		catch(BusinessException e)
		{
			//get the login page and add the response
			//request.setAttribute("loginError", "<center><span style='color:red;'>" + e.getMessage() + "</span></center>");
			rd= request.getRequestDispatcher("login.html");
			rd.forward(request, response);
			out.print("<center><span style='color:red;'>" + e.getMessage() + "</span></center>");
		}
	}

}
