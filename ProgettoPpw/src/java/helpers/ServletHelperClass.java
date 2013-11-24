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
    public static String parseText(Group grp, String text, DbHelper helper)
    {
        List<List<String>> listsOfMatch = getMatches(text);
        
        //Significa che non ci sono link
        if(listsOfMatch.isEmpty())
            return text;
        List<String> matchedStrings = listsOfMatch.get(0);
        List<String> filesToLink = listsOfMatch.get(1);
        
        List<String> linkedFiles = convertMatchedStrings(filesToLink, grp, helper);
        String parsedText = replaceStringsInText(text, matchedStrings, linkedFiles);
        
        return parsedText;
        
    }
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
    public static boolean isAnUrl(String url)
    {
        Pattern pattern = Pattern.compile("((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)");
        boolean matched = false;
        try
        {
            Matcher matcher = pattern.matcher(url);
            matched = matcher.matches();
        }
        catch (Exception ex)
        {}
        return matched;
            
    }   
    public static String replaceStringsInText(String text,List<String> toReplace, List<String> replacements)
    {
        if(toReplace.size() != replacements.size())
            throw new RuntimeException("Error: Different size between original strings and replacements");
        
        for (int i = 0; i < toReplace.size(); i++) 
            text = text.replace(toReplace.get(i), replacements.get(i));
        return text;
            
        
    }
    public static List<String> convertMatchedStrings(List<String> matches, Group g, DbHelper helper)
    {
        List<String> parsedStrings = new ArrayList<>();
        if(matches == null)
            return null;
        for (int i = 0; i < matches.size(); i++) {
            String m = matches.get(i);
            String parsed ="";
            if(!helper.isAGroupFile(g, m)) //fix this function
            {
                if(isAnUrl(m))
                    parsed = "<a href=\""+m+"\">"+m+"</a>";
                else // Se è un file che non esiste e nemmeno uno URL copio il nome e basta, come se non fosse stato linkato
                    parsed = m;
            }
            else
            {
                String hash = encryptPassword(m+g.getName());
                parsed = "<a href=\"DownloadServlet?file="+hash+"&group="+g.getId()+"\">"+m+"</a>";
                
            }
            parsedStrings.add(parsed);
            
        }
        return parsedStrings;
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
