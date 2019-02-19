/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.IAccountDao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Account;

/**
 *
 * @author teren
 */
public class AccountDaoJPA implements IAccountDao {

    @PersistenceContext(unitName ="KwetterPU")
    private EntityManager entityManager;
        
    public AccountDaoJPA(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }
        
    @Override
    public Account findById(long id) {
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findById", Account.class);
        query.setParameter("id", id);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public Account findByEmail(String email) {
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findByEmail", Account.class);
        query.setParameter("email", email);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public Account findByUsername(String username) {
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findByUsername", Account.class);
        query.setParameter("username", username);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);    
    }

    @Override
    public List<Account> findAll() {
         Query query = entityManager.createQuery("SELECT a FROM Account a");
         return  new ArrayList<>(query.getResultList());
    }

    @Override
    public Account create(Account entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Account update(Account entity) {
	return entityManager.merge(entity);
    }

    @Override
    public void delete(Account entity) {
        entityManager.remove(entityManager.merge(entity));
    }
    
}
