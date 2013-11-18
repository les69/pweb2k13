/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import database.DbHelper;
import database.User;
import database.Group;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author les
 */
public class GroupServlet extends HttpServlet {

    private DbHelper helper;
    @Override
    public void init() throws ServletException 
    {
        this.helper =(DbHelper)super.getServletContext().getAttribute("dbmanager");
    }
    private String getUsername(Cookie[] cookies)
    {
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if(cookie.getName().equals("username"))
                return cookie.getValue();
            
        }
        return null;
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
            String username = getUsername(request.getCookies());
            
            if(username ==  null)
                throw new ServerException("Bad Error: Username is not defined when it MUST be");
            if(this.helper == null)
                throw new ServerException("Bad Error: dbHelper is not defined when it MUST be");
            
            User usr = helper.getUser(username);
            List<Group> groups = helper.getGroupsByOwner(usr);
            out.println("This are the groups that you are following");
            out.println("<table>");
            for (int i = 0; i < groups.size(); i++) {
                Group g = groups.get(i);
                out.println("<tr>");
                out.println("<td>"+g.getName()+"</td>");
                out.println("<td>"+helper.getUser(g.getOwner()).getUsername()+"</td>");
                //MISSING COLUMN DATA
                out.println("<td>href=\"\\ProgettoPpw\\Group\\PostServlet\">See Posts</a>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            
        }
        catch (Exception ex)
        {}
    }
    private void printHead(PrintWriter out)
            throws IOException
    {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");            
            out.println("</head>");
            out.println("<body>");
    }
    private void printFoot(PrintWriter out)
            throws IOException
    {
            out.println("</body>");
            out.println("</html>");
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