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
    public List<Group> getGroupsByOwner(int owner_id)
            throws SQLException
    {
        PreparedStatement stm = null;
        List<Group> groupList = new ArrayList<Group>();
        try
        { 
            if(_connection == null || _connection.isClosed())
                throw new RuntimeException("Connection must be estabilished before a statement");
            stm = _connection.prepareStatement("select * from Groups where id_owner=?");
            stm.setInt(1, owner_id);
            ResultSet rs = null;
            
            try
            {
                rs = stm.executeQuery();
                while(rs.next())
                {
                    Group g = new Group();
                    //TODO potrebbe non essere una cosa cattiva farlo dal costruttore
                    g.setName(rs.getString("name")); 
                    groupList.add(g);
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
        
        return groupList;
    }
    public List<Group> getGroupsByOwner(User owner)
            throws SQLException
    {
        return getGroupsByOwner(owner.getId());
    }
    public List<Post> getPostFromGroup(int id_group)
            throws SQLException
    {
        PreparedStatement stm = null;
        List<Post> postList = new ArrayList<Post>();
        try
        { 
            if(_connection == null || _connection.isClosed())
                throw new RuntimeException("Connection must be estabilished before a statement");
            stm = _connection.prepareStatement("select * from Post where id_group=?");
            stm.setInt(1, id_group);
            ResultSet rs = null;
            
            try
            {
                rs = stm.executeQuery();
                while(rs.next())
                {
                    Post p = new Post();
                    p.setMessage(rs.getString("message"));
                    p.setDatePost(rs.getDate("date_post"));
                    p.setFileString(rs.getString("file_string"));
                    p.setIdUser((Integer)rs.getInt("id_user"));
                    p.setIdGroup((Integer)rs.getInt("id_group"));
                    p.setId(rs.getInt("id_post"));
                    postList.add(p);
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
        
        return postList;
    }
    public List<Post> getPostFromGroup(Group g)
            throws SQLException
    {
        return getPostFromGroup(g.getId());
    }
    public User getUser(int id_user)
            throws SQLException
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        { 
            if(_connection == null || _connection.isClosed())
                throw new RuntimeException("Connection must be estabilished before a statement");
            stm = _connection.prepareStatement("select * from Users where id_user=?");
            stm.setInt(1, id_user);
            ResultSet rs = null;
            
            try
            {
                rs = stm.executeQuery();
                while(rs.next())
                {
                    usr = new User();
                    usr.setId(id_user);
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
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
    public User getUser(String username)
            throws SQLException
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        { 
            if(_connection == null || _connection.isClosed())
                throw new RuntimeException("Connection must be estabilished before a statement");
            stm = _connection.prepareStatement("select * from Users where username=?");
            stm.setString(1, username);
            ResultSet rs = null;
            
            try
            {
                rs = stm.executeQuery();
                while(rs.next())
                {
                    usr = new User();
                    usr.setId(rs.getInt("id_user"));
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
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
