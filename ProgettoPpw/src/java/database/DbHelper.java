/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//TODO[Lotto] implement logging in the right way
package database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
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
public class DbHelper implements Serializable
{

    private transient Connection _connection;

    /**
     * Retrieves connection to a database from a given connection string
     *
     * @param url the Database connection string
     */
    public DbHelper(String url)
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver", true,
                    getClass().getClassLoader());
            Connection con = DriverManager.getConnection(url);
            _connection = con;
        }
        catch (ClassNotFoundException | SQLException e)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while creating DBHelper object", e);
            throw new RuntimeException(e.toString(), e);
        }
    }

    /**
     *
     * Closes the connection to the database
     */
    public static void close()
    {
        try
        {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while closing DB connection for helper object destruction", ex);
        }
    }

    /**
     * Search for a user with the given credentials
     *
     * @param username user credential
     * @param password user password
     * @return null if there is no User with that credentials or a User object
     * with the user credentials
     */
    public User authenticate(String username, String password)
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where username=? and password=?");
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                if (rs.next())
                {
                    usr = new User();
                    usr.setUsername(username);
                    usr.setPassword(password);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {         
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);

                }
            }
        }
        return usr;
    }

    /**
     * Gets a List of Groups in which the user is subscribed.
     *
     * @param usr
     * @param User
     * @return A list with all the groups that the user follows
     */
    public List<Group> getUserGroups(User usr)
    {
        return getUserGroups(usr.getId());
    }

    /**
     * Gets a List of Groups in which the user is subscribed.
     *
     * @param id_user id of the user
     * @return A list with all the groups that the user follows
     */
    public List<Group> getUserGroups(int id_user)
    {
        PreparedStatement stm = null;
        List<Group> groupList = new ArrayList<>();
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GroupUser where id_user=?");
            stm.setInt(1, id_user);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    Integer id_group = rs.getInt("id_group");

                    Group g = getGroup(id_group);
                    groupList.add(g);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return groupList;
    }

    /**
     * Gets a List of Groups in which the user is owner.
     *
     * @param owner_id
     * @return A list with all the groups that the user admins
     */
    public List<Group> getGroupsByOwner(int owner_id)
    {
        PreparedStatement stm = null;
        List<Group> groupList = new ArrayList<Group>();
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Groups where id_owner=?");
            stm.setInt(1, owner_id);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    Group g = new Group();
                    //TODO potrebbe non essere una cosa cattiva farlo dal costruttore

                    //PER BLèKMIRKO, imposta tutti i valori e non solo il nome o si rompe tutto
                    g.setId(rs.getInt("id_group"));
                    g.setName(rs.getString("name"));
                    g.setOwner(rs.getInt("id_owner"));
                    groupList.add(g);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return groupList;
    }

    /**
     * Gets a List of Groups in which the user is owner.
     *
     * @param owner
     * @return A list with all the groups that the user admins
     */
    public List<Group> getGroupsByOwner(User owner)
    {
        return getGroupsByOwner(owner.getId());
    }

    /**
     * Gets the List of Posts in a Group.
     *
     * @param id_group
     * @return A list with all the post from a group
     */
    public List<PostToShow> getPostFromGroup(int id_group)
    {
        PreparedStatement stm = null;
        List<PostToShow> postList = new ArrayList<PostToShow>();
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from post join users on post.ID_USER = USERS.ID_USER where id_group = ? order by DATE_POST desc");
            stm.setInt(1, id_group);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    PostToShow pts = new PostToShow(rs.getString("DATE_POST"),
                            rs.getString("message"), rs.getString("username"));
                    postList.add(pts);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return postList;
    }

    /**
     * Gets the List of Posts in a Group.
     *
     * @param g
     * @return A list with all the post from a group
     */
    public List<PostToShow> getPostFromGroup(Group g)
    {
        return getPostFromGroup(g.getId());
    }

    public User getUser(int id_user)
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where id_user = ?");
            stm.setInt(1, id_user);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    usr = new User();
                    usr.setId(id_user);
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return usr;
    }

    /**
     * Gets a User from the username
     *
     * @param username
     * @return A User object corresponding to the selected username
     */
    public User getUser(String username)
    {
        PreparedStatement stm = null;
        User usr = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Users where username=?");
            stm.setString(1, username);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    usr = new User();
                    usr.setId(rs.getInt("id_user"));
                    usr.setPassword(rs.getString("password"));
                    usr.setUsername(rs.getString("username"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return usr;
    }

    /**
     * Gets the Group associated with the id
     *
     * @param idGroup
     * @return A Group Object with that id
     */
    public Group getGroup(int idGroup)
    {
        PreparedStatement stm = null;
        Group grp = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GROUPS where id_group=?");
            stm.setInt(1, idGroup);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    grp = new Group();
                    grp.setId(idGroup);
                    grp.setName(rs.getString("NAME"));
                    grp.setActive(rs.getBoolean("ACTIVE"));
                    grp.setOwner(rs.getInt("ID_OWNER"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return grp;
    }

    /**
     * Gets the Group with that name
     *
     * @param groupName
     * @return A Group object if exists else null
     */
    public Group getGroup(String groupName)
    {
        PreparedStatement stm = null;
        Group grp = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GROUPS where name=?");
            stm.setString(1, groupName);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    grp = new Group();
                    grp.setId(rs.getInt("ID_GROUP"));
                    grp.setName(groupName);
                    grp.setActive(rs.getBoolean("ACTIVE"));
                    grp.setOwner(rs.getInt("ID_OWNER"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return grp;
    }

    /**
     * Gets the List of Invites for a User
     *
     * @param id_user
     * @return All the pending invites for that user
     */
    public List<Invite> getUserInvites(Integer id_user)
    {
        PreparedStatement stm = null;
        List<Invite> invites = new ArrayList<Invite>();
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from Invite where id_user=?");
            stm.setInt(1, id_user);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    Invite inv = new Invite();
                    inv.setIdUser(id_user);
                    inv.setIdGroup(rs.getInt("id_group"));
                    inv.setInviteDate(rs.getDate("invite_date"));
                    invites.add(inv);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return invites;
    }

    /**
     * Gets the List of Invites for a User
     *
     * @param usr
     * @return All the pending invites for that user
     */
    public List<Invite> getUserInvites(User usr)
    {
        return getUserInvites(usr.getId());
    }

    /**
     * Accept an invite from a group to a user
     *
     * @param g
     * @param user
     */
    public void acceptInvite(Group g, User usr)
    {
        try
        {
            this.removeInvite(g, usr);
            this.addUserToGroup(g, usr);
        }
        catch (Exception ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "An error occurred while accepting an invite", ex);
        }
    }

    /**
     * Decline an invite from a group to a user
     *
     * @param g
     * @param user
     */
    public void removeInvite(Group g, User usr)
    {

        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("delete from Invite where id_user=? and id_group=?");
            stm.setInt(1, usr.getId());
            stm.setInt(2, g.getId());

            int res = stm.executeUpdate();
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Adds a user to a group after accepting an invite
     *
     * @param g
     * @param user
     */
    public void addUserToGroup(Group g, User usr)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }

            stm = _connection.prepareStatement("INSERT INTO GROUPUSER (ID_GROUP, ID_USER, ACTIVE) VALUES (?, ?, true)");
            stm.setInt(1, g.getId());
            stm.setInt(2, usr.getId());

            int res = stm.executeUpdate();

        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Adds the files informations to the database
     *
     * @param file
     */
    public void addFile(FileDB file)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
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
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Checks if a given file belongs to a group
     *
     * @param g
     * @param original_name the original name of the file
     * @return A User object owner if the file belongs to that group else null
     */
    public User isAGroupFile(Group g, String original_name)
    {
        PreparedStatement stm = null;
        User usr = null;

        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from FileDB where original_name=? and id_group=?");
            stm.setString(1, original_name);
            stm.setInt(2, g.getId());
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                if (rs.next())
                {
                    return getUser(rs.getInt("id_user"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return null;
    }

    /**
     * Adds a new post in the database
     *
     * @param p
     */
    public void addPost(Post p)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }

            stm = _connection.prepareStatement("INSERT INTO PWEB.POST (VISIBLE, DATE_POST, MESSAGE, ID_GROUP, ID_USER) VALUES (DEFAULT, CURRENT_TIMESTAMP, ?,?, ?)");
            stm.setString(1, p.getMessage());
            stm.setInt(2, p.getIdGroup());
            stm.setInt(3, p.getIdUser());

            int res = stm.executeUpdate();

        }
        catch (RuntimeException | SQLException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Retrieves a file from an hash
     *
     * @param g
     * @param hash the hash of the file
     * @return The correspondenting file it it exists or null
     */
    public FileDB getFile(Group g, String hash)
    {
        PreparedStatement stm = null;
        FileDB file = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from FileDB where hashed_name=? and id_group=?");
            stm.setString(1, hash);
            stm.setInt(2, g.getId());
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                if (rs.next())
                {
                    file = new FileDB();
                    file.setId_group(rs.getInt("id_group"));
                    file.setId_user(rs.getInt("id_user"));
                    file.setHashed_name(hash);
                    file.setOriginal_name(rs.getString("original_name"));
                    file.setType(rs.getString("type"));
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return file;
    }

    /**
     * Check if a user belongs to a Group
     *
     * @param usr
     * @param grp
     * @return true if the user is in the given group else false
     */
    public boolean doesUserBelongsToGroup(User usr, Group grp)
    {
        return doesUserBelongsToGroup(usr, grp.getId());
    }

    /**
     * Check if a user belongs to a Group
     *
     * @param usr
     * @param id_group
     * @return true if the user is in the given group else false
     */
    public boolean doesUserBelongsToGroup(User usr, Integer id_group)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from GROUPUSER where id_group=? and id_user=? and active=true");
            stm.setInt(1, id_group);
            stm.setInt(2, usr.getId());
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                if (rs.next())
                {
                    return true;
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return false;
    }

    /**
     * Adds a new group to the database
     *
     * @param grp
     */
    public void addGroup(Group grp)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("INSERT INTO PWEB.GROUPS(NAME,ACTIVE,ID_OWNER) VALUES (?, ?, ?)");
            stm.setString(1, grp.getName());
            stm.setBoolean(2, true);
            stm.setInt(3, grp.getOwner());
            int res = stm.executeUpdate();
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Gets the date of the last post made by an User in a Group
     *
     * @param idUser
     * @param idGroup
     * @return the date of the post or null
     */
    private Date getLastPostForUserInGroup(int idUser, int idGroup)
    {
        PreparedStatement stm = null;
        Date d = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from ( select ID_USER, DATE_POST, ROW_NUMBER() OVER() as rownum from POST WHERE ID_USER = ? AND ID_GROUP = ? order by DATE_POST DESC ) as tmp where rownum = 1");
            stm.setInt(1, idUser);
            stm.setInt(2, idGroup);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    d = rs.getDate("DATE_POST");
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return d;
    }

    /**
     * Create a list of reports for a Group
     *
     * @param idGroup
     * @return The list of reports
     */
    public List<UserReport> getGroupReport(int idGroup)
    {
        PreparedStatement stm = null;
        ArrayList<UserReport> userList = new ArrayList<>();
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("SELECT username, PWEB.POST.ID_USER, COUNT(PWEB.POST.ID_POST) as PostNumber "
                    + "FROM PWEB.POST INNER JOIN PWEB.USERS ON PWEB.POST.ID_USER = PWEB.USERS.ID_USER "
                    + "WHERE ID_GROUP = ? GROUP BY username, PWEB.POST.ID_USER");
            stm.setInt(1, idGroup);
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                while (rs.next())
                {
                    UserReport up = new UserReport(rs.getString("username"), rs.getInt("ID_USER"), null, rs.getInt("PostNumber"));
                    userList.add(up);
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        for (int i = 0; i < userList.size(); i++)
        {
            UserReport ur = userList.get(i);
            ur.setLastPost(getLastPostForUserInGroup(ur.getIdUser(), idGroup));
            userList.set(i, ur);
        }
        return userList;
    }

    /**
     * Adds an invite for a User from a Group
     *
     * @param usr
     * @param g
     */
    public void addInvite(Group g, User usr)
    {

        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("INSERT INTO PWEB.INVITE (ID_GROUP, ID_USER, INVITE_DATE, VISIBLE) VALUES (?, ?, CURRENT_DATE,true)");
            stm.setInt(1, g.getId());
            stm.setInt(2, usr.getId());

            int res = stm.executeUpdate();
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Updates group informations
     *
     * @param idGroup
     * @param groupName
     */
    public void updateGroup(int idGroup, String groupName)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("Update PWEB.GROUPS SET NAME=? where id_group=?");
            stm.setString(1, groupName);
            stm.setInt(2, idGroup);
            try
            {
                stm.executeUpdate();
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing update query", sqlex);
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
    }

    /**
     * Check if a User owns the given Group
     *
     * @param usr
     * @param grp
     * @return true if the user is the owner of the given group else false
     */
    public boolean isGroupOwner(User usr, Group grp)
    {
        return doesUserBelongsToGroup(usr, grp.getId());
    }

    /**
     * Check if a User owns the given Group
     *
     * @param usr
     * @param id_group
     * @return true if the user is the owner of the given group else false
     */
    public boolean isGroupOwner(User usr, Integer id_group)
    {
        PreparedStatement stm = null;
        try
        {
            if (_connection == null || _connection.isClosed())
            {
                throw new RuntimeException("Connection must be estabilished before a statement");
            }
            stm = _connection.prepareStatement("select * from groups where id_group=? and id_owner=?");
            stm.setInt(1, id_group);
            stm.setInt(2, usr.getId());
            ResultSet rs = null;

            try
            {
                rs = stm.executeQuery();
                if (rs.next())
                {
                    return true;
                }
            }
            catch (SQLException sqlex)
            {
                Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while executing query or parsing result data", sqlex);
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        }
        catch (SQLException | RuntimeException ex)
        {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, 
                    "Error while creating query or establishing database connection", ex);
        }
        finally
        {
            if (stm != null)
            {
                try
                {
                    stm.close();
                }
                catch (SQLException sex)
                {
                    Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, "Error while closing connection", sex);
                }
            }
        }
        return false;
    }
}