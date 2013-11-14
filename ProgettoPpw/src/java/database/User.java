/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

/**
 *
 * @author les
 */
public class User {
    private int _id;
    private String _password;
    private String _username;

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(int _id) {
        this._id = _id;
    }

    /**
     * @return the _password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * @param _password the _password to set
     */
    public void setPassword(String _password) {
        this._password = _password;
    }

    /**
     * @return the _username
     */
    public String getUsername() {
        return _username;
    }

    /**
     * @param _username the _username to set
     */
    public void setUsername(String _username) {
        this._username = _username;
    }
    
}
