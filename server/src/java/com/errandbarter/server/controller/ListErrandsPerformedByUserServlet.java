/*
 * ListErrandsPerformedByUserServlet.java
 *
 * Created on March 19, 2009, 12:05 AM
 */

package com.errandbarter.server.controller;

import com.errandbarter.server.dao.DAOFactory;
import com.errandbarter.server.dao.ErrandDAO;
import com.errandbarter.server.dao.mock.MockDAOFactory;
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.xml.SimpleXMLWriter;
import com.errandbarter.server.xml.XMLWriter;
import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Zach
 * @version
 */
public class ListErrandsPerformedByUserServlet extends HttpServlet {

	private ErrandDAO errandDAO;
	private XMLWriter xmlWriter;

	public void init() {
		DAOFactory daoFactory = MockDAOFactory.getInstance();
		errandDAO = daoFactory.getErrandDAO();
		xmlWriter = SimpleXMLWriter.getInstance();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods. /listErrandsPerformed?user=gussoh 
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

		String user = request.getParameter("user");

		List<Errand> errandList = errandDAO.findByUserPerformed(user);
		String xmlOutput = xmlWriter.getXML(errandList);
		out.print(xmlOutput);

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
