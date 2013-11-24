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
public class FileDB {
    private Integer id_group;
    private Integer id_user;
    private String original_name;
    private String hashed_name;
    private String type;

    /**
     * @return the id_group
     */
    public Integer getId_group() {
        return id_group;
    }

    /**
     * @param id_group the id_group to set
     */
    public void setId_group(Integer id_group) {
        this.id_group = id_group;
    }

    /**
     * @return the id_user
     */
    public Integer getId_user() {
        return id_user;
    }

    /**
     * @param id_user the id_user to set
     */
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    /**
     * @return the original_name
     */
    public String getOriginal_name() {
        return original_name;
    }

    /**
     * @param original_name the original_name to set
     */
    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    /**
     * @return the hashed_name
     */
    public String getHashed_name() {
        return hashed_name;
    }

    /**
     * @param hashed_name the hashed_name to set
     */
    public void setHashed_name(String hashed_name) {
        this.hashed_name = hashed_name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
}