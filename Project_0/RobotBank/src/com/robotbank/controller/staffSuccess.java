package com.robotbank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.robotbank.bo.AccountBo;
import com.robotbank.bo.EmployeeBo;
import com.robotbank.boimpl.AccountBoImpl;
import com.robotbank.boimpl.EmployeeBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.Customer;
import com.robotbank.to.TransHistory;
import com.robotbank.to.User;


/**
 * Servlet implementation class staffSuccess
 */
@WebServlet("/staff")
public class staffSuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public staffSuccess() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    /**
   	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   	 */
   	protected void doGet(HttpServletRequest request, HttpServletResponse response,String search) throws ServletException, IOException {
   		response.setContentType("text/html");
   		//response.setCharacterEncoding("UTF-8");
   		EmployeeBo bo = new EmployeeBoImpl();
   		AccountBo abo = new AccountBoImpl();
   	
   		Gson gson = new Gson();
   		PrintWriter out = response.getWriter();
   		if(search =="") {
   			RequestDispatcher rd10 = request.getRequestDispatcher("staff.html");
	   	   	rd10.include(request, response);
   		}else {
   			try {
   	   			System.out.println("enter ");
   	   			RequestDispatcher rd1 = request.getRequestDispatcher("staffheader.html");
   	   	   		rd1.include(request, response);
   				Account account =bo.searchByAccountNumber(search);
   				Customer cust = abo.getCustomer(account.getCustInfo());
   				List<TransHistory> tList = abo.getAccountTransaction(account.getAccountNumber());
   				Customer customer = new Customer();
   				customer = abo.getCustomer(account.getCustInfo());
   				out.print("<div class='card container'>");
   				out.print("<br><center><h1> Searching Account "+account.getAccountNumber()+"</h1></center><br>");
   				out.print("<p> Customer ID:  " +customer.getCID()+"</p><br>");
   				out.print("<p>Name: "+ customer.getFname() + " " +customer.getLname()+"</p><br>");
   				out.print("<p> Date of Birth: "+customer.getDob() +"</p><br>");
   				if(customer.getGender().equals("m")) {
   					out.print("<p> Gender: Male</p><br>");
   				}else if(customer.getGender().equals("f")) {
   					out.print("<p> Gender: Female</p><br>");
   				}else {
   					out.print("<p> Gender: Other</p><br>");
   				}
   				
   				
   				out.print("<br><center><h1> Transaction Log</h1></center><br>");
   				if(tList.size() == 0) {}
   				else {
   					out.print("<table class=\"striped\"><thead><tr>");
   					out.print("<th>Account</th>");
   					out.print("<th>Task</th>");
   					out.print("<th>Amount</th>");
   					out.print("<th>Date</th>");
   					out.print("</tr></thead><tbody> ");
   					for(int i = 0; i< tList.size();i++) {
   						out.print("<tr>");    
   						out.print("<td>"+tList.get(i).getAccountNum() +"</td>");
   						out.print("<td>"+tList.get(i).gettType().getTransType() +"</td>");
   						out.print("<td>"+"$"+tList.get(i).getAmount() +"</td>");
   						out.print("<td>"+tList.get(i).getLog() +"</td>");
   						out.print("</tr>"); 		
   					}
   					out.print("</tbody></table><br> ");
   				}
   				out.print("</div>");
   				
   				RequestDispatcher rd2 = request.getRequestDispatcher("stafffooter2.html");
   	   	   		rd2.include(request, response);
   			} catch (BusinessException e) {
   	   	   		out.print("<center><span style='color:red;'>"+e.getMessage()+"</span></center>");
   				RequestDispatcher rd4 = request.getRequestDispatcher("stafffooter.html");
   	   	   		rd4.include(request, response);
   			}
   		}
   		
   		//bo.searchByAccountNumber();
   	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String account = request.getParameter("accounts");
		String pa = request.getParameter("process");
		PrintWriter out = response.getWriter();
		EmployeeBo ebo = new EmployeeBoImpl();
		Account acc = new Account();
		acc.setAccountNumber(account);
		if(pa.equals("approve")) {
			acc.setStatusId(3);
		}else {
			acc.setStatusId(2);
		}
		try {
			RequestDispatcher rd4 = request.getRequestDispatcher("staff.html");
   	   		rd4.include(request, response);
			boolean success =ebo.proccessAccount(acc);
		}catch(BusinessException e) {
			RequestDispatcher rd6 = request.getRequestDispatcher("staffheader.html");
   	   		rd6.include(request, response);
			out.print(e.getMessage());
			RequestDispatcher rd7 = request.getRequestDispatcher("stafffooter.html");
   	   		rd7.include(request, response);
		}
		//Account account=gson.fromJson(request.getReader(), Account.class);
		//System.out.println(account.getAccountNumber());
	}
 
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		if(session!= null) {
			User user = (User)session.getAttribute("users");
			if(user.getRole().equals("e")) {
				String search=request.getParameter("searchaccount");
				String account = request.getParameter("accounts");
				if(search!=null) {
					doGet(request,response,search);
				}else if(account !=null){
					//System.out.println("second");
					doPost(request,response);
					
				}else {
					//System.out.println("checking");
						RequestDispatcher rd = request.getRequestDispatcher("staff.html");
						rd.include(request, response);
				}
			}
			
		}else {
			response.sendRedirect("login.html");
		}
		System.out.println("staff server");
	}

}
