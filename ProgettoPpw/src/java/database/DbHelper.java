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
import database.Group;
import database.User;
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
    public User authenticate(String username, String password)
            throws SQLException, Exception
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        { 
            if(_connection == null || _connection.isClosed())
                throw new RuntimeException("Connection must be estabilished before a statement");
            stm = _connection.prepareStatement("select * from Users where username=? and password=?");
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = null;
            
            try
            {
                rs = stm.executeQuery();
                if(rs.next())
                {
                    usr = new User();
                    usr.setUsername(username);
                    usr.setPassword(password);
                }
            }
            catch (SQLException sqlex)
            {}
            finally
            {
                if(rs != null)
                    rs.close();
            }
        }
        catch (Exception ex)
        {}
        finally
        {
            if(stm != null)
                stm.close();
        }
        
        return usr;
    }
}
