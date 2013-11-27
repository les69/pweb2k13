/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.programmazione;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.DbHelper;
import database.UserReport;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo
 */
public class ReportServlet extends HttpServlet {

    private DbHelper helper;

    @Override
    public void init() throws ServletException {
        this.helper = (DbHelper) super.getServletContext().getAttribute("dbmanager");
    }

    public void printPost(UserReport ur, PrintWriter out) {
        out.println("<tr>");
        out.println("<td>");
        out.println(ur.getUsername());
        out.println("</td>");
        out.println("<td>");
        out.println(ur.getLastPost());
        out.println("</td>");
        out.println("<td>");
        out.println(ur.getPostNumber());
        out.println("</td>");
        out.println("</tr>");
    }

    private void MakePDF(Document report, List<UserReport> lur, String groupName)
            throws DocumentException {
        report.add(new Paragraph("Report document for group " + groupName));
        report.add(new Paragraph());
        report.add(new Paragraph("This document has been automatically generated on " + new Date().toString()));
        report.add(new Paragraph(" "));

        report.add(new Paragraph("In the following table, user statistics are given for the group"));
        report.add(new Paragraph(" "));

        PdfPTable myTable = new PdfPTable(3);
        myTable.setHeaderRows(1);
        myTable.addCell("Username");
        myTable.addCell("Post count");
        myTable.addCell("Last activity in group");

        for (UserReport ur : lur) {
            myTable.addCell(ur.getUsername());
            myTable.addCell(String.valueOf(ur.getPostNumber()));
            myTable.addCell(ur.getLastPost().toString());
        }

        report.add(myTable);
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
        response.setContentType("application/pdf");
        /* TODO output your page here. You may use following sample code. */

        int idGroup = Integer.parseInt(request.getParameter("group"));

        List<UserReport> lur = helper.getGroupReport(idGroup);
        String groupName = helper.getGroup(idGroup).getName();
        Document report = new Document();
        try {

            PdfWriter.getInstance(report, response.getOutputStream());
            report.open();
            MakePDF(report, lur, groupName);
            report.close();

        } catch (Exception ex) {
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
