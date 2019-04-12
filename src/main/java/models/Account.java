/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import utils.PasswordSecurity;

/**
 *
 * @author teren
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")
    ,
    @NamedQuery(name = "account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username")
    ,
    @NamedQuery(name = "account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 1024)
    private String accountPassword;
    private String location;
    private String website;
    private String picture;

    @Column(length = 160)
    private String bio;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "account")
    @JsonbTransient
    private List<Tweet> tweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "followers_following")
    @JsonbTransient
    @JsonIgnore
    private List<Account> following = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, mappedBy = "following")
    @JsonbTransient
    @JsonIgnore
    private List<Account> followers = new ArrayList<>();

    @ManyToOne
    private Role role;

    /**
     *
     */
    public Account() {
    }

    /**
     *
     * @param email
     * @param username
     * @param password
     */
    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        try {
            this.accountPassword = PasswordSecurity.generateSha256(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    /**
     * @return the tweets
     */
    public List<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Add a Tweet to an User
     *
     * @param tweet Tweet to add
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    /**
     * @param tweets the tweets to set
     */
    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    /**
     * @return the following
     */
    public List<Account> getFollowing() {
        return following;
    }

    /**
     * Add a following User
     *
     * @param user Following User to add
     */
    public void addFollowing(Account user) {
        following.add(user);
    }

    /**
     * Remove a following User
     *
     * @param user
     */
    public void removeFollowing(Account user) {
        following.remove(user);
    }

    /**
     * @param following the following to set
     */
    public void setFollowing(List<Account> following) {
        this.following = following;
    }

    /**
     * @return the followers
     */
    public List<Account> getFollowers() {
        return followers;
    }

    /**
     * Add a Follower to a User
     *
     * @param follower Follower to add
     */
    public void addFollower(Account follower) {
        followers.add(follower);
    }

    /**
     * Remove a Follower of a User
     *
     * @param follower
     */
    public void removeFollower(Account follower) {
        followers.remove(follower);
    }

    /**
     * @param followers the followers to set
     */
    public void setFollowers(List<Account> followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     *
     * @param accountPassword
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    /**
     *
     * @return
     */
    public Role getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     *
     * @return
     */
    public String getPicture() {
        return picture;
    }

    /**
     *
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
