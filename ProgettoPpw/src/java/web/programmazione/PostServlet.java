/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.programmazione;

import database.DbHelper;
import database.Group;
import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.PostToShow;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lorenzo
 */
public class PostServlet extends HttpServlet {

    private DbHelper helper;

    @Override
    public void init() throws ServletException {
        this.helper = (DbHelper) super.getServletContext().getAttribute("dbmanager");
    }

    public void printPost(PostToShow pts, PrintWriter out) {
        out.println("<tr>");
        out.println("<td>");
        out.println(pts.getUsername());
        out.println("</td>");
        out.println("<td>");
        out.println(pts.getMessage());
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr><td colspan=\"2\">");
        out.println(pts.getDatePost());
        out.println("</td>");
        out.println("</tr>");
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
        try {
            int p = Integer.parseInt(request.getParameter("g"));

            List<PostToShow> ptss;
            ptss = helper.getPostFromGroup(p);
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                printHead(out);

                Group gr = null;
                gr = helper.getGroup(p);

                out.println("<h1>Posts for group " + gr.getName() + "</h1>");
                out.println("<table border=1>");
                ServletHelperClass.printTableHead(out, "User", "Message");
                for (PostToShow pts : ptss) {
                    printPost(pts, out);
                }
                printFoot(out);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void printHead(PrintWriter out)
            throws IOException {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet PostServlet</title>");
        out.println("</head>");
        out.println("<body>");
    }

    private void printFoot(PrintWriter out)
            throws IOException {
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
