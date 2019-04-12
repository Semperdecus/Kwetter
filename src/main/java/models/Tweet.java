/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author teren
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "tweet.findById", query = "SELECT t FROM Tweet t WHERE t.id = :id")
    ,
    @NamedQuery(name = "tweet.findByMessage", query = "SELECT t FROM Tweet t WHERE t.message = :message")
    ,
    @NamedQuery(name = "tweet.findByAccount", query = "SELECT t FROM Tweet t WHERE t.account = :account")
    ,
 @NamedQuery(name = "tweet.findByUsername", query = "SELECT t FROM Account a, Tweet t WHERE a.username = :username")})
public class Tweet implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 140, nullable = false)
    private String message;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JsonbTransient
    private Account account;

    /**
     *
     */
    public Tweet() {
    }

    /**
     *
     * @param message
     * @param account
     */
    public Tweet(String message, Account account) {
        this.message = message;
        this.account = account;
        this.date = new Date();
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
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public Account getAccount() {
        return account;
    }

    /**
     *
     * @param account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
