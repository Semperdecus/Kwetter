/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IAccountDao;
import dao.ITweetDao;
import dao.JPA;
import exceptions.AccountException;
import exceptions.TweetException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import models.Account;
import models.Tweet;

/**
 *
 * @author teren
 */
@Stateless
public class TweetService {

    @Inject
    @JPA
    private ITweetDao tweetDao;

    @Inject
    @JPA
    private IAccountDao accountDao;

    public TweetService() {
        super();
    }

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

    @RolesAllowed({"Admin", "Moderator"})
    public void delete(Tweet tweet) throws TweetException {
        tweetDao.delete(tweet);
    }

    @PermitAll
    public void deleteOwnTweet(long id) throws TweetException {
        Tweet entity = tweetDao.findById(id);
        if (entity != null) {
            tweetDao.delete(entity);
            return;
        }
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
    public List<Tweet> findByAccountId(Account account) {
        return tweetDao.findByAccountId(account);
    }

    @PermitAll
    public List<Tweet> findAll() {
        return tweetDao.findAll();
    }

    @PermitAll
    public List<Tweet> getFollowingTweets(Long id) {
        return tweetDao.getFollowingTweets(id);
    }

    @PermitAll
    public List<Tweet> search(String message) {
        return tweetDao.search(message);
    }
}
