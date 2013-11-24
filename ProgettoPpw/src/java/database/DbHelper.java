/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//TODO[Lotto] implement logging in the right way

package database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author les
 */
public class DbHelper implements Serializable {

    private transient Connection _connection;

    public DbHelper(String url)
    {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true,
                    getClass().getClassLoader());
            Connection con = DriverManager.getConnection(url);
            _connection = con;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.toString(), e);
        }
    }

    public static void close()
    {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
        }
    }

    public User authenticate(String username, String password)
    {
        PreparedStatement stm = null;
        User usr = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where username=? and password=?");
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                if (rs.next()) {
                    usr = new User();
                    usr.setUsername(username);
                    usr.setPassword(password);
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        catch (Exception ex) {
        } finally {
            if (stm != null) {
                try{
                    stm.close();
                }
                catch (SQLException sex){}
            }
        }

        return usr;
    }

    public List<Group> getGroupsByOwner(int owner_id)
             {
        PreparedStatement stm = null;
        List<Group> groupList = new ArrayList<Group>();
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Groups where id_owner=?");
            stm.setInt(1, owner_id);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    Group g = new Group();
                    //TODO potrebbe non essere una cosa cattiva farlo dal costruttore

                    //PER BLèKMIRKO, imposta tutti i valori e non solo il nome o si rompe tutto
                    g.setId(rs.getInt("id_group"));
                    g.setName(rs.getString("name"));
                    g.setOwner(rs.getInt("id_owner"));
                    g.setDateCreation(rs.getDate("date_creation"));
                    groupList.add(g);
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try{
                stm.close();
                }catch (SQLException sex){}
            }
        }

        return groupList;
    }

    public List<Group> getGroupsByOwner(User owner) {
        
            return getGroupsByOwner(owner.getId());
        
        //    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        
    }

    public List<PostToShow> getPostFromGroup(int id_group)
    {
        PreparedStatement stm = null;
        List<PostToShow> postList = new ArrayList<PostToShow>();
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from post join users on post.ID_USER = USERS.ID_USER where id_group = ? order by DATE_POST desc");
            stm.setInt(1, id_group);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    PostToShow pts = new PostToShow(rs.getString("DATE_POST"),
                            rs.getString("message"), rs.getString("username"));
                    postList.add(pts);
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try
                {
                    stm.close();
                }
                catch (SQLException sex){}
            }
        }

        return postList;
    }

    public List<PostToShow> getPostFromGroup(Group g)
    {
        return getPostFromGroup(g.getId());
    }

    public User getUser(int id_user)
    {
        PreparedStatement stm = null;
        User usr = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where id_user = ?");
            stm.setInt(1, id_user);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    usr = new User();
                    usr.setId(id_user);
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try{     
                    stm.close();
                }
                catch(SQLException sex){}
            }
        }

        return usr;
    }

    public User getUser(String username)
    {
        PreparedStatement stm = null;
        User usr = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where username=?");
            stm.setString(1, username);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    usr = new User();
                    usr.setId(rs.getInt("id_user"));
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try
                {
                    stm.close();
                }
                catch (SQLException sex){}
            }
        }

        return usr;
    }

    public Group getGroup(int idGroup)
    {
        PreparedStatement stm = null;
        Group grp = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GROUPS where id_group=?");
            stm.setInt(1, idGroup);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    grp = new Group();
                    grp.setId(idGroup);
                    grp.setName(rs.getString("NAME"));
                    grp.setActive(rs.getBoolean("ACTIVE"));
                    grp.setOwner(rs.getInt("ID_OWNER"));
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try{
                stm.close();
                }
                catch(SQLException sex){}
            }
        }

        return grp;
    }
    public Group getGroup(String groupName)
    {
        PreparedStatement stm = null;
        Group grp = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GROUPS where name=?");
            stm.setString(1, groupName);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    grp = new Group();
                    grp.setId(rs.getInt("ID_GROUP"));
                    grp.setName(groupName);
                    grp.setActive(rs.getBoolean("ACTIVE"));
                    grp.setOwner(rs.getInt("ID_OWNER"));
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                    try{
                stm.close();
                }
                catch(SQLException sex){}
            }
        }

        return grp;
    }
    public List<Invite> getUserInvites(Integer id_user)
    {
        PreparedStatement stm = null;
        List<Invite> invites = new ArrayList<Invite>();
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Invite where id_user=?");
            stm.setInt(1, id_user);
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                while (rs.next()) {
                    Invite inv = new Invite();
                    inv.setIdUser(id_user);
                    inv.setIdGroup(rs.getInt("id_group"));
                    inv.setInviteDate(rs.getDate("invite_date"));
                    invites.add(inv);
                }
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                    try{
                stm.close();
                }
                catch(SQLException sex){}
            }
        }

        return invites;
    }
    public List<Invite> getUserInvites(User usr)
    {
        return getUserInvites(usr.getId());
    }
    
    public void acceptInvite(Group g, User usr)
    {
        try {
            
            this.removeInvite(g, usr);
            this.addUserToGroup(g, usr);
          
        } 
        catch (Exception ex) {
        } 
  
    }
    public void removeInvite(Group g, User usr)
    {
            
        PreparedStatement stm = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("delete from Invite where id_user=? and id_group=?");
            stm.setInt(1, usr.getId());
            stm.setInt(2, g.getId());

            int res = stm.executeUpdate();          
        } 
        catch (Exception ex) {
        } 
        finally {
            if (stm != null) {
                    try{
                stm.close();
                }
                catch(SQLException sex){}
            }
    
        }
    
    }
    public void addUserToGroup(Group g, User usr)
    {
        PreparedStatement stm = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            
            stm = _connection.prepareStatement("INSERT INTO GROUPUSER (ID_GROUP, ID_USER, ACTIVE) VALUES (?, ?, true)");
            stm.setInt(1, g.getId());
            stm.setInt(2, usr.getId());
            
            int res = stm.executeUpdate();
            
          
        } 
        catch (Exception ex) {
        } 
        finally {
            if (stm != null) 
                    try{
                stm.close();
                }
                catch(SQLException sex){}
        }
    }
    public void addFile(FileDB file)
    {
        PreparedStatement stm = null;
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            
            stm = _connection.prepareStatement("INSERT INTO FILEDB (ID_GROUP, ID_USER, HASHED_NAME,ORIGINAL_NAME,TYPE) VALUES (?, ?, ?, ?, ?)");
            stm.setInt(1, file.getId_group());
            stm.setInt(2, file.getId_user());
            stm.setString(3, file.getHashed_name());
            stm.setString(4, file.getOriginal_name());
            stm.setString(5, file.getType());
            
            int res = stm.executeUpdate();
            
          
        } 
        catch (Exception ex) {
        } 
        finally {
            if (stm != null) 
                    try{
                stm.close();
                }
                catch(SQLException sex){}
        }
    }
    public boolean isAUserFile(User usr, String filehash)
    {
        PreparedStatement stm = null;
        
        try {
            if (_connection == null || _connection.isClosed()) {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from FileDB where original_name=? and id_user=?");
            stm.setString(1, filehash);
            stm.setInt(2, usr.getId());
            ResultSet rs = null;

            try {
                rs = stm.executeQuery();
                if(rs.next())
                    return true;
            } catch (SQLException sqlex) {
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (Exception ex) {
        } finally {
            if (stm != null) {
                try
                {
                    stm.close();
                }
                catch (SQLException sex){}
            }
        }

        return false;
    }
}

