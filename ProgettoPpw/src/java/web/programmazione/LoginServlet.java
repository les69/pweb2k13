/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.programmazione;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DbHelper;
import database.User;
import java.util.Date;
import javax.servlet.http.Cookie;

/**
 *
 * @author Lorenzo(Mr Blék) und Mirko(Mr. Les)
 */
public class LoginServlet extends HttpServlet {

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

        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");

        try {

            PrintWriter out = response.getWriter();
            if (username == null || password == null) {
                printErrorPage(out, "You have to log in");
            } else {
                User user = this.helper.authenticate(username, password);

                if (user == null) {
                    printErrorPage(out, "Your credentials are wrong!");
                } else {
                    Cookie usernameCookie = new Cookie("username", user.getUsername());
                    request.getSession().setAttribute("username", username);
                    response.addCookie(usernameCookie);
                    response.sendRedirect("/ProgettoPpw/User/HomeServlet");
                }
            }

        } catch (Exception ex) {
        }
    }

    private void printErrorPage(PrintWriter out, String message)
            throws IOException {
        //Questa è la easy soluzione, ristampo la pagina di login(tanto è poco codice comunque)
        // se più avanti avremo idee migliori fixeremo
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width\">\n" +
"<link href=\"css/bootstrap.css\" type=\"text/css\" rel=\"stylesheet\" /><link href=\"css/bootstrap-responsive.css\" type=\"text/css\" rel=\"stylesheet\" /><title>Login</title>");
        out.println("</head><body style=\"padding-top: 60px;\">");
        out.println("<div class=\"navbar navbar-inverse navbar-fixed-top\">\n" +
"      <div class=\"navbar-inner\">\n" +
"        <div class=\"container\">\n" +
"          <button type=\"button\" class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
"            <span class=\"icon-bar\"></span>\n" +
"            <span class=\"icon-bar\"></span>\n" +
"            <span class=\"icon-bar\"></span>\n" +
"          </button>\n" +
"          <a class=\"brand\" href=\"/ProgettoPpw/User/HomeServlet\">Web Programming Project</a>\n" +
"          <div class=\"nav-collapse collapse\">\n" +
"          </div><!--/.nav-collapse -->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>");
        out.println("<div class=\"container\"><h1>Login page</h1><div class=\"alert alert-error\">\n" +
"  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n" +
"  <strong>Error! </strong>"+message+
"</div><div class=\"navbar\">\n" +
"              <div class=\"navbar-inner\">\n" +
"     ");
        out.println("<form class=\"navbar-form pull-down\" action=\"LoginServlet\" method=\"post\">Username<br/> <input type=\"text\" class=\"span2\" name=\"username\"/><br/>Password<br/> <input type=\"password\"  class=\"span2\"name=\"password\" /><br/><input type=\"submit\" class=\"btn\" value=\"Login\" /></form>");
        out.println("</div></div></div></body></html>");
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
