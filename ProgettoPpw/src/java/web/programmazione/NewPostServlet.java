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
import database.Group;
import database.Post;
import java.io.File;

import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            
            ServletHelperClass.printHead(out, "Add Post","PostServlet?group="+request.getParameter("group"),"Post List");
            
            out.println("<form action=\"NewPostServlet\" enctype=\"multipart/form-data\" method=\"post\">");
            out.println("Message text<br/>");
            out.println("<input type=\"text\" style=\"width:300px;height:300px;\" name=\"text\" /><br/>");
            out.println("<input type=\"hidden\" name=\"group\" value=\""+request.getParameter("group")+"\" />");
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
                    try  {
                        PrintWriter out = response.getWriter();

            
            User usr = helper.getUser(ServletHelperClass.getUsername(request,false));
            String relativeWebPath = "/WEB-INF/uploads";
            Integer maxSize = Integer.parseInt((String)getServletConfig().getInitParameter("maxLengthSize"));
            
            checkPath(relativeWebPath);
            String absoluteFilePath = getServletContext().getRealPath(relativeWebPath)+File.separator;
            
           try{
            MultipartRequest multi = new MultipartRequest(request, absoluteFilePath,maxSize,"utf-8", new DefaultFileRenamePolicy());
                  
            String group = (String) multi.getParameter("group");
             

            Group g = helper.getGroup(Integer.parseInt(group));
            if(g == null)
                throw new ServerException("Bad Error: group is null");
            absoluteFilePath+=g.getName();
            checkPath(absoluteFilePath);

            Enumeration file_list = multi.getFileNames();

            while(file_list.hasMoreElements())
            {
                String name =(String) file_list.nextElement();
                String filename = multi.getFilesystemName(name);
                String originalname=multi.getOriginalFileName(name);
                String type=multi.getContentType(name);
                File f= multi.getFile(name);
                
                if(f == null)
                    continue;
                if(helper.isAGroupFile(g, originalname) != null)
                {
                    out.println("<h1>This file already exists</h1><br/><h6>Your post was not submitted.</h6>");
                    out.println("<a href=\"PostServlet?group="+g.getId()+"\">Come back to post list</a>");
                    f.delete();
                    return;
                }
                String hash = ServletHelperClass.encryptPassword(originalname+g.getName()+usr.getUsername());
                File renameFile = new File(absoluteFilePath+"/"+hash);
                if(!renameFile.exists())
                    f.renameTo(renameFile);
                database.FileDB file = new database.FileDB();
                file.setHashed_name(hash);
                file.setType(type);
                file.setOriginal_name(originalname);
                file.setId_user(usr.getId());
                file.setId_group(g.getId());
                helper.addFile(file);
            }
            
            Enumeration params = multi.getParameterNames();

            String text = (String) multi.getParameter("text");
                
             //file linking needed
            
            String message = ServletHelperClass.parseText(g, text, helper);
            Post p = new Post();
            p.setIdGroup(g.getId());
            p.setIdUser(usr.getId());
            p.setMessage(message);
            helper.addPost(p);
 
            //response.sendRedirect("PostServlet?g="+g.getId());
            request.getRequestDispatcher("/Group/PostServlet?group="+g.getId()).forward(request, response);
            
            


           }
           catch(IOException ioex)
           {
               out.println(ioex.getMessage());
           }
            
        }
                    catch(Exception ex){
                        String e = ex.getMessage();
                        
                        
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
    private void checkPath(String path)
    {
        File dir = new File(path);
        
        if(!dir.exists())
            dir.mkdir();
    }

}
