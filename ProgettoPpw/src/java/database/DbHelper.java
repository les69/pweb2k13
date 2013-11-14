/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author les
 */
public class DbHelper implements Serializable {
    private transient Connection _connection;
    
    public DbHelper (String url) 
            throws SQLException
    {
        try 
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true,
                        getClass().getClassLoader());
        } 
        catch(Exception e) 
        {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(url);
        _connection = con;
       
    }
    public static void close()
            throws SQLException
    {
        try 
        {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) 
        {}
    }
    
}
