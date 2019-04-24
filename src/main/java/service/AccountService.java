/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IAccountDao;
import dao.IRoleDao;
import dao.JPA;
import exceptions.AccountException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import models.Account;
import utils.JwtUtil;
import utils.PasswordSecurity;

/**
 *
 * @author teren
 */
@Stateless
public class AccountService {

    @Inject
    @JPA
    private IAccountDao accountDao;

    @Inject
    private RoleService roleService;

    @Inject
    @JPA
    private IRoleDao roleDao;

    private JwtUtil jwt;

    public AccountService() {
        super();
        jwt = new JwtUtil();
    }

    @PermitAll
    public Account create(Account entity) throws AccountException {
        if (entity.getRole() == null) {
            entity.setRole(roleService.getRoleByName("User"));
        }

        if (entity.getPicture() == null) {
            entity.setPicture("https://i.pinimg.com/originals/9f/81/2d/9f812d4cf313e887ef99d8722229eee1.jpg");
        }

        accountDao.create(entity);
        return entity;
    }

    @RolesAllowed({"Admin"})
    public void deleteById(long id) throws AccountException {
        Account entity = accountDao.findById(id);
        accountDao.delete(entity);
    }

    @PermitAll
    public void update(Account entity) throws Exception {
        accountDao.update(entity);
    }

    @RolesAllowed({"User", "Admin", "Moderator"})
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

    @PermitAll
    public void addFollowing(long followingId, long id) throws AccountException, IOException {
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

    @PermitAll
    public void removeFollowing(long followerId, long id) throws AccountException, IOException {
        if (followerId != id) {
            System.out.println("UNFOLLOWING 1");
            Account account = accountDao.findById(id);
            Account followingUser = accountDao.findById(followerId);

            if (account != null && followingUser != null) {
                System.out.println("UNFOLLOWING 2");

                account.removeFollowing(followingUser);
                followingUser.removeFollower(account);

                accountDao.update(account);
                accountDao.update(followingUser);
            }
        }
    }

    @PermitAll
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @PermitAll
    public Account login(String username, String password) throws NoSuchAlgorithmException {
        Account getAccountByUsername = this.accountDao.findByUsername(username);
        String passwordCheck = PasswordSecurity.generateSha256(password);
        if (passwordCheck.equals(getAccountByUsername.getAccountPassword())) {
            return getAccountByUsername;
        } else {
            return null;
        }
    }

    @PermitAll
    public List<Account> getFollowers(Long id) {
        return accountDao.findFollowers(id);
    }

    @PermitAll
    public List<Account> getFollowing(Long id) {
        return accountDao.findFollowing(id);
    }

    @PermitAll
    public List<Account> search(String username) {
        return accountDao.search(username);
    }
}
