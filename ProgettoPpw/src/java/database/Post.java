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
    private Integer _id;
    private boolean _visible;
    private Date _datePost;
    private String _message;
    private Integer _idGroup;
    private Integer _idUser;

    /**
     * @return the _id
     */
    public Integer getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(Integer _id) {
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
     * @return the _idGroup
     */
    public Integer getIdGroup() {
        return _idGroup;
    }

    /**
     * @param _idGroup the _idGroup to set
     */
    public void setIdGroup(Integer _idGroup) {
        this._idGroup = _idGroup;
    }

    /**
     * @return the _idUser
     */
    public Integer getIdUser() {
        return _idUser;
    }

    /**
     * @param _idUser the _idUser to set
     */
    public void setIdUser(Integer _idUser) {
        this._idUser = _idUser;
    }

   }
