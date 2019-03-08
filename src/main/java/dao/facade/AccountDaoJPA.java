/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.IAccountDao;
import exceptions.AccountException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Account;

/**
 *
 * @author teren
 */
@JPA
@Stateless
public class AccountDaoJPA extends AbstractJPADao<Account> implements IAccountDao {

    @PersistenceContext(unitName = "kwetterPU")
    private EntityManager entityManager;
    private EntityTransaction transaction;

    public AccountDaoJPA() {
        super();
        setClassObj(Account.class);
    }

    public AccountDaoJPA(EntityManager entityManager) {
        super();
        setClassObj(Account.class);
        this.entityManager = entityManager;
        transaction = entityManager.getTransaction();
    }

    @Override
    public Account findById(long id) {
        transaction.begin();
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findById", Account.class);
        query.setParameter("id", id);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result.get(0);
    }

    @Override
    public Account findByEmail(String email) {
        transaction.begin();
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findByEmail", Account.class);
        query.setParameter("email", email);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result.get(0);
    }

    @Override
    public Account findByUsername(String username) {
        transaction.begin();
        TypedQuery<Account> query = entityManager.createNamedQuery("account.findByUsername", Account.class);
        query.setParameter("username", username);
        List<Account> result = query.getResultList();
        System.out.println("count: " + result.size());
        transaction.commit();
        return result.get(0);
    }

    @Override
    public List<Account> findAll() {
        transaction.begin();
        Query query = entityManager.createQuery("SELECT a FROM Account a");
        List<Account> result = query.getResultList();
        transaction.commit();
        return result;
    }

    @Override
    public Account create(Account entity) throws AccountException {
        transaction.begin();
        checkCreate(entity);
        entityManager.persist(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public Account update(Account entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Account entity) {
        transaction.begin();
        entityManager.remove(entityManager.merge(entity));
        transaction.commit();
    }

    private void checkCreate(Account entity) throws AccountException {
        if (entity.getUsername().length() > 20 || entity.getUsername().length() < 0 || entity.getUsername().isEmpty()) {
            throw new AccountException("Username has an invalid length");
        }

        if (entity.getPassword().length() > 20 || entity.getPassword().length() < 0 || entity.getPassword().isEmpty()) {
            throw new AccountException("Password has an invalid length");
        }

        if (!isValidEmail(entity.getEmail()) || entity.getEmail().isEmpty() || entity.getEmail().length() < 0) {
            throw new AccountException("Email has invalid format or invalid length");
        }
    }

    private boolean isValidEmail(String email) {
        System.out.println("email: " + email);
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
