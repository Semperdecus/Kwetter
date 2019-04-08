/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import exceptions.AccountException;
import exceptions.TweetException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.AccessLocalException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import models.Account;
import models.Tweet;
import service.AccountService;
import service.TweetService;
import utils.EmailUtil;
import utils.RedirectUtil;

/**
 *
 * @author teren
 */
@Named(value = "tweetBean")
@SessionScoped
public class TweetBean implements Serializable {

    @Inject
    private AccountService accountService;

    @Inject
    private TweetService tweetService;
    @Inject
    private EmailUtil emailBean;
    private Account account;

    private List<Tweet> tweets;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void showTweets(String username) {
        this.findAccount(username);
        RedirectUtil.redirect("/pages/admin/usertweet.xhtml");

    }

    public void findAccount(String username) {
        Account a = accountService.findByUsername(username);
        this.account = a;
        this.tweets = this.tweetService.findByUser(account.getUsername());
    }

    public void deleteTweet(Tweet tweet) throws TweetException, MessagingException, UnsupportedEncodingException {
        try {
            this.tweetService.delete(tweet);
            emailBean.sendEmail_tweetDeleted(tweet.getAccount());
            RedirectUtil.redirect("/pages/admin/usertweet.xhtml");
        } catch (AccessLocalException e) {
            System.out.println("NO PERMISSION");
            e.printStackTrace();
        }
    }
}
