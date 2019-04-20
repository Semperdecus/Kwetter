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
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.Stateful;
import javax.enterprise.inject.Default;
import models.Account;

/**
 *
 * @author teren
 */
@Default
@Stateful
public class AccountDaoColl implements IAccountDao {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public Account findById(long id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        for (Account account : accounts) {
            if (account.getEmail().contentEquals(email)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().contentEquals(username)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public Account create(Account entity) throws AccountException {
        accounts.add(entity);
        return entity;
    }

    @Override
    public Account update(Account account) {
        int index = accounts.indexOf(account.getId());
        return accounts.set(index, account);
    }

    @Override
    public void delete(Account entity) {
        accounts.remove(entity);
    }

    @Override
    public List<Account> findFollowing(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Account> findFollowers(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
