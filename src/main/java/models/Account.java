/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author teren
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "account.findByUserame", query = "SELECT a FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
})
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    
    private String role;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    
    @Column(nullable = false, length = 20)
    private String password;
    private String location;
    private String website;
    
    @Column(length = 160)
    private String bio;

    private List<Tweet> tweets = new ArrayList<>();
    private List<Account> following = new ArrayList<>();
    private List<Account> followers = new ArrayList<>();
    
    public Account() {
        this.role = Role.USER.toString();
        this.bio = "";
        this.website = "";
        this.location = "";
    }

    /**
     *
     * @param role
     * @param email
     * @param username
     * @param password
     */
    public Account(Role role, String email, String username, String password) {
        //TODO email, password, website, username, bio string validations
        this.role = role.toString();
        this.email = email;
        this.username = username;
        this.password = password;
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
        if(role.equalsIgnoreCase(Role.USER.toString())) {
            return Role.USER;
        } else if (role.equalsIgnoreCase(Role.ADMIN.toString())) {
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
        this.role = role;
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
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
    
    
    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
   
    public void addTweets(Tweet tweet) {
        tweets.add(tweet);
    }

    public List<Account> getFollowing() {
        return following;
    }

    public void setFollowing(List<Account> following) {
        this.following = following;
    }
    
    public void addFollowing(Account account) {
        following.add(account);
    }       

    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Account> followers) {
        this.followers = followers;
    }
    
    public void addFollowers(Account account) {
        followers.add(account);
    }    
}
