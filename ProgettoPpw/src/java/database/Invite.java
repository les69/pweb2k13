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
public class Invite {
    private int _idGroup;
    private int _idUser;
    private Date _inviteDate;

    /**
     * @return the _idGroup
     */
    public int getIdGroup() {
        return _idGroup;
    }

    /**
     * @param _idGroup the _idGroup to set
     */
    public void setIdGroup(int _idGroup) {
        this._idGroup = _idGroup;
    }

    /**
     * @return the _idUser
     */
    public int getIdUser() {
        return _idUser;
    }

    /**
     * @param _idUser the _idUser to set
     */
    public void setIdUser(int _idUser) {
        this._idUser = _idUser;
    }

    /**
     * @return the _inviteDate
     */
    public Date getInviteDate() {
        return _inviteDate;
    }

    /**
     * @param _inviteDate the _inviteDate to set
     */
    public void setInviteDate(Date _inviteDate) {
        this._inviteDate = _inviteDate;
    }
}
