/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.ITweetDao;
import java.util.List;
import javax.persistence.EntityManager;
import models.Tweet;

/**
 *
 * @author teren
 */
public class TweetDaoJPA implements ITweetDao{
    private EntityManager entityManager;

    @Override
    public Tweet findById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tweet> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
