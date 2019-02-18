/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.IAccountDao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account findByEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account findByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Account> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
