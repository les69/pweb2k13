/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.util.Date;

/**
 *
 * @author Lorenzo
 */
public class UserReport {
    private String _username;
    private int _idUser;

    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public int getIdUser() {
        return _idUser;
    }

    public void setIdUser(int _idUser) {
        this._idUser = _idUser;
    }

    public Date getLastPost() {
        return _lastPost;
    }

    public void setLastPost(Date _lastPost) {
        this._lastPost = _lastPost;
    }

    public int getPostNumber() {
        return _postNumber;
    }

    public void setPostNumber(int _postNumber) {
        this._postNumber = _postNumber;
    }

    public UserReport(String _username, int _idUser, Date _lastPost, int _postNumber) {
        this._username = _username;
        this._idUser = _idUser;
        this._lastPost = _lastPost;
        this._postNumber = _postNumber;
    }
    private Date _lastPost;
    private int _postNumber;
    
}
