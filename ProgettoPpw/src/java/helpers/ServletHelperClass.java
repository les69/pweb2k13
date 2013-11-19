/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author les
 */
public class ServletHelperClass 
{
    private ServletHelperClass()
    {}
    public static String getUsername(Cookie[] cookies)
    {
        if(cookies == null)
            return null;
        
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("username"))
                return cookie.getValue();
        }
        return null;
    }
    public static void printHead(PrintWriter out)
            throws IOException
    {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");            
            out.println("</head>");
            out.println("<body>");
    }
    public static void printFoot(PrintWriter out)
            throws IOException
    {
            out.println("</body>");
            out.println("</html>");
    }
}
