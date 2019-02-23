/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author teren
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
})
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    
    private String accountRole;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    
    @Column(nullable = false, length = 20)
    private String accountPassword;
    private String location;
    private String website;
    
    @Column(length = 160)
    private String bio;
    
    public Account() {
    }

    /**
     *
     * @param role
     * @param email
     * @param username
     * @param password
     */
    public Account(Role role, String email, String username, String password) {
        //TODO email, accountPassword, website, username, bio string validations
        this.accountRole = role.toString();
        this.email = email;
        this.username = username;
        this.accountPassword = password;
        this.location = "";
        this.bio = "";
        this.website = "";
        this.location = "";
    }
    
    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Role getRole() {
        if(accountRole.equalsIgnoreCase(Role.USER.toString())) {
            return Role.USER;
        } else if (accountRole.equalsIgnoreCase(Role.ADMIN.toString())) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }

    /**
     *
     * @param role
     */
    public void setRole(String role) {
        this.accountRole = role;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return accountPassword;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.accountPassword = password;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param websiteUrl
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.website = websiteUrl;
    }

    /**
     *
     * @return
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
    
}
