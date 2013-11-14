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
public class Post {
    private int _id;
    private boolean _visible;
    private Date _datePost;
    private String _message;
    private String _fileString;
    private int _idGroup;
    private int _idUser;

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
     * @return the _visible
     */
    public boolean isVisible() {
        return _visible;
    }

    /**
     * @param _visible the _visible to set
     */
    public void setVisible(boolean _visible) {
        this._visible = _visible;
    }

    /**
     * @return the _datePost
     */
    public Date getDatePost() {
        return _datePost;
    }

    /**
     * @param _datePost the _datePost to set
     */
    public void setDatePost(Date _datePost) {
        this._datePost = _datePost;
    }

    /**
     * @return the _message
     */
    public String getMessage() {
        return _message;
    }

    /**
     * @param _message the _message to set
     */
    public void setMessage(String _message) {
        this._message = _message;
    }

    /**
     * @return the file_string
     */
    public String getFile_string() {
        return _fileString;
    }

    /**
     * @param file_string the file_string to set
     */
    public void setFile_string(String file_string) {
        this._fileString = file_string;
    }

    /**
     * @return the id_group
     */
    public int getId_group() {
        return _idGroup;
    }

    /**
     * @param id_group the id_group to set
     */
    public void setId_group(int id_group) {
        this._idGroup = id_group;
    }

    /**
     * @return the id_user
     */
    public int getId_user() {
        return _idUser;
    }

    /**
     * @param id_user the id_user to set
     */
    public void setId_user(int id_user) {
        this._idUser = id_user;
    }
}
