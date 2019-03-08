/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.ITweetDao;
import exceptions.TweetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import models.Account;
import models.Role;
import models.Tweet;

/**
 *
 * @author teren
 */
public class TweetDaoColl implements ITweetDao {

    CopyOnWriteArrayList<Tweet> tweets = new CopyOnWriteArrayList<>();

    @Override
    public Tweet findById(long id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId().equals(id)) {
                return tweet;
            }
        }
        return null;
    }

    @Override
    public List<Tweet> findByMessage(String message) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getMessage().contains(message)) {
                result.add(tweet);
            }
        }

        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public List<Tweet> findByUsername(String username) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAccount().getUsername().contentEquals(username)) {
                result.add(tweet);
            }
        }

        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(long id, Account adminAccount) throws TweetException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tweet> findAll() {
        return new ArrayList<>(tweets);
    }

    @Override
    public Tweet create(Tweet entity) throws TweetException {
        tweets.add(entity);
        return entity;
    }

    @Override
    public Tweet update(Tweet entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Tweet entity, Account adminAccount) throws TweetException {
        if (adminAccount.getRole() == Role.ADMIN) {
            tweets.remove(entity);
        } else {
            throw new TweetException("Account does not have permissions to delete tweet");
        }
    }
}
