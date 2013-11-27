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
 * @author les
 */
public class CreateInviteServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        
        

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
        
        if(request.getParameter("group") == null)
            throw new ServletException("Bad Error: group parameter must be set");
        
        Integer id_group = Integer.parseInt(request.getParameter("group"));
        Group g = helper.getGroup(id_group);
        
        try
        {
            PrintWriter out = response.getWriter();
            ServletHelperClass.printHead(out);
            
            out.println("<form action=\"InviteServlet\" method=\"post\">");
            out.println("Username: <input type=\"text\" name=\"username\" /><br/>");
            out.println("<input type=\"hidden\" name=\"group\" value=\""+g.getId()+"\"/>");
            out.println("<input type=\"submit\" value=\"invite\" /></form>");
            ServletHelperClass.printFoot(out);
        }
        catch (Exception ex)
        {}
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
         if(request.getParameter("username") == null)
            throw new ServletException("Bad Error: group parameter must be set");
        
        Integer id_group = Integer.parseInt(request.getParameter("group"));
        Group g = helper.getGroup(id_group);
        User usr = helper.getUser(request.getParameter("username"));
        
        
        
        
        
        try
        {
            PrintWriter out = response.getWriter();
            ServletHelperClass.printHead(out);
            if (usr == null || usr.getUsername().equals(ServletHelperClass.getUsername(request, false)))
                out.println("<h1>Invite failed</h1>");
            else
            {
                helper.addInvite(g, usr);
                out.println("<h1>Your invite has been sent</h1>");
            }
            out.println("<br/><a href=\"MyGroupServlet\" >Go back to your groups</a>");
            ServletHelperClass.printFoot(out);
        }
        catch(Exception ec)
        {}
        
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
