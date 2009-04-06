
/*
 * ViewErrandServlet.java
 *
 * Created on March 19, 2009, 12:05 AM
 */

package com.errandbarter.server.controller;

import com.errandbarter.server.dao.AnswerDAO;
import com.errandbarter.server.dao.DAOFactory;
import com.errandbarter.server.dao.ErrandDAO;
import com.errandbarter.server.dao.UserDAO;
import com.errandbarter.server.dao.mock.MockDAOFactory;
import com.errandbarter.server.entity.Answer;
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.entity.User;
import com.errandbarter.server.xml.SimpleXMLWriter;
import com.errandbarter.server.xml.XMLWriter;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * @author Zach
 * @version
 */
public class RewardServlet extends HttpServlet {

	private AnswerDAO answerDAO;
	private ErrandDAO errandDAO;
        private UserDAO userDAO;
	private XMLWriter xmlWriter;

	public void init() {
		DAOFactory daoFactory = MockDAOFactory.getInstance();
		answerDAO = daoFactory.getAnswerDAO();
                errandDAO = daoFactory.getErrandDAO();
                userDAO = daoFactory.getUserDAO();
		xmlWriter = SimpleXMLWriter.getInstance();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods. /reward?id=2145442
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			int answerID = Integer.parseInt(request.getParameter("id"));
                        
                        Answer answer = answerDAO.findById(answerID);
                        Errand errand = errandDAO.findById(answer.getErrandID());
                        User owner = userDAO.findById(errand.getUser());
                        User performer = userDAO.findById(answer.getUserID());                        
                        answer.setPointsRewarded(errand.getPrice());
                        //update balance of owner and performer
                        owner.setBalance(owner.getBalance() - errand.getPrice());
                        performer.setBalance(performer.getBalance() + errand.getPrice());
                        
                        answerDAO.makePersistent(answer);
                        userDAO.makePersistent(owner);
                        userDAO.makePersistent(performer);
                        
                        String xmlOutput = xmlWriter.getXML(new Response(Response.STATUS_OK, "Answer rewarded"));
			
                        out.print(xmlOutput);		
		} catch(NumberFormatException e) {
			e.printStackTrace();
			out.println(xmlWriter.getXML(new Response(Response.STATUS_ERROR, "Request parameters are not in correct format")));
		}
		out.close();
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}
	// </editor-fold>
}