/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import database.DbHelper;
import database.Group;
import database.User;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author les
 */
public class ServletHelperClass {

    private ServletHelperClass() {
    }

    public static String getUsernameFromCookies(Cookie[] cookies) {
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
    /**
     * Gets username of the current user from Session or Cookies.From cookies is a deprecated version
     *
     * @param request 
     * @param useCookies 
     * @return the username of the user logged or null if there's none.
     */
    public static String getUsername(HttpServletRequest request, boolean useCookies) {
        
        if(request == null)
            return null;
        if(useCookies)
            return getUsernameFromCookies(request.getCookies());
        else
            return getUsernameFromSession(request.getSession());
            
    }
        /**
     * Retrieve username from sessione
     *
     * @param session  
     * 
     * @return the username of the user logged or null if there's none.
     */
    public static String getUsernameFromSession(HttpSession session)
    {
        return (String)session.getAttribute("username");
    }
        /**
     * Prints the html page header
     *
     * @param out the writer
     * @param title the title of the page
     * @param returnToServlet the url to go in the previous page
     * @param titleBack the name associated to returnToServlet
     * 
     */
    public static void printHead(PrintWriter out, String title,String returnToServlet, String titleBack) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link href=\"../css/bootstrap.css\" type=\"text/css\" rel=\"stylesheet\" />");
        out.println("<link href=\"../css/bootstrap-responsive.css\" type=\"text/css\" rel=\"stylesheet\" />");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body style=\"padding-top: 60px;\">");
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
"            <ul class=\"nav\">\n" +
"              <li class=\"active\"><a href=\""+returnToServlet+"\">"+titleBack+"</a></li>\n" +
"            </ul>\n" +
"          </div><!--/.nav-collapse -->\n" +
"        </div>\n" +
"      </div>\n" +
"    </div>");
      out.println("<div class=\"container\">");
    }
        /**
     * Prints the html page footer
     *
     * @param out the writer
     * 
     */
    public static void printFoot(PrintWriter out) {
        out.println("</div> <!-- container -->");
        out.println("</body>");
        out.println("</html>");
    }
        /**
     * Prints the html for a table head
     *
     * @param out the writer
     * @param tableCols the columns for the table
     * 
     */
    public static void printTableHead(PrintWriter out, String... tableCols) {
        printTableHead(out, true, tableCols);
    }
        /**
     * Prints the html for a table head with border
     *
     * @param out the writer
     * @param border if there's the need for borders
     * @param tableCols the columns for the table
     * 
     */
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
        /**
     * Prints the html for a table close tag
     *
     * @param out the writer
     * 
     */
    public static void printTableClose(PrintWriter out) {
        out.println("</table>");

    }
        /**
     * Main method for parsing text.Given a file with $$filename$$ or $$url$$ it returns a parsed text with html references to the elements
     *
     * @param grp
     * @param text the original text
     * @param helper tha database interface
     * 
     * @return a string with the parsed text
     * 
     */
    public static String parseText(Group grp, String text, DbHelper helper) {
        List<List<String>> listsOfMatch = getMatches(text);

        //Significa che non ci sono link
        if (listsOfMatch.isEmpty()) {
            return text;
        }
        List<String> matchedStrings = listsOfMatch.get(0);
        List<String> filesToLink = listsOfMatch.get(1);

        List<String> linkedFiles = convertMatchedStrings(filesToLink, grp, helper);
        String parsedText = replaceStringsInText(text, matchedStrings, linkedFiles);

        return parsedText;

    }
        /**
     * Finds the matches for words between $$ and $$
     *
     * @param text the original text
     * @return A list of Lists of strings. The first list contains the strings between the dollars and the second contains the matched strings in the form $$word$$ 
     * 
     */
    public static List<List<String>> getMatches(String text) {
        List<String> matchedStrings = new ArrayList<>();
        List<String> stringsToReplace = new ArrayList<>();

        List<List<String>> toRet = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\$\\$(\\S+)\\$\\$");
        try {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                stringsToReplace.add(matcher.group());
                matchedStrings.add(matcher.group(1));
            }

            toRet.add(stringsToReplace);
            toRet.add(matchedStrings);
        } catch (Exception ex) {
        }

        return toRet;
    }
            /**
     * Check if the given string is an url
     *
     * @param url
     * @return true if matched else false
     * 
     */
    public static boolean isAnUrl(String url) {
        Pattern pattern = Pattern.compile("((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)");
        boolean matched = false;
        try {
            Matcher matcher = pattern.matcher(url);
            matched = matcher.matches();
        } catch (Exception ex) {
        }
        return matched;

    }
        /**
     * Replaces all the matched strings in the parsed version
     *
     * @param text the original text
     * @param toReplace the list of strings to replace
     * @param replacements 
     * 
     * @return a string with the parsed text
     * 
     */
    public static String replaceStringsInText(String text, List<String> toReplace, List<String> replacements) {
        if (toReplace.size() != replacements.size()) {
            throw new RuntimeException("Error: Different size between original strings and replacements");
        }

        for (int i = 0; i < toReplace.size(); i++) {
            text = text.replace(toReplace.get(i), replacements.get(i));
        }
        return text;

    }
        /**
     * Puts html in the post to link the items
     *
     * @param matches the list of matched strings
     * @param g
     * @param helper the database interface
     * 
     * @return a string with the linked text
     * 
     */
    public static List<String> convertMatchedStrings(List<String> matches, Group g, DbHelper helper) {
        List<String> parsedStrings = new ArrayList<>();
        User usr = null;
        if (matches == null) {
            return null;
        }
        for (int i = 0; i < matches.size(); i++) {
            String m = matches.get(i);
            String parsed = "";
            if ((usr = helper.isAGroupFile(g, m)) == null) 
            {
                if (isAnUrl(m)) {
                    parsed = "<a href=\"" + m + "\">" + m + "</a>";
                } else // Se è un file che non esiste e nemmeno uno URL copio il nome e basta, come se non fosse stato linkato
                {
                    parsed = m;
                }
            } else {
                String hash = encryptPassword(m + g.getName()+usr.getUsername());
                parsed = "<a href=\"DownloadServlet?file=" + hash + "&group=" + g.getId() + "\">" + m + "</a>";

            }
            parsedStrings.add(parsed);

        }
        return parsedStrings;
    }
        /**
     * Hash a string with SHA1 algorithm
     *
     * @param password text to hash

     * 
     * @return an hashed string
     * 
     */
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
        /**
     * Converts an array of bytes to hexadecimal
     *
     * @param hash 

     * 
     * @return a formatted string
     * 
     */
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
