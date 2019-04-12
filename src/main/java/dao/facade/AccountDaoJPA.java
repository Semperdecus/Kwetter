/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.IAccountDao;
import dao.JPA;
import exceptions.AccountException;
import exceptions.TweetException;
import java.io.Serializable;
import models.Account;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Role;

/**
 *
 * @author teren
 */
@JPA
@Stateless
public class AccountDaoJPA implements IAccountDao {

    @PersistenceContext(name = "persistence/kwetterPU", unitName = "kwetterPU")
    private EntityManager entityManager;

    public AccountDaoJPA() {
        super();
    }

    // Extra constructor to define entitymanager to test DAO layer in mySQL test database
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
        if (result.size() > 0) {
            return result.get(0);
        } else {
            System.out.println("No user found for: " + username);
            return null;
        }
    }

    @Override
    public List<Account> findAll() {
        Query query = entityManager.createQuery("SELECT a FROM Account a");
        List<Account> result = query.getResultList();
        return result;
    }

    @Override
    public Account create(Account entity) throws AccountException {
        checkCreate(entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Account update(Account entity) {
        return entityManager.merge(entity);
    }

    private void checkCreate(Account entity) throws AccountException {
        if (entity.getUsername().length() > 20 || entity.getUsername().length() < 0 || entity.getUsername().isEmpty()) {
            throw new AccountException("Username has an invalid length");
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

    @Override
    public void delete(Account entity) {
        entityManager.remove(entityManager.merge(entity));
    }
}
