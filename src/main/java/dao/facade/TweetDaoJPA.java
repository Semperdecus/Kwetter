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
public class TweetDaoJPA extends AbstractJPADao<Tweet> implements ITweetDao {

    @PersistenceContext(name = "persistence/kwetterPU", unitName = "kwetterPU")
    private EntityManager entityManager;
    private EntityTransaction transaction;

    public TweetDaoJPA() {
        super();
        setClassObj(Tweet.class);
    }

    public TweetDaoJPA(EntityManager entityManager) {
        super();
        setClassObj(Tweet.class);

        this.entityManager = entityManager;
        transaction = entityManager.getTransaction();
    }

    @Override
    public Tweet findById(long id) {
        transaction.begin();
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findById", Tweet.class);
        query.setParameter("id", id);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result.get(0);
    }

    @Override
    public List<Tweet> findByMessage(String message) {
        transaction.begin();
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findByMessage", Tweet.class);
        query.setParameter("message", message);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result;
    }

    @Override
    public List<Tweet> findByUsername(String username) {
        transaction.begin();
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findByUsername", Tweet.class);
        query.setParameter("username", username);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result;
    }

    @Override
    public List<Tweet> findAll() {
        transaction.begin();
        Query query = entityManager.createQuery("SELECT t FROM Tweet t");
        List<Tweet> result = query.getResultList();
        transaction.commit();
        return result;
    }

    @Override
    public Tweet create(Tweet entity) throws TweetException {
        transaction.begin();
        checkCreate(entity);
        entityManager.persist(entity);
        transaction.commit();
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
        if (adminAccount.getRole() == Role.ADMIN) {
            transaction.begin();
            entityManager.remove(entityManager.merge(entity));
            transaction.commit();
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
