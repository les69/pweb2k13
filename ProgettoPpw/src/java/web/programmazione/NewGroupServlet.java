/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import database.DbHelper;
import database.Group;
import database.User;
import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lorenzo
 */
public class NewGroupServlet extends HttpServlet {
private DbHelper helper;

    @Override
    public void init() throws ServletException {
        this.helper = (DbHelper) super.getServletContext().getAttribute("dbmanager");
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        ServletHelperClass.printHead(out,"New Group","MyGroupServlet","MyGroup List");
            
            out.println("<form action=\"NewGroupServlet\" method=\"post\">");
            out.println("Group name:<br/>");
            out.println("<input type=\"text\" style=\"width:300px;height:300px;\" name=\"groupname\" /><br/>");
            out.println("<input type=\"submit\" value=\"Submit\" />");
            
            ServletHelperClass.printFoot(out);
        }
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User usr = helper.getUser(ServletHelperClass.getUsername(request,false));
        String grpName = request.getParameter("groupname");
        Group tGrp = new Group();
        tGrp.setName(grpName);
        tGrp.setOwner(usr.getId());
        helper.addGroup(tGrp);
        
        helper.addUserToGroup(helper.getGroup(grpName), usr);
        response.sendRedirect("/ProgettoPpw/Admin/MyGroupServlet");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
