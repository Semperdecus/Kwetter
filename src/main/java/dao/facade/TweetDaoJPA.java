/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.ITweetDao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Tweet;

/**
 *
 * @author teren
 */
public class TweetDaoJPA implements ITweetDao{
    private EntityManager entityManager;

    @Override
    public Tweet findById(long id) {
        TypedQuery<Tweet> query = entityManager.createNamedQuery("tweet.findById", Tweet.class);
        query.setParameter("id", id);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public List<Tweet> findAll() {
         Query query = entityManager.createQuery("SELECT t FROM Tweet t");
         return  new ArrayList<>(query.getResultList());
    }

    @Override
    public Tweet create(Tweet entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tweet update(Tweet entity) {
	return entityManager.merge(entity);
    }

    @Override
    public void delete(Tweet entity) {
        entityManager.remove(entityManager.merge(entity));
    }
    
}
