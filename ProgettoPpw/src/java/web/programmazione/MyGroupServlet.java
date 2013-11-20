/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD

package web.programmazione;

import java.io.IOException;
import java.io.PrintWriter;
=======
package web.programmazione;

import com.sun.xml.rpc.encoding.GenericObjectSerializer;
import database.DbHelper;
import database.Group;
import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
>>>>>>> d319f278a2a05031c930638a1964355d636d0088
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
<<<<<<< HEAD
 * @author les
 */
public class MyGroupServlet extends HttpServlet {

=======
 * @author lorenzo
 */
public class MyGroupServlet extends HttpServlet {

    private DbHelper helper;

    @Override
    public void init() throws ServletException {
        this.helper = (DbHelper) super.getServletContext().getAttribute("dbmanager");
    }

    public void printGroupTable(PrintWriter out, Group group) {
        out.println("<tr>");
        out.println("<td>");
        out.println(group.getId());
        out.println("</td>");
        out.println("<td>");
        out.println(group.getName());
        out.println("</td>");
        out.println("<td>");
        out.println(group.isActive());
        out.println("</td>");
        out.println("<td>");
        out.println("<a href=\"\\ProgettoPpw\\Group\\GroupActionServlet?action=delete&group=" + group.getId() + "\">Delete group</a>");
        out.println("</td>");
    }

>>>>>>> d319f278a2a05031c930638a1964355d636d0088
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
<<<<<<< HEAD
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyGroupServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyGroupServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
=======
        List<Group> groups = null;
        try {
            groups = helper.getGroupsByOwner(helper.getUser(ServletHelperClass.getUsername(request.getCookies())));
        } catch (SQLException ex) {
            Logger.getLogger(MyGroupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PrintWriter out = response.getWriter()) {
            ServletHelperClass.printHead(out);

            ServletHelperClass.printTableHead(out, "Group ID", "Group name", "Is active", "Action");
            if (groups != null) {
                for (Group group : groups) {
                    printGroupTable(out, group);
                }
            }
            out.println("<tr><td colspan=\"4\"><a href=\"\\ProgettoPpw\\Group\\GroupActionServlet?action=new\" align=\"right\">New group</a></td></tr>");
            ServletHelperClass.printTableClose(out);
            /* TODO output your page here. You may use following sample code. */

            ServletHelperClass.printFoot(out);
>>>>>>> d319f278a2a05031c930638a1964355d636d0088
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
