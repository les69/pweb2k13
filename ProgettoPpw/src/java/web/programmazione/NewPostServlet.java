/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import com.google.common.io.Files;
import database.DbHelper;
import database.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import helpers.ServletHelperClass;
import java.util.List;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import java.io.File;
import java.util.Enumeration;


/**
 *
 * @author Lorenzo
 */
public class NewPostServlet extends HttpServlet {

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
        
        //QUI Stampiamo il form per l'inserimento del nuovo post
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            ServletHelperClass.printHead(out);
            
            out.println("<form action=\"NewPostServlet\" enctype=\"multipart/form-data\" method=\"post\">");
            out.println("Message text<br/>");
            out.println("<input type=\"text\" style=\"width:300px;height:300px;\" name=\"text\" /><br/>");
            out.println("<input type=\"file\" name=\"file\" />");
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            User usr = helper.getUser(ServletHelperClass.getUsername(request.getCookies()));
            String relativeWebPath = "/WEB-INF/uploads";
            String absoluteFilePath = getServletContext().getRealPath(relativeWebPath)+"/"+usr.getUsername();
            out.println(absoluteFilePath);
            MultipartRequest multi = new MultipartRequest(request, absoluteFilePath,10*1024*1024,"utf-8");
            
            Enumeration params = multi.getParameterNames();
            List<String> files = null;
            while(params.hasMoreElements())
            {
                String name=(String)params.nextElement();
                String value = (String) multi.getParameter(name);
                
                if(name.equals("text"))
                    files = ServletHelperClass.getMatches(value);
            }
                         

    
            Enumeration file_list = multi.getFileNames();
            
            while(file_list.hasMoreElements())
            {
                String name =(String) file_list.nextElement();
                String filename = multi.getFilesystemName(name);
                String originalname=multi.getOriginalFileName(name);
                String type=multi.getContentType(name);
                File f= multi.getFile(name);
                String path = absoluteFilePath+"/"+(ServletHelperClass.encryptPassword(originalname+usr.getUsername()));
                File renameFile = new File(path);
                int x = 0;
                f.renameTo(renameFile);
                //Files.move(f, renameFile);
            }
        }
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
