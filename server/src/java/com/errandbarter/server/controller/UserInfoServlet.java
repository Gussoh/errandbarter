/*
 * UserInfoServlet.java
 *
 * Created on March 19, 2009, 12:05 AM
 */

package com.errandbarter.server.controller;

import com.errandbarter.server.dao.DAOFactory;
import com.errandbarter.server.dao.UserDAO;
import com.errandbarter.server.dao.mock.MockDAOFactory;
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
public class UserInfoServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private XMLWriter xmlWriter;
    
    public void init() {
        DAOFactory daoFactory = MockDAOFactory.getInstance();
        userDAO = daoFactory.getUserDAO();
        xmlWriter = SimpleXMLWriter.getInstance();
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * /whois?user=gussoh 
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String id = request.getParameter("user");
        User user = userDAO.findById(id);
        String xmlOutput = xmlWriter.getXML(user);
        out.print(xmlOutput);
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
