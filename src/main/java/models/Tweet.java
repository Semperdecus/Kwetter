/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author teren
 */
@Entity
public class Tweet implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(length = 140, nullable = false)
    private String message;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
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
