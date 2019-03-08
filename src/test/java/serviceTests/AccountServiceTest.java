/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceTests;

import dao.IAccountDao;
import models.Account;
import models.Role;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import service.AccountService;

/**
 *
 * @author teren
 */
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private IAccountDao accountDao;

    public AccountServiceTest() {
    }

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void ServiceTest() {
        assertNotNull(accountService);
    }

    /*
    Case 1 - Existing Username
     */
    @Test
    public void createTest1() throws Exception {

        Account account = new Account(Role.USER, "email@mail.com", "username", "password");
        account.setId(1l);

        when(accountDao.findByUsername(account.getUsername())).thenReturn(null);
        when(accountDao.findByEmail(account.getEmail())).thenReturn(null);

        accountService.create(account);
        verify(accountDao, atLeastOnce()).create(account);
    }

    /*
    Case 2 - Existing Email
     */
    @Test
    public void createTest2() throws Exception {
        Account account = new Account(Role.USER, "email@mail.com", "username", "password");
        account.setId(1l);

        when(accountDao.findByUsername(account.getUsername())).thenReturn(null);
        when(accountDao.findByEmail(account.getEmail())).thenReturn(account);

        accountService.create(account);
        verify(accountDao, never()).create(account);
    }

    /*
    Case 3 - Update username
     */
    @Test
    public void updateUsernameTest1() throws Exception {
        Account user = new Account(Role.USER, "user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "username2";

        when(accountDao.findById(1l)).thenReturn(user);
        when(accountDao.findByUsername(newUsername)).thenReturn(null);

        accountService.updateUsername(newUsername, user);
        verify(accountDao, atLeastOnce()).update(user);
    }

    /*
    Case 4 - Update empty username
     */
    @Test
    public void updateUsernameTest2() throws Exception {
        Account user = new Account(Role.USER, "user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "";

        when(accountDao.findById(1l)).thenReturn(user);
        when(accountDao.findByUsername(newUsername)).thenReturn(null);

        accountService.updateUsername(newUsername, user);
        verify(accountDao, never()).update(user);
    }

    /*
    Case 5 - Update to existing username
     */
    @Test
    public void updateUsernameTest3() throws Exception {
        Account user = new Account(Role.USER, "user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "";

        when(accountDao.findById(1l)).thenReturn(user);
        when(accountDao.findByUsername(newUsername)).thenReturn(user);

        accountService.updateUsername(newUsername, user);
        verify(accountDao, never()).update(user);
    }

    /*
    Case 6 - add user to following
     */
    @Test
    public void addFollowingTest1() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        Account user2 = new Account(Role.USER, "user510@mail.com", "user510", "password");
        user1.setId(1l);
        user2.setId(2l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(2l)).thenReturn(user2);

        accountService.addFollowing(user1.getId(), user2.getId());

        verify(accountDao, atLeastOnce()).update(user1);
        verify(accountDao, atLeastOnce()).update(user2);
    }

    /*
    Case 7 - add yourself to following
     */
    @Test
    public void addFollowingTest2() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);

        accountService.addFollowing(user1.getId(), user1.getId());
        verify(accountDao, never()).update(user1);
    }

    /*
    Case 8 - add non existing account to following
     */
    @Test
    public void addFollowingTest3() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(3l)).thenReturn(null);

        accountService.addFollowing(user1.getId(), 3l);
        verify(accountDao, never()).update(user1);
    }
    /*
    Case 9 - remove follower
     */
    @Test
    public void removeFollowingTest1() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        Account user2 = new Account(Role.USER, "user510@mail.com", "user510", "password");
        user1.setId(1l);
        user2.setId(2l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(2l)).thenReturn(user2);

        accountService.removeFollowing(user1.getId(), user2.getId());
        verify(accountDao, atLeastOnce()).update(user1);
    }

    /*
    Case 10 - Remove yourself as follower
     */
    @Test
    public void removeFollowingTest2() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);

        accountService.removeFollowing(user1.getId(), user1.getId());
        verify(accountDao, never()).update(user1);
    }

    /*
    Case 11 - Remove non existing user from following
     */
    @Test
    public void removeFollowingTest3() throws Exception {
        Account user1 = new Account(Role.USER, "user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(3l)).thenReturn(null);

        accountService.removeFollowing(user1.getId(), 3l);
    }
}