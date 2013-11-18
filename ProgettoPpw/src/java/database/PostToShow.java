/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

/**
 *
 * @author lorenzo
 */
public class PostToShow {

    public PostToShow(String _datePost, String _message, String _username) {
        this._datePost = _datePost;
        this._message = _message;
        this._username = _username;
    }

    public String getDatePost() {
        return _datePost;
    }

    public void setDatePost(String _datePost) {
        this._datePost = _datePost;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String _message) {
        this._message = _message;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }
    
 
    
    private String _datePost;
    private String _message;
    private String _username;
}
