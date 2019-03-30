/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.AbstractJPADao;
import dao.ITweetDao;
import dao.JPA;
import exceptions.TweetException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Account;
import models.Role;
import models.Tweet;

/**
 *
 * @author teren
 */
@JPA
@Stateless
public class TweetDaoJPA extends AbstractJPADao<Serializable> implements ITweetDao {

    @PersistenceContext(name = "persistence/kwetterPU", unitName = "kwetterPU")
    private EntityManager entityManager;

    public TweetDaoJPA() {
        super();
    }

    // Extra constructor to define entitymanager to test DAO layer in mySQL test database
    public TweetDaoJPA(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public Tweet findById(long id) {
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findById", Tweet.class);
        query.setParameter("id", id);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public List<Tweet> findByMessage(String message) {
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findByMessage", Tweet.class);
        query.setParameter("message", message);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result;
    }

    @Override
    public List<Tweet> findByUsername(String username) {
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findByUsername", Tweet.class);
        query.setParameter("username", username);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result;
    }

    @Override
    public List<Tweet> findAll() {
        Query query = entityManager.createQuery("SELECT t FROM Tweet t");
        List<Tweet> result = query.getResultList();
        return result;
    }

    @Override
    public Tweet create(Tweet entity) throws TweetException {
        checkCreate(entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tweet update(Tweet entity) {
        return entityManager.merge(entity);
    }

    public void deleteById(long id, Account adminAccount) throws TweetException {
        final Tweet entity = findById(id);

        if (entity == null) {
            throw new TweetException("Tweet is not found.");
        } else {
            delete(entity, adminAccount);
        }
    }

    @Override
    public void delete(Tweet entity, Account adminAccount) throws TweetException {
        if (adminAccount.getRole().getRole_name() == "ADMIN") {
            entityManager.remove(entityManager.merge(entity));
        } else {
            throw new TweetException("Account does not have permissions to delete tweet");
        }
    }

    private void checkCreate(Tweet entity) throws TweetException {
        if (entity.getMessage().length() < 0 || entity.getMessage().isEmpty() || entity.getMessage().length() > 140) {
            throw new TweetException("Tweet has invalid length");
        }
    }
}
