/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        out.println("<a href=\"\\ProgettoPpw\\Admin\\EditGroupServlet?group=" + group.getId() + "\">Edit group</a>");
        out.println("</td>");
        out.println("<td>");
        out.println("<a href=\"\\ProgettoPpw\\Admin\\InviteServlet?group=" + group.getId() + "\">Invite user</a>");
        out.println("</td>");
        out.println("<td>");
        out.println("<a href=\"\\ProgettoPpw\\User\\ReportServlet?group=" + group.getId() + "\">Generate report</a>");
        out.println("</td>");
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
        List<Group> groups = null;
       
            groups = helper.getGroupsByOwner(helper.getUser(ServletHelperClass.getUsername(request, false)));
       
        try (PrintWriter out = response.getWriter()) {
            ServletHelperClass.printHead(out);

            ServletHelperClass.printTableHead(out, "Group ID", "Group name", "Is active","Edit", "Invite", "Report");
            if (groups != null) {
                for (Group group : groups) {
                    printGroupTable(out, group);
                }
            }
            out.println("<tr><td colspan=\"4\"><a href=\"\\ProgettoPpw\\User\\NewGroupServlet\" >New group</a></td></tr>");
            ServletHelperClass.printTableClose(out);
            /* TODO output your page here. You may use following sample code. */

            ServletHelperClass.printFoot(out);
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
