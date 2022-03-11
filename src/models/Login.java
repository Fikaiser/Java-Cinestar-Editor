/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author RnaBo
 */
public class Login {
    
    private String username;
    private Boolean admin;

    public String getUsername() {
        return username;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public Login(String Username, Boolean Admin) {
        this.username = Username;
        this.admin = Admin;
    }
    
}
