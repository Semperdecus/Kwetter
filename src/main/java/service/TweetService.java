/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IAccountDao;
import dao.ITweetDao;
import dao.facade.JPA;
import exceptions.AccountException;
import exceptions.TweetException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import models.Account;
import models.Role;
import models.Tweet;

/**
 *
 * @author teren
 */
public class TweetService {

    @Inject @JPA
    private ITweetDao tweetDao;

    @Inject @JPA
    private IAccountDao accountDao;

    public TweetService() {
        super();
    }

    @RolesAllowed({"USER", "ADMIN"})
    public Tweet create(Tweet tweet) throws AccountException, TweetException {
        Account account = accountDao.findById(tweet.getAccount().getId());
        if (account != null) {
            tweet = tweetDao.create(tweet);
            account.addTweet(tweet);
            accountDao.update(account);
            return tweet;
        }
        return null;
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void delete(long id, Account account) throws TweetException, AccountException {
        Tweet entity = tweetDao.findById(id);
        if (entity != null) {
            if (entity.getAccount().getId().equals(account.getId())) {
                tweetDao.delete(entity, entity.getAccount());
                return;
            }
        }
        throw new AccountException("Account not found.");
    }

    @PermitAll
    public Tweet findById(long id) {
        return tweetDao.findById(id);
    }

    @PermitAll
    public List<Tweet> findByMessage(String message) {
        return tweetDao.findByMessage(message);
    }

    @PermitAll
    public List<Tweet> findByUser(String username) {
        return tweetDao.findByUsername(username);
    }

    @PermitAll
    public List<Tweet> findAll() {
        return tweetDao.findAll();
    }
}
