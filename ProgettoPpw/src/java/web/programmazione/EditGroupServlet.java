/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import database.DbHelper;
import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo
 */
public class EditGroupServlet extends HttpServlet {
private DbHelper helper;

    @Override
    public void init() throws ServletException {
        this.helper = (DbHelper) super.getServletContext().getAttribute("dbmanager");
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
        int idGroup = Integer.parseInt(request.getParameter("group"));
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ServletHelperClass.printHead(out,"Edit","MyGroupServlet","MyGroup List");
            
            out.println("<form action=\"EditGroupServlet\" method=\"post\">");
            out.println("Group name:<br/>");
            out.println("<input type=\"hidden\" name=\"idGroup\" value=\""+idGroup+"\">");
            out.println("<input type=\"text\" style=\"width:300px;height:300px;\" name=\"groupname\" value=\"" + helper.getGroup(idGroup).getName() + "\" /><br/>");
            out.println("<input type=\"submit\" value=\"Submit\" />");
            
            ServletHelperClass.printFoot(out);
        }
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
        String groupName = request.getParameter("groupname");
        String groupId = request.getParameter("idGroup");
        helper.updateGroup(Integer.parseInt(groupId), groupName);
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
