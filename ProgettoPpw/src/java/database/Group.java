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
public class Group {
    private Integer _id;
    private String _name;
    private boolean _active;
    private int _owner;

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
     * @return the _name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param _name the _name to set
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * @return the _active
     */
    public boolean isActive() {
        return _active;
    }

    /**
     * @param _active the _active to set
     */
    public void setActive(boolean _active) {
        this._active = _active;
    }

    /**
     * @return the _owner
     */
    public int getOwner() {
        return _owner;
    }

    /**
     * @param _owner the _owner to set
     */
    public void setOwner(Integer _owner) {
        this._owner = _owner;
    }

    

    
}
