/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import database.DbHelper;
import database.Group;
import database.Invite;
import database.User;
import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author les
 */
public class InviteServlet extends HttpServlet {

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
        try 
        {
            PrintWriter out = response.getWriter();
            String username = ServletHelperClass.getUsername(request, false);
            
            if(username ==  null)
                throw new ServletException("Bad Error: Username not set");
            ServletHelperClass.printHead(out);
            
            User usr = helper.getUser(username);
            out.println("<h1>Hello "+ usr.getUsername() + ". These are your pending invites!</h1>");
            ServletHelperClass.printTableHead(out, "From", "Group","Accept", "Decline");
            List<Invite> invites = helper.getUserInvites(usr);
            out.println("<form action=\"AcceptInvite\" method=\"post\">");
            printTable(invites, out);
            ServletHelperClass.printTableClose(out);
            out.println("<input type=\"submit\" value=\"Accept\" />");
            out.println("</form>");
            ServletHelperClass.printFoot(out);
        }
        catch(Exception ex)
        {}
    }
    private void printTable(List<Invite> invites, PrintWriter out)
            throws SQLException
    {
        for (int i = 0; i < invites.size(); i++) {
            Invite inv = invites.get(i);
            Group g = helper.getGroup(inv.getIdGroup());
            out.println("<tr>");
            out.println("<td>"+(helper.getUser(g.getOwner())).getUsername() +"</td>");
            out.println("<td>"+ g.getName()+"</td>");
            out.println("<td><input type=\"checkbox\" name="+g.getId()+" /></td>");           
            out.println("<td><a href=\"\\ProgettoPpw\\User\\DeclineInvite?group="+g.getId()+"\" >Decline</a></td>");
            out.println("</tr>");
        }
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
        processRequest(request, response);
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
