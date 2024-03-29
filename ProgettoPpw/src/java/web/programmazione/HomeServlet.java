/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.programmazione;

import helpers.ServletHelperClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo(Mr Blék) und Mirko(Mr. Les)
 */
public class HomeServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String username = ServletHelperClass.getUsername(request, false);
            ServletHelperClass.printHead(out, "Home", "","");
            out.println("<h3>Welcome " + username + ". This is your Home!</h3>");
            //printLastLogin(request.getCookies(), out, username);

            out.println("<h6 style=\"font-style:italic\">Last login at " + (String)request.getSession().getAttribute("last-login") + "</h6>");
            out.println("<br/>");
            out.println("<a href=\"\\ProgettoPpw\\Admin\\MyGroupServlet\">My Groups</a><br/>");
            out.println("<a href=\"\\ProgettoPpw\\Group\\GroupListServlet\">Groups</a><br/>");
            out.println("<a href=\"\\ProgettoPpw\\User\\InviteServlet\">Pending invites</a><br/>");
            out.println("<a href=\"\\ProgettoPpw\\LogoutServlet\">Log out</a><br/>");
            ServletHelperClass.printFoot(out);

        }
    }
    //deprecated
    private void printLastLogin(Cookie[] cookies, PrintWriter out, String username) {

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(username)) {
                    out.println("<h6 style=\"font-style:italic\">Last login at " + cookie.getValue() + "</h6>");
                    break;  //[LOTTO] added because multiple cookies, maybe bug only in my browser
                }
            }
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
