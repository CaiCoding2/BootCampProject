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
import com.robotbank.to.TransHistory;

/**
 * Servlet implementation class log
 */
@WebServlet("/log")
public class log extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public log() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		HttpSession session = request.getSession(false);
		PrintWriter out=response.getWriter();
		Gson gson = new Gson();
		AccountBo abo = new AccountBoImpl();
		String aNum = request.getParameter("accNum");
		try {
			List<TransHistory> tList = abo.getAccountTransaction(aNum);
			for(int i = 0; i< tList.size();i++) {
				out.print(gson.toJson(tList.get(i).getAccountNum()));
				out.print(gson.toJson(tList.get(i).getAmount()));
				if(tList.get(i).gettType().equals("1")) {
					out.print(gson.toJson("Deposit"));
				}else if(tList.get(i).gettType().equals("2")) {
					out.print(gson.toJson("Withdrawal"));
				}else {
					out.print(gson.toJson("Transfer"));
				}
				out.print(gson.toJson(tList.get(i).getLog()));
			}
			
		}catch (BusinessException e) {
			out.print(e.getMessage());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
