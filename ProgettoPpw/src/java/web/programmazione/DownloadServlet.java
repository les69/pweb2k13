/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web.programmazione;

import database.DbHelper;
import database.FileDB;
import database.Group;
import helpers.ServletHelperClass;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author les
 */
public class DownloadServlet extends HttpServlet {
    static final long serialVersionUID = 1L;
    private static final int BUFSIZE = 1024*1024*10;
    private String filePath;
    
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
        
        String relativeWebPath = "/WEB-INF/uploads";
        
        String file_hash = request.getParameter("file");
        String group_id =request.getParameter("group");
        Enumeration vals =request.getParameterNames();
        Group g = helper.getGroup(Integer.parseInt(group_id));
        filePath = getServletContext().getRealPath(relativeWebPath)+File.separator+g.getName()+File.separator;
        
        try{
            //PrintWriter out = response.getWriter();
            //ServletHelperClass.printHead(out);
            File tmpFile = new File(filePath+file_hash);
            if(!tmpFile.exists())
                printError(response, "File not found", g.getId());
                //out.println("The request file was not found!");
            
            FileDB file = helper.getFile(g, file_hash);
            
            response.setContentType(file.getType());
            response.setContentLength((int)tmpFile.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getOriginal_name() + "\"");
            byte[] byteBuffer = new byte[BUFSIZE];
            DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
            ServletOutputStream outStream = response.getOutputStream();

            // reads the file's bytes and writes them to the response stream
            int length = 0;
            while ((in != null) && ((length = in.read(byteBuffer)) != -1))
            {
                outStream.write(byteBuffer,0,length);
            }

            in.close();
            outStream.close();
            
        }
        catch(IOException ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, 
                    "Error while retrieving file for download", ex);
        }

    }
    private void printError(HttpServletResponse response, String message, int id_group)
    {
       try
       {
           PrintWriter out = response.getWriter();
           ServletHelperClass.printHead(out, "Download","PostServlet?group="+id_group,"Post List");
           out.println("<h1>"+message+"</h1>");
           out.println("<a href=\"PostServlet?group="+id_group+"\">Come back to post list</a>");
           ServletHelperClass.printFoot(out);
       
           
       }
       catch(IOException ex)
       {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, 
                    "Error while printing error", ex);
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
