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
import com.robotbank.boimpl.AccountBoImpl;
import com.robotbank.exception.BusinessException;
import com.robotbank.to.Account;
import com.robotbank.to.TransHistory;
import com.robotbank.to.User;

/**
 * Servlet implementation class clientSuccess
 */
@WebServlet("/client")
public class clientSuccess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public clientSuccess() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response,String aNum) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		AccountBo abo = new AccountBoImpl();
		try {
			List<TransHistory> tList = abo.getAccountTransaction(aNum);
			RequestDispatcher rd =request.getRequestDispatcher("head.html");
			rd.include(request, response);
			out.print("<div class='card container'>");
			out.print("<br><center><h1> Transaction Log</h1></center><br>");
			if(tList.size() ==0) {
			}else {
				
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
				out.print("</tbody></table><br>");
			}
			out.print("</div>");
			RequestDispatcher rd2 =request.getRequestDispatcher("footer.html");
			rd2.include(request, response);
		}catch (BusinessException e) {
			RequestDispatcher rd =request.getRequestDispatcher("head.html");
			rd.include(request, response);
			out.print(e.getMessage());
			RequestDispatcher rd2 =request.getRequestDispatcher("footer.html");
			rd2.include(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Account account = new Account();
		AccountBo bo = new AccountBoImpl();
		try {
			
			RequestDispatcher rd31 =request.getRequestDispatcher("head.html");
			rd31.include(request, response);
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
			
			if(!success) {
				out.print("<br><center><span style='color:blue;'> Task has been process by the system.</span></center><br>");
			}
			
			RequestDispatcher rd4 =request.getRequestDispatcher("footer.html");
			rd4.include(request, response);
			
			//rd3 =request.getRequestDispatcher("client.html");
			//rd3.include(request, response);
		}catch(BusinessException e) {
			
			out.print("<br><center><span style='color:red;'>" + e.getMessage() + "</span></center><br>");
			RequestDispatcher rd6 =request.getRequestDispatcher("footer.html");
			rd6.include(request, response);
		}
	}
    
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		if(session!= null) {
			String aNum = request.getParameter("accNum");
			String transfer = request.getParameter("trans");
			User user = (User) session.getAttribute("users");
			if(user.getRole().equals("c")) {
				if(aNum != null) {
					if(transfer != null) {
						doPost(request,response);
					}else {
						doGet(request,response,aNum);
					}	
				}else {
					RequestDispatcher rd = request.getRequestDispatcher("client.html");
					rd.include(request, response);
					response.setContentType("text/html");
				}
				
			}
			
		}else {
			response.sendRedirect("login.html");
		}	
		System.out.println("client server");
	}
}
