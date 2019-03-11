/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IAccountDao;
import dao.JPA;
import exceptions.AccountException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import models.Account;

/**
 *
 * @author teren
 */
@Stateless
public class AccountService {

    @Inject
    @JPA
    private IAccountDao accountDao;

    public AccountService() {
        super();
    }

    @PermitAll
    public Account create(Account entity) throws AccountException {
        return accountDao.create(entity);
    }

    @RolesAllowed({"ADMIN"})
    public void delete(long id) throws AccountException {
        Account entity = accountDao.findById(id);
        accountDao.delete(entity);
    }

    @RolesAllowed({"USER", "ADMIN", "MODERATOR"})
    public void update(Account entity) throws Exception {
        Account user = accountDao.findById(entity.getId());
        accountDao.update(user);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void updateUsername(String username, Account user) throws AccountException {
        if (accountDao.findByUsername(username) == null && !username.isEmpty()) {
            user.setUsername(username);
            accountDao.update(user);
        }
    }

    @PermitAll
    public Account findById(long id) {
        return accountDao.findById(id);
    }

    @PermitAll
    public Account findByUsername(String username) {
        return accountDao.findByUsername(username);
    }

    @PermitAll
    public Account findByEmail(String email) {
        return accountDao.findByEmail(email);
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void addFollowing(long followingId, long id) throws AccountException {
        if (followingId != id) {
            Account account = accountDao.findById(id);
            Account followingUser = accountDao.findById(followingId);
            if (account != null && followingUser != null) {

                account.addFollowing(followingUser);
                followingUser.addFollower(account);

                accountDao.update(account);
                accountDao.update(followingUser);
            }
        }
    }

    @RolesAllowed({"USER", "ADMIN"})
    public void removeFollowing(long followingId, long id) throws AccountException {
        if (followingId != id) {
            Account user = accountDao.findById(id);
            Account followingUser = accountDao.findById(followingId);
            if (user != null && followingUser != null) {
                user.removeFollowing(followingUser);
                followingUser.removeFollower(user);

                accountDao.update(user);
                accountDao.update(followingUser);
            }
        }
    }

    @PermitAll
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}
