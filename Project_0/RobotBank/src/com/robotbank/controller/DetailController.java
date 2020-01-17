package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.robotbank.to.Customer;
import com.robotbank.to.Employee;
import com.robotbank.to.User;

/**
 * Servlet implementation class DetailController
 */
@WebServlet("/detail")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Gson gson=new Gson();
		if(session != null) {
			User user = (User) session.getAttribute("users");
			PrintWriter out=response.getWriter();
			if(user.getRole().equals("c")) {
				Customer cust = (Customer) session.getAttribute("customer");
				out.print(gson.toJson(cust));
			}else {
				Employee employee = (Employee) session.getAttribute("employee");
				out.print(gson.toJson(employee));
			}
		}
	}

}
