/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;

/**
 *
 * @author les
 */
public class ServletHelperClass {

    private ServletHelperClass() {
    }

    public static String getUsername(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void printHead(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet HomeServlet</title>");
        out.println("</head>");
        out.println("<body>");
    }

    public static void printFoot(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }

    public static void printTableHead(PrintWriter out, String... tableCols) {
        printTableHead(out, true, tableCols);
    }

    public static void printTableHead(PrintWriter out, boolean border, String... tableCols) {
        if (!border) {
            out.println("<table>");
        } else {
            out.println("<table border=\"1\">");
        }
        out.println("<tr>");
        for (String col : tableCols) {
            out.println("<th><b>" + col + "</b></th>");
        }
        out.println("</tr>");
    }

    public static void printTableClose(PrintWriter out) {
        out.println("</table>");

    }

    public static List<String> getMatches(String text) {
        List<String> matchedStrings = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\$\\$(\\w+)\\$\\$");
        try {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                matchedStrings.add(matcher.group(1));
            }
        } catch (Exception ex) {
        }
        return matchedStrings;
    }

    public static String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
